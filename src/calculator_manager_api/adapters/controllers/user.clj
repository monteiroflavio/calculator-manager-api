(ns calculator-manager-api.adapters.controllers.user 
  (:require [calculator-manager-api.adapters.commons.jwt :refer [jwt-sign]]
            [calculator-manager-api.mappers.response :refer [->ok]]
            [calculator-manager-api.ports.services.user :as services.user]
            [calculator-manager-api.wires.in.user :refer [AuthenticateUser]]
            [schema.core :as s]))

(s/defn authenticate!
  [{:keys [body]}]
  (let [_ (s/validate AuthenticateUser body)
        {:keys [username password]} body
        user    (services.user/get! username password)
        user-id (:id user)
        token   (jwt-sign user-id)]
    (->ok {:token token :user {:id user-id}})))