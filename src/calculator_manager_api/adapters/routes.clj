(ns calculator-manager-api.adapters.routes
  (:require [calculator-manager-api.adapters.commons.middlewares :refer [authenticated-middleware exception-middleware json->edn_middleware]]
            [calculator-manager-api.adapters.controllers.operation :as controllers.operation]
            [calculator-manager-api.adapters.controllers.record :as controllers.record]
            [calculator-manager-api.adapters.controllers.user :as controllers.user]
            [compojure.core :refer :all]
            [ring.middleware.cors :refer [wrap-cors]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(defn ^:private public-url-builder [url]
  (str "/v1" url))

(defn ^:private private-url-builder [url]
  (-> "/users/:user-id" (str url) public-url-builder))

(defroutes ^:private public-routes
  (POST   (public-url-builder "/authenticate") [] controllers.user/authenticate!))

(defroutes ^:private private-routes
  (GET    (private-url-builder "/operations")  [] controllers.operation/list!) 
  (GET    (private-url-builder "/records")     [] controllers.record/list!)
  (POST   (private-url-builder "/records")     [] controllers.record/insert!)
  (DELETE (private-url-builder "/records/:id") [] controllers.record/delete!))

(def all-routes
  (routes
   (-> #'public-routes
       (wrap-cors
        :access-control-allow-origin [#".*"]
        :access-control-allow-headers ["Content-Type"]
        :access-control-allow-methods [:get :put :post :delete :options])
       (wrap-routes exception-middleware)
       (wrap-routes json->edn_middleware)
       (wrap-routes wrap-json-response)
       (wrap-routes wrap-json-body))
   (-> #'private-routes
       (wrap-cors
        :access-control-allow-origin [#".*"]
        :access-control-allow-headers ["Content-Type" "Authorization"]
        :access-control-allow-methods [:get :put :post :delete :options])
       (wrap-routes authenticated-middleware)
       (wrap-routes exception-middleware)
       (wrap-routes json->edn_middleware)
       (wrap-routes wrap-json-response)
       (wrap-routes wrap-json-body))))