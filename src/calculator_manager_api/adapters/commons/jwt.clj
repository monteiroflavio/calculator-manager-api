(ns calculator-manager-api.adapters.commons.jwt
  (:require [calculator-manager-api.adapters.config :refer [config]]
            [buddy.sign.jwt :as jwt]))

(defn jwt-sign [id]
  (jwt/sign {:user id} (:secret config)))