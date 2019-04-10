(ns nuid.ethereum.address
  (:require
   #?@(:clj [[clojure.core.async :as async]]
       :cljs [[cljs.core.async :as async]
              ["keythereum" :as keth]]))
  #?@(:clj
      [(:import
        (org.web3j.protocol.core DefaultBlockParameterName)
        (org.web3j.crypto Credentials))]))

(defn from-private-key [private-key]
  #?(:clj (.getAddress (Credentials/create private-key))
     :cljs (keth/privateKeyToAddress private-key)))

(defn transaction-count
  [client & [{:keys [ethereum/address pending? channel]
              :or {address (:ethereum/coinbase client)
                   pending? true
                   channel (async/chan)}}]]
  #?(:clj (let [c (-> (.ethGetTransactionCount
                       (:ethereum/connection client)
                       address
                       (when pending? DefaultBlockParameterName/PENDING))
                      (.send)
                      (.getTransactionCount))]
            (async/put! channel c))
     :cljs (.getTransactionCount
            (.-eth (:ethereum/connection client))
            address
            (when pending? "pending")
            #(async/put! channel (or %1 %2))))
  channel)

#?(:cljs (def exports #js {:transactionCount transaction-count
                           :fromPrivateKey from-private-key}))
