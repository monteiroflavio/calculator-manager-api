(ns calculator-manager-api.models.user
  (:require [calculator-manager-api.models.common :as models.common]
            [schema.core :as s]))

(defn- email? [str]
  (re-matches #"/^[a-z0-9.]+@[a-z0-9]+\.[a-z]+\.([a-z]+)?$/gi" str))

(s/defschema Email
  (s/pred email? "Not an email."))

(def user-skeleton
  {:id       {:schema s/Uuid}
   :username {:schema Email}
 	 :password {:schema s/Str}
   :status   {:schema models.common/Status}})

(s/defschema User user-skeleton)