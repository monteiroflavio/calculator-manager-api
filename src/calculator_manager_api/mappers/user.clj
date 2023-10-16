(ns calculator-manager-api.mappers.user
  (:require [schema.core :as s]
            [calculator-manager-api.models.user :as models.user]
            [calculator-manager-api.wires.db.user :as db.user]))

(s/defn db->internal :- models.user/User
  [db-user :- db.user/User]
  {:id       (:users/id       db-user)
   :username (:users/username db-user)
   :status   (:users/status   db-user)})