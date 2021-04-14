(ns nuid.ethereum.transaction
  (:refer-clojure :exclude [send])
  (:require
   [cljs.core.async :as async]
   [goog.object :as obj]
   [nuid.ethereum.client :as client]
   [nuid.ethereum.lib :as lib]))


   ;;;
   ;;; NOTE: helpers
   ;;;


(defn -send-signed!
  [{::client/keys [conn]} channel signed]
  (.sendSignedTransaction
   (obj/get conn "eth")
   (obj/get signed "rawTransaction")
   (fn [err id] (async/put! channel (if err {::anomaly err} {::id id})))))


   ;;;
   ;;; NOTE: api
   ;;;


(defn send!
  [{::client/keys [coinbase conn private-key] :as config}
   {::keys [data channel gas-limit gas-price to value]
    :or    {channel   (async/chan)
            gas-limit lib/default-gas-limit
            gas-price lib/default-gas-price
            to        coinbase
            value     lib/default-value}}]
  (let [transaction #js {:gas gas-limit :to to :data data}
        eth         (obj/get conn "eth")
        accounts    (obj/get eth "accounts")]
    (->
     (.signTransaction accounts transaction private-key)
     (.then (partial -send-signed! config channel))
     (.catch (fn [err] (async/put! channel {::anomaly err}))))
    channel))

(defn get-by-id
  [{::client/keys [conn]}
   {::keys [id channel]
    :or    {channel (async/chan)}}]
  (.getTransaction
   (obj/get conn "eth")
   id
   (fn [_ tx] (async/put! channel (or tx {::anomaly ::not-found}))))
  channel)

(defn get-receipt-by-id
  [{::client/keys [conn]}
   {::keys [id channel]
    :or    {channel (async/chan)}}]
  (.getTransactionReceipt
   (obj/get conn "eth")
   id
   (fn [_ receipt] (async/put! channel (or receipt {::anomaly ::not-found}))))
  channel)
