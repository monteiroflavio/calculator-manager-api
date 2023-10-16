(ns calculator-manager-api.ports.repositories.record
  (:require [calculator-manager-api.adapters.commons.db :refer [execute!
                                                                insert-record-map
                                                                list-record-map update-record-map]]
            [calculator-manager-api.mappers.record :refer [db->internals]]
            [calculator-manager-api.models.common :as models.common]
            [calculator-manager-api.models.record :refer [Record]]
            [schema.core :as s]))

(s/defn list! :- [Record]
  [user-id status q limit offset sorting sorting-field]
  (-> (list-record-map user-id status q limit offset sorting sorting-field)
      execute!
      db->internals))

(defn delete!
  [id]
  (execute! (update-record-map id models.common/INACTIVE)))

(defn insert!
  [operation-id user-id amount user-balance operation-response]
  (execute! (insert-record-map operation-id user-id amount user-balance operation-response models.common/ACTIVE)))