(ns calculator-manager-api.wires.db.user
  (:require [calculator-manager-api.models.user :as models.user]
            [schema.core :as s]))

(s/defschema User models.user/user-skeleton)