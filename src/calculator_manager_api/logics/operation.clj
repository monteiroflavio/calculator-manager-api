(ns calculator-manager-api.logics.operation
  (:require [schema.core :as s]))

(s/defn enough-balance? :- s/Bool
  [user-balance   :- s/Num
   operation-cost :- s/Num]
  (>= user-balance operation-cost))