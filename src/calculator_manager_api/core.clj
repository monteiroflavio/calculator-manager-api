(ns calculator-manager-api.core
  (:gen-class)
  (:require [calculator-manager-api.adapters.commons.http-server :as adapters.http-server]
            [calculator-manager-api.adapters.migrations :as migrations]))

(defn -main
  [& _]
  (migrations/migrate-db!) 
  (adapters.http-server/start-server!))