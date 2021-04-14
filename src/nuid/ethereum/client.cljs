(ns nuid.ethereum.client
  (:require
   [clojure.spec.alpha :as s]
   [nuid.hex :as hex]
   ["Web3" :as Web3]))


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
   [::coinbase
    ::conn
    ::private-key]))


   ;;;
   ;;; NOTE: api
   ;;;


(defn parameters->config
  [{::keys [http-provider private-key]}]
  (let [conn     (Web3. http-provider)
        pri      (hex/prefixed private-key)
        coinbase (.. conn -eth -accounts (privateKeyToAccount pri) -address)]
    {::conn conn ::coinbase coinbase ::private-key pri}))
