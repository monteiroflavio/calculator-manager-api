(ns calculator-manager-api.models.common
  (:require [schema.core :as s]))

(defn- positive? [x] (> x 0))

(s/defschema PositiveBigDecimal (s/pred positive? "Not a positive value."))

(def ^:const ACTIVE "A")
(def ^:const INACTIVE "I")

(s/defschema Status (s/enum ACTIVE INACTIVE))