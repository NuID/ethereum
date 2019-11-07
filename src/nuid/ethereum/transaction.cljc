(ns nuid.ethereum.transaction
  (:refer-clojure :exclude [send])
  (:require
   [nuid.hex :as hex]
   [nuid.bn :as bn]
   #?@(:clj
       [[clojure.core.async :as async]]
       :cljs
       [[cljs.core.async :as async]]))
  #?@(:clj
      [(:import
        (org.web3j.protocol.core.methods.response Transaction))]))

(def default-gas-price (bn/from "22000000000"))
(def default-gas-limit (bn/from "4300000"))
(def default-value (bn/from "0"))

#?(:clj
   (defn send
     [{:keys [ethereum/transaction-manager]}
      {:keys [data gas-price gas-limit to value channel]
       :or   {gas-price default-gas-price
              gas-limit default-gas-limit
              to        (.getFromAddress transaction-manager)
              value     default-value
              channel   (async/chan)}}]
     (let [tx (.sendTransaction transaction-manager gas-price gas-limit to data value)
           v  (or (and tx (.hasError tx) {:err (.getMessage (.getError tx))})
                  {:ethereum/transaction-id (.getTransactionHash tx)})]
       (async/put! channel v)
       channel)))

#?(:clj (def replace-error "replacement transaction underpriced"))
#?(:clj (def nonce-error "nonce too low"))
#?(:clj (def retry-error "too many retries"))
#?(:clj (def reset-error "too many resets"))

#?(:clj
   (defn retry?
     [{:keys [err]}]
     (and err (or (= err replace-error)
                  (= err nonce-error)))))

#?(:clj
   (defn send-with-retry
     [client {:keys [retries] :as opts :or {retries 50}}]
     (async/go-loop [rs retries]
       (if (> rs 0)
         (let [resp (async/<! (send client opts))]
           (if (retry? resp)
             (recur (assoc opts :retries (dec retries)))
             resp))
         {:err retry-error}))))

#?(:clj
   (defn reset?
     [{:keys [err]}]
     (and err (= err retry-error))))

#?(:clj
   (defn send-with-reset
     [{:keys [ethereum/transaction-manager] :as client}
      {:keys [resets] :as opts :or {resets 20}}]
     (async/go-loop [rs resets]
       (if (> rs 0)
         (let [resp (async/<! (send-with-retry client opts))]
           (if (reset? resp)
             (do (.resetNonce transaction-manager)
                 (recur (assoc opts :resets (dec resets))))
             resp))
         {:err reset-error}))))

#?(:clj
   (defn get-by-hash
     [{:keys [ethereum/connection]}
      {:keys [ethereum/transaction-id channel]
       :or   {channel (async/chan)}}]
     (let [tx
           (->
            (.ethGetTransactionByHash connection transaction-id)
            (.send)
            (.getTransaction)
            (.orElse {:err :not-found}))]
       (async/put! channel tx)
       channel)))

#?(:clj
   (defn receipt
     [{:keys [ethereum/connection]}
      {:keys [ethereum/transaction-id channel]
       :or   {channel (async/chan)}}]
     (let [receipt
           (->
            (.ethGetTransactionReceipt connection transaction-id)
            (.send)
            (.getTransactionReceipt)
            (.orElse {:err :not-found}))]
       (async/put! channel receipt)
       channel)))

#?(:cljs
   (defn send
     [{:keys [ethereum/coinbase ethereum/connection ethereum/private-key]}
      {:keys [data gas-price gas-limit to value channel]
       :or   {gas-price default-gas-price
              gas-limit default-gas-limit
              to        coinbase
              value     default-value
              channel   (async/chan)}}]
     (let [tx       #js {"gas" gas-limit "to" to "data" data}
           accounts (.-accounts ^js (.-eth connection))]
       (->
        (.signTransaction ^js accounts tx private-key)
        (.then
         (fn [signed]
           (.sendSignedTransaction
            ^js (.-eth connection)
            (.-rawTransaction ^js signed)
            (fn [err id]
              (->>
               (if err {:err err} {:ethereum/transaction-id id})
               (async/put! channel))))))
        (.catch (fn [err] (async/put! channel {:err err}))))
       channel)))

#?(:cljs
   (defn get-by-hash
     [{:keys [ethereum/connection]}
      {:keys [ethereum/transaction-id channel]
       :or   {channel (async/chan)}}]
     (.getTransaction
      ^js (.-eth connection)
      transaction-id
      (fn [_ tx]
        (->>
         (or tx {:err :not-found})
         (async/put! channel))))
     channel))

#?(:cljs
   (defn receipt
     [{:keys [ethereum/connection]}
      {:keys [ethereum/transaction-id channel]
       :or   {channel (async/chan)}}]
     (.getTransactionReceipt
      ^js (.-eth connection)
      transaction-id
      (fn [_ receipt]
        (->>
         (or receipt {:err :not-found})
         (async/put! channel))))
     channel))

(defn get-input
  [x]
  #?(:clj (.getInput x)
     :cljs (.-input x)))

#?(:cljs (def exports #js {}))
