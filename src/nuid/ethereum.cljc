(ns nuid.ethereum
  (:require
   [nuid.ethereum.client :as client]
   [nuid.ethereum.impl :as impl]
   [nuid.ethereum.proto :as proto]
   [nuid.ethereum.transaction :as tx]
   #?@(:clj  [[clojure.alpha.spec :as s]]
       :cljs [[clojure.spec.alpha :as s]])))

(def networks
  #{:nuid.ethereum.network/mainnet
    :nuid.ethereum.network/rinkeby})

(s/def ::client  ::proto/client)
(s/def ::network networks)
(s/def ::address (s/keys :req [::network ::tx/id]))

(def parameters->client impl/parameters->client)

(defn send-transaction!
  [client opts]
  (proto/send-transaction! client opts))

(defn get-transaction-by-id
  [client opts]
  (proto/get-transaction-by-id client opts))

(defn get-transaction-receipt-by-id
  [client opts]
  (proto/get-transaction-receipt-by-id client opts))
