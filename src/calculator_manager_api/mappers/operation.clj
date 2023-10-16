(ns calculator-manager-api.mappers.operation
  (:require [calculator-manager-api.models.operation :as models.operation]
            [calculator-manager-api.wires.db.operation :as db.operation]
            [schema.core :as s]))

(s/defn db->internal :- models.operation/Operation
  [db-operation :- db.operation/Operation]
  {:id   (:operations/id   db-operation)
   :type (:operations/type db-operation)
   :cost (:operations/cost db-operation)})

(s/defn db->internals :- [models.operation/Operation]
  [db-operations :- [db.operation/Operation]]
  (mapv db->internal db-operations))