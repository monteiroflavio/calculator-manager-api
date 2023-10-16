(ns calculator-manager-api.mappers.user
  (:require [calculator-manager-api.models.user :as models.user]
            [calculator-manager-api.wires.db.record :refer [Record]]
            [calculator-manager-api.wires.db.user :as db.user]
            [clojure.set :as set]
            [schema.core :as s]))

(s/defn db->internal :- models.user/User
  [db-user :- db.user/User]
  (-> db-user
      (set/rename-keys {:users/id :id :users/username :username :users/status :status})
      (select-keys (keys models.user/user-skeleton))))

(s/defn db-record->db-user :- db.user/User
  [db-record :- Record]
  (-> db-record
      (select-keys [:users/id :users/username :users/password :users/status])))