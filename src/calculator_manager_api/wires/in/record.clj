(ns calculator-manager-api.wires.in.record 
  (:require [calculator-manager-api.models.common :refer [NotZero PositiveReal]]
            [schema.core :as s]))

(s/defschema ^:private ValidOperationId
  (s/enum 2 3 4 5 6 7))

(def ^:private base-insert-record-skeleton
  {:operation-id ValidOperationId
   :a (s/maybe s/Num)
   :b (s/maybe s/Num)})

(def ^:private BaseInsertRecord
  base-insert-record-skeleton)

(def ^:private single-parameter-insert-record-skeleton
  (-> base-insert-record-skeleton
      (dissoc :a :b)
      (assoc :a s/Num
             :b (s/maybe s/Num))))

(def ^:private DoubleParametersInsertRecord
  (-> single-parameter-insert-record-skeleton
      (dissoc :b)
      (assoc :b s/Num)))

(def ^:private PositiveDenominatorInsertRecord
  (-> single-parameter-insert-record-skeleton
      (dissoc :b)
      (assoc :b NotZero)))

(def ^:private PositiveSingleInsertRecord
  (-> single-parameter-insert-record-skeleton
      (dissoc :a :b)
      (assoc :a PositiveReal)))

(defn ^:private insert-record? [operation-id schema]
  (= (:operation-id schema) operation-id))

(s/defschema InsertRecord (s/conditional
                           #(insert-record? 2 %) DoubleParametersInsertRecord
                           #(insert-record? 3 %) DoubleParametersInsertRecord
                           #(insert-record? 4 %) DoubleParametersInsertRecord
                           #(insert-record? 5 %) PositiveDenominatorInsertRecord
                           #(insert-record? 6 %) PositiveSingleInsertRecord
                           #(insert-record? 7 %) BaseInsertRecord))