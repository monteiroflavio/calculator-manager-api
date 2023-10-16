(ns calculator-manager-api.mappers.record
  (:require [calculator-manager-api.mappers.operation :as mappers.operation]
            [calculator-manager-api.mappers.user :as mappers.user]
            [calculator-manager-api.models.record :as models.record]
            [calculator-manager-api.wires.db.record :as db.record]
            [schema.core :as s]))

(s/defn db->internal :- models.record/Record
  [db-record :- db.record/Record]
  {:id                 (:records/id db-record)
   :user               (mappers.user/db->internal db-record)
   :operation          (mappers.operation/db->internal db-record)
   :user-balance       (:records/user-balance db-record)
   :amount             (:records/amount db-record)
   :operation-response (:records/operation-response db-record)
   :date               (str (:records/date db-record))
   :status             (:records/status db-record)})

(s/defn db->internals :- [models.record/Record]
  [db-records :- [db.record/Record]]
  (mapv db->internal db-records))