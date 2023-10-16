(ns calculator-manager-api.wires.db.user
  (:require [calculator-manager-api.models.common :refer [Status]]
            [calculator-manager-api.models.user :refer [Email]]
            [schema.core :as s]))

(def user-skeleton
  #:users{:id       s/Int
          :username Email
          :password s/Str
          :status   Status})

(s/defschema User user-skeleton)