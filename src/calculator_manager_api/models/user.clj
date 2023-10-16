(ns calculator-manager-api.models.user
  (:require [calculator-manager-api.models.common :as models.common]
            [schema.core :as s]))

(defn- email? [str]
  (re-matches #"^[a-z0-9.]+@[a-z0-9]+.[a-z]+(.[a-z]+)?$" str))

(s/defschema Email
  (s/pred email? "Not an email."))

(def user-skeleton
  {:id       s/Int
   :username Email
   :status   models.common/Status})

(s/defschema User user-skeleton)