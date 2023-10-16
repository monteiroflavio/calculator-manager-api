(ns calculator-manager-api.adapters.commons.middlewares 
  (:require [buddy.auth :refer [throw-unauthorized]]
            [calculator-manager-api.adapters.commons.jwt :refer [jwt-sign]]
            [calculator-manager-api.mappers.response :refer [->exception-response-handler]]))

(defn authenticated-middleware [handler]
  (fn [req]
    (let [user-id      (Integer/parseInt (get (:params req) :user-id))
          bearer-token (get (:headers req) "authorization")
          token        (jwt-sign user-id)
          token        (str "Bearer " token)]
      (if (= bearer-token token)
        (handler req)
        (throw-unauthorized)))))

(defn exception-middleware [handler-fn]
  (fn [req]
    (try
      (handler-fn req)
      (catch Exception e
        (->exception-response-handler e)))))

(defn json->edn_middleware [handler-fn]
  (fn [req]
    (let [body (:body req)
          body (into {} (map (fn [[k v]] {(keyword k) v}) body))
          req (assoc req :body body)]
      (handler-fn req))))