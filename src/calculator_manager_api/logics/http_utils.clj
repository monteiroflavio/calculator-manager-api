(ns calculator-manager-api.logics.http-utils
  (:require [clojure.string :as string]
            [clojure.string :as str]
            [schema.core :as s]))

(s/defn build-url :- s/Str
  [url :- s/Str query-params :- map]
  (if-not (empty? query-params)
    (str url "?" (string/join "&" (map (fn [[k v]] (str (name k) "=" v)) query-params)))
    url))

(s/defn map-qs :- map
  [qs :- s/Str]
  (if (seq qs)
    (->> (str/split qs #"&")
         (map (fn [s] (let [[k, v] (str/split s #"=")] {(keyword k), v})))
         (reduce merge))
    {}))