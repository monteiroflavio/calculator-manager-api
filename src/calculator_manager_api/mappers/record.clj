(ns calculator-manager-api.mappers.record
  (:require [calculator-manager-api.mappers.operation :as mappers.operation]
            [calculator-manager-api.mappers.user :as mappers.user]
            [calculator-manager-api.models.record :refer [Record]]
            [schema.core :as s]))

(s/defn db->internal :- Record
  [db-record]
  {:id                 (:records/id db-record)
   :user               (mappers.user/db->internal db-record)
   :operation          (mappers.operation/db->internal db-record)
   :user-balance       (:records/user-balance db-record)
   :amount             (:records/amount db-record)
   :operation-response (:records/operation-response db-record)
   :date               (str (:records/date db-record))
   :status             (:records/status db-record)})

(s/defn dbs->internals :- [Record]
  [db-records]
  (mapv db->internal db-records))