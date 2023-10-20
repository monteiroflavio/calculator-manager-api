(ns calculator-manager-api.wires.db.operation
  (:require [calculator-manager-api.models.common :refer [Real Status]]
            [calculator-manager-api.models.operation :refer [Type]]
            [schema.core :as s]))

(def operation-skeleton
  #:operations{:id   s/Int
               :type Type
               :cost Real
               :status Status})

(s/defschema Operation operation-skeleton)