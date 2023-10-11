(ns calculator-manager-api.adapters.commons.encryption
  (:require [crypto.password.pbkdf2 :as password]))

(defn encrypt
  [string]
  (password/encrypt string))

(defn check
  [string encrypted]
  (password/check string encrypted))