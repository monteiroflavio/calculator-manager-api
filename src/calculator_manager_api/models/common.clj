(ns calculator-manager-api.models.common
  (:require [schema.core :as s]))

(defn- real? [x] (>= x 0))

(s/defschema Real (s/pred real? "Not a real number."))

(defn- positive-real? [x] (> x 0))

(s/defschema PositiveReal (s/pred positive-real? "Not a postive real number."))

(defn- not-zero? [x] (not= x 0))

(s/defschema NotZero (s/pred not-zero? "Not a positive real number."))

(def ^:const ACTIVE "A")
(def ^:const INACTIVE "I")

(s/defschema Status (s/enum ACTIVE INACTIVE))