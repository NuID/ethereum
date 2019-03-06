(ns nuid.ethereum
  (:require
   [nuid.transit :as transit]
   [nuid.utils :as utils]
   [nuid.bn :as bn]
   #?@(:clj
       [[clojure.core.async :refer [go chan <! <!! put! take!]]]
       :cljs
       [[cljs.core.async :refer-macros [go] :refer [chan <! put! take!]]
        ["ethereumjs-tx" :as etx]
        ["keythereum" :as keth]
        ["web3" :as web3]
        ["buffer" :as b]]))
  #?@(:clj
      [(:import
        (org.web3j.tx FastRawTransactionManager)
        (org.web3j.crypto Credentials)
        (org.web3j.protocol
         core.methods.request.Transaction
         http.HttpService
         Web3j))]))

(def default-gas-price
  #?(:clj (BigInteger/valueOf 22000000000)
     :cljs (web3/utils.toBN "22000000000")))

(def default-gas-limit
  #?(:clj (BigInteger/valueOf 4300000)
     :cljs (web3/utils.toBN "4300000")))

(defn make-connection [{:keys [http-provider] :as config}]
  (let [conn #?(:cljs (web3. (web3/providers.HttpProvider. http-provider))
                :clj (Web3j/build (HttpService. http-provider)))]
    (assoc config :conn conn)))

#?(:clj (defn make-fast-raw-transaction-manager
          [{:keys [conn private-key] :as client}]
          (let [creds (Credentials/create private-key)
                rtm (FastRawTransactionManager. conn creds)]
            (assoc client :transaction-manager rtm))))

(defn get-coinbase-from-private-key [private-key]
  #?(:clj (.getAddress (Credentials/create private-key))
     :cljs (keth/privateKeyToAddress private-key)))

(defn get-transaction-count
  [{:keys [client address pending? channel]
    :or {address (:coinbase client)
         pending? "pending"
         channel (chan 1)}}]
  #?(:cljs
     (.getTransactionCount
      (.-eth (:conn client))
      (or address (:coinbase client))
      (or pending? "pending")
      #(put! channel (or %1 %2))))
  channel)

(defn encode-transaction
  [{:keys [client nonce gas-price gas-limit to value data]
    :or {gas-price default-gas-price
         gas-limit default-gas-limit
         to (:coinbase client)
         value "0x00"}}]
  #?(:cljs
     (let [tx (etx. #js {"nonce" (web3/utils.toHex nonce)
                         "data" (transit/hex-encode data)
                         "gasPrice" gas-price
                         "gasLimit" gas-limit
                         "value" value
                         "to" to})]
       (.sign tx (b/Buffer.from (:private-key client) "hex"))
       (str "0x" (-> tx .serialize (.toString "hex"))))))

(defn decode-transaction
  [{:keys [transaction]}]
  (transit/hex-decode
   #?(:clj (.getInput transaction)
      :cljs (.-input transaction))))

(defn send-transaction
  [{:keys [client gas-price gas-limit to data value channel]
    :or {gas-price default-gas-price
         gas-limit default-gas-limit
         to (:coinbase client)
         value (BigInteger/valueOf 0)
         channel (chan 1)}
    :as transaction}]
  #?(:clj
     (let [tm (:transaction-manager client)
           raw (.sendTransaction tm gas-price gas-limit to data value)
           resp (if (and raw (.hasError raw))
                  {:err (.getMessage (.getError raw))}
                  {:transaction-id (.getTransactionHash raw)})]
       (put! channel resp))
     :cljs
     (take!
      (get-transaction-count transaction)
      (fn [nonce]
        (.sendSignedTransaction
         (.-eth (:conn client))
         (encode-transaction (assoc transaction :nonce nonce))
         #(put! channel (if %1 {:err %1} {:transaction-id %2}))))))
  channel)

#?(:clj (defn retry? [{:keys [err]}]
          (and err (or (= err "replacement transaction underpriced")
                       (= err "nonce too low")))))

#?(:clj (defn send-transaction-with-retry-nonces
          [{:keys [transaction nonces] :or {nonces 100} :as opts}]
          (if (> nonces 0)
            (let [resp (<!! (send-transaction transaction))]
              (if (retry? resp)
                (recur (assoc opts :nonces (dec nonces)))
                resp))
            {:err "too many retries"})))

#?(:clj (defn reset? [{:keys [err]}]
          (and err (= err "too many retries"))))

#?(:clj (defn send-transaction-with-reset-nonce
          [{:keys [transaction resets] :or {resets 10} :as opts}]
          (if (> resets 0)
            (let [resp (send-transaction-with-retry-nonces opts)]
              (if (reset? resp)
                (recur (assoc opts :resets (dec resets)))
                resp))
            {:err "too many resets"})))

(defn get-transaction
  [{:keys [client transaction-id channel]
    :or {channel (chan 1)}}]
  #?(:clj
     (let [resp (-> (.ethGetTransactionByHash (:conn client) transaction-id)
                    (.send)
                    (.getTransaction)
                    (.orElse nil))]
       (put! channel (if resp {:transaction resp} {:err :not-found})))
     :cljs
     (.getTransaction
      (.-eth (:conn client))
      transaction-id
      #(put! channel (if %1 {:err :not-found} {:transaction %2}))))
  channel)

(defn get-transaction-receipt
  [{:keys [client transaction-id channel]
    :or {channel (chan 1)}}]
  #?(:clj
     (let [resp (-> (.ethGetTransactionReceipt (:conn client) transaction-id)
                    (.send)
                    (.getTransactionReceipt)
                    (.orElse nil))]
       (put! channel (if resp {:transaction-receipt resp} {:err :not-found}))))
  channel)

(defn finalized?
  [{:keys [client transaction-id channel]
    :or {channel (chan 1)}
    :as input}]
  (take!
   (get-transaction-receipt (dissoc input :channel))
   #(put! channel (contains? % :transaction-receipt)))
  channel)

#?(:cljs (def exports
           #js {:get-transaction-count get-transaction-count
                :encode-transaction encode-transaction
                :decode-transaction decode-transaction
                :default-gas-price default-gas-price
                :default-gas-limit default-gas-limit
                :send-transaction send-transaction
                :get-transaction get-transaction
                :make-connection make-connection}))
