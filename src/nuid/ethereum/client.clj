(ns nuid.ethereum.client
  (:require
   [clojure.spec.alpha :as s])
  (:import
   (org.web3j.crypto Credentials)
   (org.web3j.protocol Web3j)
   (org.web3j.protocol.http HttpService)
   (org.web3j.tx FastRawTransactionManager)))


   ;;;
   ;;; NOTE: specs, predicates
   ;;;


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


   ;;;
   ;;; NOTE: client configuration helpers
   ;;;


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


   ;;;
   ;;; NOTE: api
   ;;;


(defn parameters->config
  [params]
  (let [conn    (conn params)
        manager (transaction-manager conn params)]
    {::conn conn ::transaction-manager manager}))
