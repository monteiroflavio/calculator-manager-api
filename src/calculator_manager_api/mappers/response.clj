(ns calculator-manager-api.mappers.response 
  (:require [calculator-manager-api.models.exception :refer [NOT-ENOUGH-CREDIT NOT-FOUND]]
            [calculator-manager-api.models.response :as models.response :refer [Response]]
            [schema.core :as s]))

(defn ^:private ->response
  ([status]
   (->response status nil))
  ([status body]
   (-> {:status status}
       (merge
        (when (not (nil? body)) {:body body})))))

(defn ^:private ->exception-response [status message]
  (->response status {:message message}))

(s/defn ->ok [body] :- Response
  {:status models.response/OK :body body})

(s/defn ->created [] :- Response
  (->response models.response/CREATED))

(s/defn ->no-content [] :- Response
  (->response models.response/NO-CONTENT))

(defmulti ->exception-response-handler (fn [e] (:type (ex-data e))))

(defn ^:private general-exception-response [e]
  (println e)
  (->exception-response models.response/INTERNAL-SERVER-ERROR "Unknown error"))

(defmethod ^:private ->exception-response-handler :schema.core/error [e]
  (->exception-response models.response/BAD-REQUEST (str "Bad request: " (:error (ex-data e)))))

(defmethod ^:private ->exception-response-handler NOT-ENOUGH-CREDIT [e]
  (->exception-response models.response/BAD-REQUEST (:message (ex-data e))))

(defmethod ^:private ->exception-response-handler :default [e]
  (if (= (:buddy.auth/type (ex-data e)) :buddy.auth/unauthorized)
    (->exception-response models.response/UNAUTHORIZED "Unauthorized")
    (general-exception-response e)))

(defmethod ^:private ->exception-response-handler NOT-FOUND [e]
  (->exception-response models.response/NOT-FOUND (:message (ex-data e))))