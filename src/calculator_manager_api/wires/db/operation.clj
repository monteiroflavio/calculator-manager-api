(ns calculator-manager-api.wires.db.operation
  (:require [calculator-manager-api.models.common :refer [Real]]
            [calculator-manager-api.models.operation :refer [Type]]
            [schema.core :as s]))

(def operation-skeleton
  #:operations{:id   s/Int
               :type Type
               :cost Real})

(s/defschema Operation operation-skeleton)