(ns calculator-manager-api.adapters.commons.http-client
  (:require [calculator-manager-api.logics.http-utils :as logics.http-utils]
            [schema.core :as s]
            [calculator-manager-api.models.request-config :as models.request-config]
            [org.httpkit.client :as http]))

(defn- base-handler
  [on-success-fn on-error-fn]
  (fn [{:keys [status headers body error]}]
    (if error
      (on-error-fn status headers error)
      (on-success-fn status headers body))))

(defn- get!
  [config]
  (let [url           (:url config)
        options       (:options config)
        query-params  (:query-params config)
        on-success-fn (:on-success config)
        on-error-fn   (:on-error config)
        handler       (base-handler on-success-fn on-error-fn)
        final-url     (logics.http-utils/build-url url query-params)]
    @(http/get final-url options handler)))

(s/defn request!
  [config :- models.request-config/RequestConfig]
  (let [method (:method config)]
    (case method 
      :get  (get! config)
      :else nil)))