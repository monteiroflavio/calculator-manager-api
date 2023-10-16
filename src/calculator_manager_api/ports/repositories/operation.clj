(ns calculator-manager-api.ports.repositories.operation
  (:require [calculator-manager-api.adapters.commons.db :refer [execute!
                                                                list-operation-map]]
            [calculator-manager-api.mappers.operation :refer [db->internal
                                                              db->internals]]
            [calculator-manager-api.models.operation :refer [Operation]]
            [schema.core :as s]))

(s/defn list! :- [Operation]
  []
  (-> (list-operation-map nil)
      execute! 
      db->internals))

(s/defn get! :- Operation
  [id :- s/Int]
  (-> (list-operation-map id)
      execute!
      (get 0)
      db->internal))