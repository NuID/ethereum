(ns nuid.ethereum.transaction
  (:refer-clojure :exclude [send])
  (:require
   [clojure.core.async :as async]
   [nuid.ethereum.client :as client]
   [nuid.ethereum.lib :as lib]))

(defn send!
  [{::client/keys [transaction-manager]}
   {::keys [data channel gas-limit gas-price to value]
    :or    {channel   (async/chan)
            gas-limit lib/default-gas-limit
            gas-price lib/default-gas-price
            to        (.getFromAddress transaction-manager)
            value     lib/default-value}}]
  (let [tx (.sendTransaction transaction-manager gas-price gas-limit to data value)
        v  (or
            (and tx (.hasError tx) {::anomaly (.getMessage (.getError tx))})
            {::id (.getTransactionHash tx)})]
    (async/put! channel v)
    channel))

  ;; From Web3j
(def replace-anomaly-message "replacement transaction underpriced")
(def nonce-anomaly-message   "nonce too low")

(def retry?
  (comp
   #{replace-anomaly-message
     nonce-anomaly-message}
   ::anomaly))

  ;; TODO: async/retry
(defn send-with-retry!
  [config {::keys [retries] :as opts :or {retries 50}}]
  (async/go-loop [rs retries]
    (if (> rs 0)
      (let [resp (async/<! (send! config opts))]
        (if (retry? resp)
          (recur (assoc opts ::retries (dec retries)))
          resp))
      {::anomaly ::retry})))

(def reset?
  (comp
   #{::retry}
   ::anomaly))

(defn send-with-reset!
  [{::client/keys [transaction-manager] :as config}
   {::keys [resets] :as opts :or {resets 20}}]
  (async/go-loop [rs resets]
    (if (> rs 0)
      (let [resp (async/<! (send-with-retry! config opts))]
        (if (reset? resp)
          (do
            (.resetNonce transaction-manager)
            (recur (assoc opts ::resets (dec resets))))
          resp))
      {::anomaly ::reset})))

(defn get-by-id
  [{::client/keys [conn]}
   {::keys [id channel]
    :or    {channel (async/chan)}}]
  (let [tx
        (->
         (.ethGetTransactionByHash conn id)
         (.send)
         (.getTransaction)
         (.orElse {::anomaly ::not-found}))]
    (async/put! channel tx)
    channel))

(defn get-receipt-by-id
  [{::client/keys [conn]}
   {::keys [id channel]
    :or    {channel (async/chan)}}]
  (let [receipt
        (->
         (.ethGetTransactionReceipt conn id)
         (.send)
         (.getTransactionReceipt)
         (.orElse {::anomaly ::not-found}))]
    (async/put! channel receipt)
    channel))
