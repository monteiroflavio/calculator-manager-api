(ns calculator-manager-api.wires.in.record 
  (:require [calculator-manager-api.models.common :refer [NotZero]]
            [schema.core :as s]))

(s/defschema ^:private ValidOperationId
  (s/enum 2 3 4 5 6 7))

(def ^:private base-insert-record-skeleton
  {:operation-id ValidOperationId})

(def ^:private BaseInsertRecord
  base-insert-record-skeleton)

(def ^:private single-parameter-insert-record-skeleton
  (-> base-insert-record-skeleton
      (assoc :a s/Num)))

(def ^:private SingleParameterInsertRecord
  single-parameter-insert-record-skeleton)

(def ^:private DoubleParametersInsertRecord
  (-> single-parameter-insert-record-skeleton
      (assoc :b s/Num)))

(def ^:private PositiveDenominatorInsertRecord
  (-> single-parameter-insert-record-skeleton
      (assoc :b NotZero)))

(defn ^:private insert-record? [operation-id schema]
  (= (:operation-id schema) operation-id))

(s/defschema InsertRecord (s/conditional
                           #(insert-record? 2 %) DoubleParametersInsertRecord
                           #(insert-record? 3 %) DoubleParametersInsertRecord
                           #(insert-record? 4 %) DoubleParametersInsertRecord
                           #(insert-record? 5 %) PositiveDenominatorInsertRecord
                           #(insert-record? 6 %) SingleParameterInsertRecord
                           #(insert-record? 7 %) BaseInsertRecord))