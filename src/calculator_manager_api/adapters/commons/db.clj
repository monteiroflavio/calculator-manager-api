(ns calculator-manager-api.adapters.commons.db
  (:require [calculator-manager-api.adapters.config :refer [config]]
            [calculator-manager-api.models.common :as models.common]
            [honey.sql :as sql]
            [next.jdbc :as jdbc]))

(def ^:private db-config
  {:dbtype   "postgresql"
   :dbname   (:postgres-db       config)
   :host     (:postgres-host     config)
   :user     (:postgres-username config)
   :password (:postgres-password config)})

(def ^:private db (jdbc/get-datasource db-config))

(defn execute! [sql-expression]
  (jdbc/execute! db sql-expression))

(defn count-adapter [records]
  (:next.jdbc/update-count (get records 0)))

(defn ^:private likefy [s]
  (str "%" s "%"))

(defn ^:private add-unique-table [table column]
  [(str "alter table " table " add unique (" column ")")])

(def add-unique-operation-map
  (add-unique-table "operations" "type"))

(def add-unique-username-map
  (add-unique-table "users" "username"))

(def create-users-table-map
  (-> {:create-table [:users :if-not-exists]
       :with-columns
       [[:id :serial [:not nil]]
        [:username :text [:not nil]]
        [:password :text [:not nil]]
        [:status [:varchar 1] [:not nil] [:default models.common/ACTIVE]] 
        [[:primary-key :id]]]}
      sql/format))

(def create-operations-table-map
  (-> {:create-table [:operations :if-not-exists]
       :with-columns
       [[:id :serial [:not nil]]
        [:type :text [:not nil]]
        [:cost :float [:not nil]]
        [[:primary-key :id]]]}
      sql/format))

(def create-records-table-map
  (-> {:create-table [:records :if-not-exists]
       :with-columns
       [[:id :serial [:not nil]]
        [:operation-id :int [:not nil] [:references :operations :id]]
        [:user-id :int [:not nil] [:references :users :id]]
        [:amount :float [:not nil]]
        [:user-balance :float [:not nil]]
        [:operation-response :text]
        [:date :timestamp [:not nil] [:default :CURRENT_TIMESTAMP]]
        [:status [:varchar 1] [:not nil] [:default models.common/ACTIVE]]
        [[:primary-key :id]]]}
      sql/format))

(defn insert-operations-map [operations]
  (-> {:insert-into [:operations [:type :cost]]
       :values operations
       :on-conflict :type
       :do-nothing true}
      sql/format))

(defn insert-user-map [username password]
  (-> {:insert-into [:users [:username :password]]
       :values [[username password]]
       :on-conflict :username
       :do-nothing true}
      sql/format))

(defn insert-record-map
  [operation-id user-id amount user-balance response status]
  (-> {:insert-into [:records [:operation-id
                               :user-id
                               :amount
                               :user-balance
                               :operation-response
                               :status]]
       :values [[operation-id user-id amount user-balance response status]]}
      sql/format))

(defn update-record-map
  [id status]
  (-> {:update :records
       :set {:status status}
       :where [:= :id id]}
      sql/format))

(defn list-user-map [username password]
  (-> {:select [:u.*]
       :from [[:users :u]]
       :where [:and [:= :u.username username] [:= :u.password password]]}
      sql/format))

(defn list-record-map
  [user-id status q limit offset sorting sorting-field]
  (let [sorting (if (not (nil? sorting)) (keyword sorting) :asc)
        status-filter (if (not (nil? status)) [status] [models.common/ACTIVE models.common/INACTIVE])
        where-list [:and [:= :u.id user-id] [:in :r.status status-filter]]
        where-list (if (not (nil? q))
                     (conj where-list [:or [:like :u.username (likefy q)] [:like :o.type (likefy q)]])
                     where-list)]
    (-> {:select [:*]
         :from [[:records :r]]
         :join-by [:join [[:users :u]
                          [:= :r.user-id :u.id]]
                   :join [[:operations :o]
                          [:= :r.operation-id :o.id]]]
         :where where-list}
        (merge
         (when (not (nil? sorting-field)) {:order-by [[(keyword sorting-field) sorting]]})
         (when (not (nil? limit))         {:limit (Integer/parseInt limit)})
         (when (not (nil? offset))        {:offset (Integer/parseInt offset)}))
        sql/format)))

(defn list-operation-map
  [id]
  (let [where-list (if (not (nil? id)) {:where [:= :o.id id]} {})]
    (-> {:select [:*]
         :from [[:operations :o]]}
        (merge where-list)
        sql/format)))