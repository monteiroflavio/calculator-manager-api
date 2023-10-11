(ns calculator-manager-api.mappers.user
  (:require [schema.core :as s]
            [calculator-manager-api.models.user :refer [User]]))

(s/defn db->internal :- User
  [db-user]
  {:id       (:users/id       db-user)
   :username (:users/username db-user)
   :status   (:users/status   db-user)})