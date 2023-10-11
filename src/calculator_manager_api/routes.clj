(ns calculator-manager-api.routes
  (:require [calculator-manager-api.adapters.controllers.operation :as controllers.operation]
            [calculator-manager-api.adapters.controllers.record :as controllers.record]
            [calculator-manager-api.adapters.controllers.user :as controllers.user]
            [compojure.core :refer :all]
            [compojure.route :as route]))

(defroutes app-routes
  (GET    "/operations"             [] controllers.operation/list!)
  (POST   "/authenticate"           [] controllers.user/authenticate!)
  (GET    "/users/:user-id/records" [] controllers.record/list!)
  (POST   "/users/:user-id/records" [] controllers.record/insert!)
  (DELETE "/records/:id"            [] controllers.record/delete!)
  (route/not-found "You Must Be New Here"))