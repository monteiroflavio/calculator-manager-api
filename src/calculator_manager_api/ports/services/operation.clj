(ns calculator-manager-api.ports.services.operation
  (:require [calculator-manager-api.mappers.exception :refer [->not-found]]
            [calculator-manager-api.models.operation :refer [Operation]]
            [calculator-manager-api.ports.repositories.operation :as repositories.operation]
            [schema.core :as s]))

(def ^:private ENTITY "Operation")

(s/defn list! :- [Operation]
  []
  (let [result (repositories.operation/list!)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))

(s/defn get! :- Operation
  [id :- s/Int]
  (let [result (repositories.operation/get! id)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))