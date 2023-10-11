(ns calculator-manager-api.ports.repositories.operation
  (:require [calculator-manager-api.adapters.commons.db :refer [execute! list-operation-map]]
            [calculator-manager-api.mappers.operation :refer [db->internal dbs->internals]]))

(defn list! []
  (-> (list-operation-map nil)
      execute! 
      dbs->internals))

(defn get! [id]
  (-> (list-operation-map id)
      execute!
      (get 0)
      db->internal))