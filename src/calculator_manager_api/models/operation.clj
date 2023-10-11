(ns calculator-manager-api.models.operation
  (:require [calculator-manager-api.models.common :refer [PositiveBigDecimal]]
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
  {:id   {:schema Integer}
   :type {:schema Type}
   :cost {:schema PositiveBigDecimal}})

(s/defschema Operation operation-skeleton)