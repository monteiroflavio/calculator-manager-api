(ns calculator-manager-api.mappers.operation
  (:require [calculator-manager-api.models.operation :as models.operation]
            [calculator-manager-api.wires.db.operation :as db.operation]
            [calculator-manager-api.wires.db.record :refer [Record]]
            [schema.core :as s]
            [clojure.set :as set]))

(s/defn db->internal :- models.operation/Operation
  [db-operation :- db.operation/Operation]
  (-> db-operation
      (set/rename-keys {:operations/id :id :operations/type :type :operations/cost :cost})
      (select-keys (keys models.operation/operation-skeleton))))

(s/defn db->internals :- [models.operation/Operation]
  [db-operations :- [db.operation/Operation]]
  (mapv db->internal db-operations))

(s/defn db-record->db-operation :- db.operation/Operation
  [db-record :- Record]
  (-> db-record (select-keys [:operations/id :operations/type :operations/cost])))