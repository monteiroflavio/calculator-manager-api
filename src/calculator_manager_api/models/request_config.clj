(ns calculator-manager-api.models.request-config
  (:require [schema.core :as s]))

(s/defschema Method
  (s/enum :get :post :put :patch :delete))

(def request-config-skeleton
  {:url          s/Str
   :method       Method
   :options      (s/maybe s/Any)
   :query-params s/Any
   :on-success   (s/=> s/Any)
   :on-error     (s/=> s/Any)})

(s/defschema RequestConfig request-config-skeleton)