(ns nuid.ethereum.impl
  (:require
   [nuid.ethereum.client :as client]
   [nuid.ethereum.proto :as proto]
   [nuid.ethereum.transaction :as tx]))

  ;; TODO: tx multis?
(defn parameters->client
  [params]
  (let [config (client/parameters->config params)]
    (reify proto/Client
      (send-transaction!             [_ opts] #?(:clj  (tx/send-with-reset! config opts)
                                                 :cljs (tx/send!            config opts)))
      (get-transaction-by-id         [_ opts] (tx/get-by-id         config opts))
      (get-transaction-receipt-by-id [_ opts] (tx/get-receipt-by-id config opts)))))
