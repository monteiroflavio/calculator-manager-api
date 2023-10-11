(ns calculator-manager-api.adapters.commons.http-server
  (:require [calculator-manager-api.config :refer [config]]
            [calculator-manager-api.middlewares :refer [exception-middleware]]
            [calculator-manager-api.routes :as routes]
            [org.httpkit.server :as server]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]))

(def ^:private handler
  (-> routes/app-routes
      exception-middleware
      wrap-json-response
      wrap-json-body))

(defn start-server! []
  (let [port (Integer/parseInt (:port config))]
    (server/run-server handler {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))