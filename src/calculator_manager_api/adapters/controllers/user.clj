(ns calculator-manager-api.adapters.controllers.user 
  (:require [calculator-manager-api.mappers.response :refer [->ok]]
            [calculator-manager-api.ports.services.user :as services.user]
            [calculator-manager-api.adapters.commons.jwt :refer [jwt-sign]]))

(defn authenticate! [req]
  (let [query-params (:body req)
        username     (get query-params "username")
        password     (get query-params "password")
        user         (services.user/get! username password)
        token        (jwt-sign (:id user))]
    (->ok {:token token})))