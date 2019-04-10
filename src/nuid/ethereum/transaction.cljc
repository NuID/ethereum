(ns nuid.ethereum.transaction
  (:require
   [nuid.ethereum.address :as addr]
   [nuid.ethereum :as eth]
   [nuid.hex :as hex]
   [nuid.bn :as bn]
   #?@(:clj
       [[clojure.core.async :as async]]
       :cljs
       [[cljs.core.async :as async]
        ["ethereumjs-tx" :as etx]]))
  (:refer-clojure :exclude [send get]))

#?(:cljs (defn sign [client {:keys [nonce gas-price gas-limit to value data]
                             :or {gas-price eth/default-gas-price
                                  gas-limit eth/default-gas-limit
                                  to (:ethereum/coinbase client)
                                  value eth/default-value}}]
           (let [t (etx. #js {"gasPrice" gas-price
                              "gasLimit" gas-limit
                              "value" value
                              "nonce" nonce
                              "data" data
                              "to" to})]
             (.sign t (hex/decode (:ethereum/private-key client)))
             (.serialize t))))

(defn send [client {:keys [gas-price gas-limit to data value channel]
                    :or {gas-price eth/default-gas-price
                         gas-limit eth/default-gas-limit
                         to (:ethereum/coinbase client)
                         value eth/default-value
                         channel (async/chan)}
                    :as transaction}]
  #?(:clj (let [tm (:ethereum/transaction-manager client)
                raw (.sendTransaction tm gas-price gas-limit to data value)
                resp (if (and raw (.hasError raw))
                       {:err (.getMessage (.getError raw))}
                       {:ethereum/transaction-id (.getTransactionHash raw)})]
            (async/put! channel resp))
     :cljs (async/take!
            (addr/transaction-count client)
            (fn [nonce]
              (let [conn (.-eth (:ethereum/connection client))
                    nonce (str "0x" (bn/str (bn/from nonce) 16))
                    raw (assoc transaction :nonce nonce)
                    tx (str "0x" (hex/encode (sign client raw)))
                    f #(async/put!
                        channel
                        (if %1
                          {:err %1}
                          {:ethereum/transaction-id %2}))]
                (.sendSignedTransaction conn tx f)))))
  channel)

#?(:clj (defn retry? [{:keys [err]}]
          (and err (or (= err "replacement transaction underpriced")
                       (= err "nonce too low")))))

#?(:clj (defn send-with-retry
          [client {:keys [retries] :or {retries 50} :as opts}]
          (if (> retries 0)
            (let [resp (async/<!! (send client opts))]
              (if (retry? resp)
                (recur client (assoc opts :retries (dec retries)))
                resp))
            {:err "too many retries"})))

#?(:clj (defn reset? [{:keys [err]}]
          (and err (= err "too many retries"))))

#?(:clj (defn send-with-reset
          [client {:keys [resets] :or {resets 20} :as opts}]
          (if (> resets 0)
            (let [resp (send-with-retry client opts)]
              (if (reset? resp)
                (do (.resetNonce (:ethereum/transaction-manager client))
                    (recur client (assoc opts :resets (dec resets))))
                resp))
            {:err "too many resets"})))

(defn get [client {:keys [ethereum/transaction-id channel] :or {channel (async/chan)}}]
  #?(:clj (let [resp (-> (.ethGetTransactionByHash (:ethereum/connection client) transaction-id)
                         (.send)
                         (.getTransaction)
                         (.orElse nil))]
            (async/put! channel (if resp {:ethereum/transaction resp} {:err :not-found})))
     :cljs (let [f #(async/put! channel (if %1 {:err :not-found} {:ethereum/transaction %2}))
                 conn (.-eth (:ethereum/connection client))]
             (.getTransaction conn transaction-id f)))
  channel)

(defn input [{:keys [ethereum/transaction]}]
  #?(:clj (.getInput transaction)
     :cljs (.-input transaction)))

#?(:clj (defn receipt [client {:keys [ethereum/transaction-id channel] :or {channel (async/chan)}}]
          (let [resp (-> (.ethGetTransactionReceipt (:ethereum/connection client) transaction-id)
                         (.send)
                         (.getTransactionReceipt)
                         (.orElse nil))]
            (async/put! channel (if resp {:transaction-receipt resp} {:err :not-found})))
          channel))

#?(:clj (defn finalized? [client {:keys [channel] :or {channel (async/chan)} :as input}]
          (let [f #(async/put! channel (contains? % :transaction-receipt))]
            (async/take! (receipt (dissoc input :channel) f))
            channel)))

#?(:cljs (def exports #js {:input input :send send :sign sign :get get}))
