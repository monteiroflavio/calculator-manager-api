(ns calculator-manager-api.ports.repositories.user
  (:require [calculator-manager-api.adapters.commons.db :refer [execute!
                                                                list-user-map]]
            [calculator-manager-api.mappers.user :refer [db->internal]]
            [calculator-manager-api.models.user :refer [Email User]]
            [schema.core :as s]))

(s/defn get! :- User
  [username :- Email
   password :- s/Str]
  (some-> (list-user-map username password)
          execute!
          (get 0)
          db->internal))