(ns calculator-manager-api.mappers.operation
  (:require [calculator-manager-api.models.operation :refer [Operation]]
            [schema.core :as s]))

(s/defn db->internal :- Operation
  [db-operation]
  {:id   (:operations/id   db-operation)
   :type (:operations/type db-operation)
   :cost (:operations/cost db-operation)})

(s/defn dbs->internals :- [Operation]
  [db-operations]
  (mapv db->internal db-operations))