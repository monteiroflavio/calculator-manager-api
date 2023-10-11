(ns calculator-manager-api.logics.operation
  (:require [schema.core :as s]))

(s/defn enough-balance? :- s/Bool
  [user-balance   :- BigDecimal
   operation-cost :- BigDecimal]
  (>= user-balance operation-cost))