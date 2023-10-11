(ns calculator-manager-api.adapters.controllers.user 
  (:require [calculator-manager-api.mappers.response :refer [->ok]]
            [calculator-manager-api.ports.services.user :as services.user]))

(defn authenticate! [req]
  ((let [query-params (:body req)
         username     (get query-params "username")
         password     (get query-params "password")
         user         (services.user/get! username password)]
     (->ok user))))