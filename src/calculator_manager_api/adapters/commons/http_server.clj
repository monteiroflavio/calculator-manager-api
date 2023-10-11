(ns calculator-manager-api.adapters.commons.http-server
  (:require [calculator-manager-api.adapters.config :refer [config]]
            [calculator-manager-api.adapters.routes :refer [all-routes]]
            [org.httpkit.server :as server]))

(defn start-server! []
  (let [port (Integer/parseInt (:port config))]
    (server/run-server all-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))