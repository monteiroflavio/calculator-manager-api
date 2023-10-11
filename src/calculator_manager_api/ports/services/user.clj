(ns calculator-manager-api.ports.services.user 
  (:require [calculator-manager-api.mappers.exception :refer [->not-found]]
            [calculator-manager-api.ports.repositories.user :as repositories.user]))

(def ^:private ENTITY "User")

(defn get! [username password]
  (let [result (repositories.user/get! username password)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))