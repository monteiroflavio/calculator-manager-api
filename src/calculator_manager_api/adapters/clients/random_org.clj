(ns calculator-manager-api.adapters.clients.random-org
  (:require [calculator-manager-api.adapters.commons.http-client :as commons.http-client]))

(def config {:url          "https://www.random.org/strings"
             :method       :get
             :options      nil
             :query-params {:num 1
                            :len 32
                            :digits "on"
                            :upperalpha "on"
                            :loweralpha "on"
                            :format "plain"}
             :on-success   (fn [_ _ body] body)
             :on-error     (fn [_ _ error] error)})

(defn get-random-string! []
  (commons.http-client/request! config))