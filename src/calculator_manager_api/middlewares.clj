(ns calculator-manager-api.middlewares 
  (:require [calculator-manager-api.mappers.response :refer [->exception-response]]))

(defn exception-middleware [handler-fn]
  (fn [req]
    (try
      (handler-fn req)
      (catch Exception e
        (->exception-response e)))))