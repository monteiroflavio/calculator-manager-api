(ns calculator-manager-api.ports.services.operation
  (:require [calculator-manager-api.mappers.exception :refer [->not-found]]
            [calculator-manager-api.ports.repositories.operation :as repositories.operation]))

(def ^:private ENTITY "Operation")

(defn list! []
  (let [result (repositories.operation/list!)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))

(defn get! [id]
  (let [result (repositories.operation/get! id)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))