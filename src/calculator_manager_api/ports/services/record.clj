(ns calculator-manager-api.ports.services.record 
  (:require [calculator-manager-api.adapters.clients.random-org :refer [get-random-string!]]
            [calculator-manager-api.adapters.commons.db :refer [count-adapter]]
            [calculator-manager-api.logics.operation :refer [enough-balance?]]
            [calculator-manager-api.mappers.exception :refer [->not-enough-credit ->not-found]]
            [calculator-manager-api.models.common :as models.common]
            [calculator-manager-api.models.operation :refer [ADDITION DIVISION
                                                             MULTIPLICATION
                                                             RANDOM-STRING SQUARE-ROOT SUBTRACTION]]
            [calculator-manager-api.models.record :refer [Record]]
            [calculator-manager-api.ports.repositories.record :as repositories.record]
            [calculator-manager-api.ports.services.operation :as services.operation]
            [schema.core :as s]))

(def ^:private ENTITY "Record")

(defmulti ^:private calculate! (fn [operation-type _ _] operation-type))

(defmethod ^:private calculate! :default [_ _ _ ] nil)

(defmethod ^:private calculate! ADDITION [_ a b] (+ a b))

(defmethod ^:private calculate! SUBTRACTION [_ a b] (- a b))

(defmethod ^:private calculate! MULTIPLICATION [_ a b] (* a b))

(defmethod ^:private calculate! DIVISION [_ a b] (/ a b))

(defmethod ^:private calculate! SQUARE-ROOT [_ a _] (Math/sqrt a))

(defmethod ^:private calculate! RANDOM-STRING [_ _ _] (get-random-string!))

(s/defn list! :- [Record]
  [user-id       :- s/Int
   q             :- (s/maybe clojure.lang.PersistentArrayMap)
   limit         :- (s/maybe s/Int)
   offset        :- (s/maybe s/Int)
   sorting       :- (s/maybe s/Str)
   sorting-field :- (s/maybe s/Str)]
  (let [result (repositories.record/list! user-id models.common/ACTIVE q limit offset sorting sorting-field)] 
    (if (seq result)
      result
      (throw (ex-info "" (->not-found ENTITY))))))

(s/defn insert! :- s/Any
  [user-id      :- s/Int
   operation-id :- s/Int
   a            :- (s/maybe s/Num)
   b            :- (s/maybe s/Num)]
  (let [last-record         (-> user-id (repositories.record/list! nil nil nil nil "desc" "date") (get 0))
        {:keys [cost type]} (services.operation/get! operation-id)
        user-balance        (:amount last-record)
        enough-balance?     (enough-balance? user-balance cost)
        amount              (- user-balance cost)
        operation-response  (calculate! type a b)]
    (if enough-balance?
      (repositories.record/insert! operation-id user-id amount user-balance operation-response)
      (throw (ex-info "" (->not-enough-credit ENTITY user-balance cost))))))

(s/defn delete! :- s/Any
  [id :- s/Int]
  (let [update-count (-> (repositories.record/delete! id) count-adapter)]
    (if (> update-count 0)
      nil
      (throw (ex-info "" (->not-found ENTITY))))))