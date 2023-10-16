(ns calculator-manager-api.wires.in.user
  (:require [calculator-manager-api.models.user :refer [Email]]
            [schema.core :as s]))

(def authenticate-user-skeleton
  {:username Email
   :password s/Str})

(s/defschema AuthenticateUser
  authenticate-user-skeleton)