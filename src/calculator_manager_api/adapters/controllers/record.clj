(ns calculator-manager-api.adapters.controllers.record
  (:require [calculator-manager-api.logics.http-utils :refer [map-qs]]
            [calculator-manager-api.mappers.response :refer [->created
                                                             ->no-content ->ok]]
            [calculator-manager-api.wires.in.record :refer [InsertRecord]]
            [calculator-manager-api.ports.services.record :as services.record]
            [schema.core :as s]))

(s/defn list!
  [req]
  (let [user-id       (Integer/parseInt (get (:params req) :user-id))
        query-params  (map-qs (:query-string req))
        q             (:q query-params)
        limit         (:limit query-params)
        offset        (:offset query-params)
        sorting       (:sorting query-params)
        sorting-field (:sorting-field query-params)
        response (services.record/list! user-id q limit offset sorting sorting-field)]
    (->ok response)))

(defn insert!
  [{:keys [body] :as req}]
  (let [_ (s/validate InsertRecord body)
        user-id (Integer/parseInt (-> req :params :user-id))
        {:keys [operation-id a b]} body]
    (services.record/insert! user-id operation-id a b)
    (->created)))

(defn delete! [req]
  (let [id (Integer/parseInt (get (:params req) :id))]
    (services.record/delete! id)
    (->no-content)))