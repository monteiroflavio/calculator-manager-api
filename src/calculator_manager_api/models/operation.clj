(ns calculator-manager-api.models.operation
  (:require [calculator-manager-api.models.common :refer [Real]]
            [schema.core :as s]))

(def ^:const CREDIT         "credit")
(def ^:const ADDITION       "addition")
(def ^:const SUBTRACTION    "subtraction")
(def ^:const MULTIPLICATION "multiplication")
(def ^:const DIVISION       "division")
(def ^:const SQUARE-ROOT    "square-root")
(def ^:const RANDOM-STRING  "random-string")

(s/defschema Type
  (s/enum CREDIT ADDITION SUBTRACTION MULTIPLICATION DIVISION SQUARE-ROOT RANDOM-STRING))

(def operation-skeleton
  {:id   s/Int
   :type Type
   :cost Real})

(s/defschema Operation operation-skeleton)