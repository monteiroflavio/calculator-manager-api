(ns calculator-manager-api.ports.services.user 
  (:require [calculator-manager-api.mappers.exception :refer [->not-found]]
            [calculator-manager-api.models.user :refer [Email User]]
            [calculator-manager-api.ports.repositories.user :as repositories.user]
            [schema.core :as s]))

(def ^:private ENTITY "User")

(s/defn get! :- User
  [username :- Email
   password :- s/Str]
  (let [result (repositories.user/get! username password)]
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))