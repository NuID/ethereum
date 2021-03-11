(ns nuid.ethereum.proto
  (:require
   [clojure.spec.alpha :as s]))

(defprotocol Client
  (send-transaction!             [client opts])
  (get-transaction-by-id         [client opts])
  (get-transaction-receipt-by-id [client opts]))

(s/def ::client
  (fn [x] (satisfies? Client x)))
