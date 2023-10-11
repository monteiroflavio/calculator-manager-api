(ns calculator-manager-api.ports.repositories.user
  (:require [calculator-manager-api.adapters.commons.db :refer [execute! list-user-map]]
            [calculator-manager-api.mappers.user :refer [db->internal]]))

(defn get!
  [username password]
  (some-> (list-user-map username password)
          execute!
          (get 0)
          db->internal))