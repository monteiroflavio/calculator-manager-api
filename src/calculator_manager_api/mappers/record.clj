(ns calculator-manager-api.mappers.record
  (:require [calculator-manager-api.mappers.operation :as mappers.operation]
            [calculator-manager-api.mappers.user :as mappers.user]
            [calculator-manager-api.models.record :as models.record]
            [calculator-manager-api.wires.db.record :as db.record]
            [schema.core :as s]))

(s/defn db->internal :- models.record/Record
  [db-record :- db.record/Record]
  {:id                 (:records/id db-record)
   :user               (-> db-record mappers.user/db-record->db-user mappers.user/db->internal)
   :operation          (-> db-record mappers.operation/db-record->db-operation mappers.operation/db->internal)
   :user-balance       (:records/user_balance db-record)
   :amount             (:records/amount db-record)
   :operation-response (:records/operation_response db-record)
   :date               (str (:records/date db-record))
   :status             (:records/status db-record)})

(s/defn db->internals :- [models.record/Record]
  [db-records :- [db.record/Record]]
  (mapv db->internal db-records))