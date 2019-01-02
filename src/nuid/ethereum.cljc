(ns nuid.ethereum
  (:require
   [nuid.transit :as transit]
   [nuid.utils :as utils]
   [nuid.bn :as bn]
   #?@(:clj
       [[clojure.core.async :refer [go chan <! put! take!]]]
       :cljs
       [[cljs.core.async :refer-macros [go] :refer [chan <! put! take!]]
        ["ethereumjs-tx" :as etx]
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

(defn get-transaction-count
  [{:keys [client address channel]}]
  (let [c (or channel (chan 1))]
    #?(:cljs
       (.getTransactionCount
        (.-eth (:conn client))
        (or address (:coinbase client))
        #(put! c (or %1 %2))))
    c))

(defn encode-transaction
  [{:keys [client nonce gas-price gas-limit to value data]}]
  #?(:cljs
     (let [tx (etx. #js {"nonce" (web3/utils.toHex nonce)
                         "gasPrice" (or gas-price default-gas-price)
                         "gasLimit" (or gas-limit default-gas-limit)
                         "to" (or to (:coinbase client))
                         "value" (or value "0x00")
                         "data" (transit/hex-encode data)})]
       (.sign tx (b/Buffer.from (:private-key client) "hex"))
       (str "0x" (-> tx .serialize (.toString "hex"))))))

(defn decode-transaction
  [{:keys [transaction]}]
  (transit/hex-decode
   #?(:clj (.getInput transaction)
      :cljs (.-input transaction))))

(defn send-transaction
  [{:keys [client gas-price gas-limit to value data channel]
    :as transaction}]
  (let [c (or channel (chan 1))]
    #?(:clj
       (let [resp (.sendTransaction
                   (:transaction-manager client)
                   (or gas-price default-gas-price)
                   (or gas-limit default-gas-limit)
                   (or to (:coinbase client))
                   data
                   (or value (BigInteger/valueOf 0)))]
         (put! c (if (and resp (.hasError resp))
                   {:err :bad-transaction}
                   {:transaction-id (.getTransactionHash resp)})))
       :cljs
       (take!
        (get-transaction-count client)
        (fn [nonce]
          (.sendSignedTransaction
           (.-eth (:conn client))
           (encode-transaction (assoc transaction :nonce nonce))
           #(put! c (if %1 {:err %1} {:transaction-id %2}))))))
    c))

(defn get-transaction
 [{:keys [client transaction-id channel]}]
  (let [c (or channel (chan 1))]
    #?(:clj
       (-> (.ethGetTransactionByHash (:conn client) transaction-id)
           (.sendAsync)
           (utils/when-complete
            (fn [_ tx _]
              (let [transaction (-> tx .getTransaction (.orElse nil))
                    err (if transaction nil :bad-transaction-id)]
                (put!
                 c
                 [transaction-id
                  (if err
                    {:err err}
                    {:transaction transaction
                     :client client})])))))
       :cljs
       (.getTransaction
        (.-eth (:conn client))
        transaction-id
        #(put!
          c
          [transaction-id
           (if %1
             {:err %1}
             {:transaction %2
              :client client})])))
    c))

(defn transaction->channel
  "Transducer friendly ledger/get-transaction"
  [client channel transaction-id]
  (get-transaction {:transaction-id transaction-id
                    :channel channel
                    :client client}))

(defn decode-channel-result [[transaction-id result]]
  [transaction-id
   (if (:transaction result)
     (decode-transaction result)
     {:err :invalid})])

#?(:cljs (def exports
           #js {:get-transaction-count get-transaction-count
                :decode-channel-result decode-channel-result
                :transaction->channel transaction->channel
                :encode-transaction encode-transaction
                :decode-transaction decode-transaction
                :default-gas-price default-gas-price
                :default-gas-limit default-gas-limit
                :send-transaction send-transaction
                :get-transaction get-transaction
                :make-connection make-connection}))
