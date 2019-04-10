(ns nuid.ethereum
  #?@(:clj
      [(:import
        (org.web3j.protocol http.HttpService Web3j)
        (org.web3j.tx FastRawTransactionManager)
        (org.web3j.crypto Credentials))])
  #?@(:cljs
      [(:require
        ["keythereum" :as keth]
        ["bn.js" :as bnjs]
        ["Web3" :as Web3])]))

(def default-gas-price #?(:clj (BigInteger/valueOf 22000000000)
                          :cljs (bnjs. "22000000000")))

(def default-gas-limit #?(:clj (BigInteger/valueOf 4300000)
                          :cljs (bnjs. "4300000")))

(def default-value #?(:clj (BigInteger/valueOf 0) :cljs "0x00"))

(defn connection [{:keys [ethereum/http-provider] :as config}]
  (let [conn #?(:clj (Web3j/build (HttpService. http-provider))
                :cljs (Web3. http-provider))]
    (assoc config :ethereum/connection conn)))

#?(:clj (defn fast-raw-transaction-manager
          [{:keys [ethereum/connection ethereum/private-key] :as client}]
          (let [creds (Credentials/create private-key)
                rtm (FastRawTransactionManager. connection creds)]
            (assoc client :ethereum/transaction-manager rtm))))

(defn client [config]
  (let [conn (connection config)]
    #?(:clj (fast-raw-transaction-manager conn)
       :cljs conn)))

#?(:cljs (def exports #js {:defaultGasPrice default-gas-price
                           :defaultGasLimit default-gas-limit
                           :defaultValue default-value
                           :connection connection
                           :client client}))
