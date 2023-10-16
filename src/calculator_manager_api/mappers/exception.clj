(ns calculator-manager-api.mappers.exception 
  (:require [calculator-manager-api.models.exception :refer [InternalException
                                                             NOT-ENOUGH-CREDIT NOT-FOUND]]
            [schema.core :as s]))

(defn ^:private ->exception [type entity message]
  {:entity  entity
   :type    type
   :message message})

(s/defn ->not-found :- InternalException
  [entity :- s/Str]
  (->exception NOT-FOUND entity (str entity " not found.")))

(s/defn ->not-enough-credit :- InternalException
  [entity  :- s/Str
   balance :- s/Num
   cost    :- s/Num]
  (->exception NOT-ENOUGH-CREDIT entity (str "Not enough credit - user balance: " balance ", operation cost: " cost)))