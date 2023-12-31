(ns calculator-manager-api.logics.http-utils
  (:require [clojure.string :as str]
            [schema.core :as s]))

(s/defn build-url :- s/Str
  [url :- s/Str query-params :- clojure.lang.PersistentArrayMap]
  (if-not (empty? query-params)
    (str url "?" (str/join "&" (map (fn [[k v]] (str (name k) "=" v)) query-params)))
    url))

(s/defn map-qs :- (s/maybe clojure.lang.PersistentArrayMap)
  [qs :- (s/maybe s/Str)]
  (if (seq qs)
    (->> (str/split qs #"&")
         (map (fn [s] (let [[k, v] (str/split s #"=")] {(keyword k), v})))
         (reduce merge))
    {}))