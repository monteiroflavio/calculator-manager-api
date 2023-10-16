(ns calculator-manager-api.models.response 
  (:require [schema.core :as s]))

(def ^:const OK 200)
(def ^:const CREATED 201)
(def ^:const NO-CONTENT 204)
(def ^:const BAD-REQUEST 400)
(def ^:const UNAUTHORIZED 401)
(def ^:const NOT-FOUND 404)
(def ^:const INTERNAL-SERVER-ERROR 500)

(s/defschema HttpStatus
  (s/enum OK CREATED NO-CONTENT BAD-REQUEST NOT-FOUND INTERNAL-SERVER-ERROR))

(def response-skeleton
  {:status HttpStatus
   :body (s/maybe {:message s/Str})})

(s/defschema Response response-skeleton)