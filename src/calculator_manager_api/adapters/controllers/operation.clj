(ns calculator-manager-api.adapters.controllers.operation
  (:require [calculator-manager-api.mappers.response :refer [->ok]]
            [calculator-manager-api.ports.services.operation :as services.operation]
            [schema.core :as s]))

(s/defn list!
  [_]
  (let [results (services.operation/list!)]
    (->ok results)))