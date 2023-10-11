(ns calculator-manager-api.models.request-config
  (:require [schema.core :as s]))

(s/defschema Method
  (s/enum :get :post :put :patch :delete))

(s/defschema HandlerParameters [s/Any s/Any s/Any])

(def request-config-skeleton
  {:url          {:schema s/Str}
   :method       {:schema Method}
   :options      {:schema (s/maybe map)}
   :query-params {:schema map}
   :on-success   {:schema (s/=> HandlerParameters)}
   :on-error     {:schema (s/=> HandlerParameters)}})

(s/defschema RequestConfig request-config-skeleton)