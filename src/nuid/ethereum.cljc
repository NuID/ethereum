(ns nuid.ethereum
  (:require
   [nuid.ethereum.client :as client]
   [nuid.ethereum.impl :as impl]
   [nuid.ethereum.proto :as proto]
   #?@(:clj  [[clojure.alpha.spec :as s]]
       :cljs [[clojure.spec.alpha :as s]])))

(s/def ::parameters ::client/parameters)
(s/def ::client     ::proto/client)

(def parameters->client impl/parameters->client)

(def send-transaction!             proto/send-transaction!)
(def get-transaction-by-id         proto/get-transaction-by-id)
(def get-transaction-receipt-by-id proto/get-transaction-receipt-by-id)
