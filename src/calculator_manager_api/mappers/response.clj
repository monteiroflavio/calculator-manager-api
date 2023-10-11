(ns calculator-manager-api.mappers.response 
  (:require [calculator-manager-api.models.exception :refer [NOT-ENOUGH-CREDIT NOT-FOUND]]))

(defn ->ok [body]
  {:status 200 :body body})

(defn ->created []
  {:status 201})

(defn ->no-content []
  {:status 204})

(defmulti ->exception-response (fn [e] (:type (ex-data e))))

(defmethod ^:private ->exception-response :default [e]
  (println e)
  {:status 500 :body {:message "unknown exception"}})

(defmethod ^:private ->exception-response NOT-FOUND [e] {:status 404 :body (ex-data e)})

(defmethod ^:private ->exception-response NOT-ENOUGH-CREDIT [e] {:status 400 :body (ex-data e)})