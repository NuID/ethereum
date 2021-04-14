(ns nuid.ethereum.proto
  (:require
   [clojure.spec.alpha :as s]))


   ;;;
   ;;; NOTE: protocol
   ;;;


(defprotocol Client
  (send-transaction!             [client opts])
  (get-transaction-by-id         [client opts])
  (get-transaction-receipt-by-id [client opts]))


   ;;;
   ;;; NOTE: specs, predicates
   ;;;


(s/def ::client
  (fn [x] (satisfies? Client x)))
