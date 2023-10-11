(ns calculator-manager-api.models.record
  (:require [calculator-manager-api.models.common :refer [PositiveBigDecimal Status]]
            [calculator-manager-api.models.operation :refer [Operation]]
            [calculator-manager-api.models.user :refer [User]]
            [schema.core :as s]))

(def record-skeleton
  {:id                 {:schema s/Uuid}
   :user               {:schema User}
   :operation          {:schema Operation}
   :user-balance       {:schema PositiveBigDecimal}
   :amount             {:schema PositiveBigDecimal}
   :operation-response {:schema PositiveBigDecimal}
   :date               {:schema java.time.LocalDateTime}
   :status             {:schema Status}})

(s/defschema Record record-skeleton)