(ns nuid.ethereum.lib
  (:require
   [nuid.bn :as bn]))

(def default-gas-price (bn/from "22000000000"))
(def default-gas-limit (bn/from "4300000"))
(def default-value     (bn/from "0"))
