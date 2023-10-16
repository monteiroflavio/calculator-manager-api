(ns calculator-manager-api.wires.db.record
  (:require [calculator-manager-api.models.common :refer [Real Status]]
            [calculator-manager-api.models.operation :refer [Type]]
            [calculator-manager-api.models.user :refer [Email]]
            [schema.core :as s]))

(def record-skeleton
  {:users/id                   s/Int
   :users/username             Email
   :users/password             s/Str
   :users/status               Status
   :operations/id              s/Int
   :operations/type            Type
   :operations/cost            Real
   :records/id                 s/Int
   :records/user_id            s/Int
   :records/operation_id       s/Int
   :records/user_balance       (s/maybe Real)
   :records/amount             Real
   :records/operation_response (s/maybe s/Str)
   :records/date               java.sql.Timestamp
   :records/status             Status})

(s/defschema Record record-skeleton)