(ns nuid.ethereum.client
  (:require
   [clojure.alpha.spec :as s])
  (:import
   (org.web3j.crypto Credentials)
   (org.web3j.protocol Web3j)
   (org.web3j.protocol.http HttpService)
   (org.web3j.tx FastRawTransactionManager)))

(s/def ::parameters
  (s/keys
   :req
   [::http-provider
    ::private-key]))

(s/def ::config
  (s/keys
   :req
   [::conn
    ::transaction-manager]))

(defn conn
  [{::keys [http-provider]}]
  (->>
   (HttpService. http-provider)
   (Web3j/build)))

(defn transaction-manager
  [conn {::keys [private-key]}]
  (->>
   (Credentials/create private-key)
   (FastRawTransactionManager. conn)))

(defn parameters->config
  [params]
  (let [conn    (conn params)
        manager (transaction-manager conn params)]
    {::conn conn ::transaction-manager manager}))
