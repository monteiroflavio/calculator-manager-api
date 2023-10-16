(ns calculator-manager-api.core
  (:gen-class)
  (:require [calculator-manager-api.adapters.commons.http-server :as adapters.http-server]
            [calculator-manager-api.adapters.migrations :as migrations]
            [schema.core :as s]))

(defn -main
  [& _]
  (s/set-fn-validation! true)
  (migrations/migrate-db!) 
  (adapters.http-server/start-server!))