(ns calculator-manager-api.models.exception 
  (:require [schema.core :as s]))

(def ^:const NOT-FOUND         :not-found)
(def ^:const NOT-ENOUGH-CREDIT :not-enough-credit)

(def ^:private internal-exception-skeleton
  {:entity  s/Str
   :type    s/Keyword
   :message s/Str})

(s/defschema InternalException internal-exception-skeleton)