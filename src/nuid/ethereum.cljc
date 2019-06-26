(ns nuid.ethereum
  (:require
   [nuid.ethereum.transaction :as tx]
   [nuid.credential :as credential]
   [nuid.hex :as hex]
   #?@(:clj [[clojure.core.async :as async]]
       :cljs [[cljs.core.async :as async :include-macros true]
              ["Web3" :as Web3]]))
  #?@(:clj
      [(:import
        (org.web3j.protocol http.HttpService Web3j)
        (org.web3j.tx FastRawTransactionManager)
        (org.web3j.crypto Credentials))]))

(defprotocol Client
  (send-transaction        [client opts])
  (get-transaction-by-hash [client opts])
  (get-transaction-receipt [client opts]))

(defn- t
  [client {:keys [credential.data/transit]}]
  (let [d {:data (hex/prefixed (hex/encode transit))}]
    (async/go (let [{:keys [ethereum/transaction-id]}
                    (async/<! (send-transaction client d))]
                {:store.ethereum/id transaction-id}))))

(defn- q
  [client {:keys [store.ethereum/id]}]
  (let [q {:ethereum/transaction-id id}]
    (get-transaction-by-hash client q)))

#?(:clj
   (defn client
     [{:keys [ethereum/http-provider ethereum/private-key]}]
     (let [c (Web3j/build (HttpService. http-provider))
           tm (FastRawTransactionManager. c (Credentials/create private-key))
           conn #:ethereum{:connection c :transaction-manager tm}]
       (reify
         Client
         (send-transaction        [_ opts] (tx/send-with-reset conn opts))
         (get-transaction-by-hash [_ opts] (tx/get-by-hash conn opts))
         (get-transaction-receipt [_ opts] (tx/receipt conn opts))

         credential/Store
         (transact [client opts] (t client opts))
         (query    [client opts] (q client opts))))))

#?(:cljs
   (defn client
     [{:keys [ethereum/http-provider ethereum/private-key]}]
     (let [c (Web3. http-provider)
           pk (hex/prefixed private-key)
           cb (.. c -eth -accounts (privateKeyToAccount pk) -address)
           conn #:ethereum{:connection c :private-key pk :coinbase cb}]
       (reify
         Client
         (send-transaction        [_ opts] (tx/send conn opts))
         (get-transaction-by-hash [_ opts] (tx/get-by-hash conn opts))
         (get-transaction-receipt [_ opts] (tx/receipt conn opts))

         credential/Store
         (transact [client opts] (t client opts))
         (query    [client opts] (q client opts))))))

#?(:cljs
   (def exports
     #js {:getTransactionReceipt get-transaction-receipt
          :getTransactionByHash get-transaction-by-hash
          :sendTransaction send-transaction
          :client client}))
