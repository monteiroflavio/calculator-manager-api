(ns calculator-manager-api.models.record
  (:require [calculator-manager-api.models.common :refer [Real Status]]
            [calculator-manager-api.models.operation :refer [Operation]]
            [calculator-manager-api.models.user :refer [User]]
            [schema.core :as s]))

(def record-skeleton
  {:id                 s/Int
   :user               User
   :operation          Operation
   :user-balance       (s/maybe Real)
   :amount             Real
   :operation-response (s/maybe s/Str)
   :date               s/Str
   :status             Status})

(s/defschema Record record-skeleton)