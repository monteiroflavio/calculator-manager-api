(ns calculator-manager-api.mappers.exception 
  (:require [calculator-manager-api.models.exception :refer [NOT-ENOUGH-CREDIT NOT-FOUND]]))

(defn ^:private ->exception [type entity message]
  {:entity  entity
   :type    type
   :message message})

(defn ->not-found [entity]
  (->exception NOT-FOUND entity (str entity " not found.")))

(defn ->not-enough-credit [entity balance cost]
  (->exception NOT-ENOUGH-CREDIT entity (str "Not enough credit - user balance: " balance ", operation cost: " cost)))