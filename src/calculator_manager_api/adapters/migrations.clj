(ns calculator-manager-api.adapters.migrations
  (:require [calculator-manager-api.adapters.commons.db :refer [add-unique-operation-map
                                                                add-unique-username-map
                                                                count-adapter
                                                                create-operations-table-map
                                                                create-records-table-map
                                                                create-users-table-map
                                                                execute!
                                                                insert-operations-map
                                                                insert-record-map
                                                                insert-user-map]]
            [calculator-manager-api.adapters.config :refer [config]]
            [calculator-manager-api.models.operation :as models.operation]
            [calculator-manager-api.models.common :as models.common]))

(defn ^:private insert-defaults-map! []
  (let [operations [[models.operation/CREDIT         0]
                    [models.operation/ADDITION       50]
                    [models.operation/SUBTRACTION    50]
                    [models.operation/MULTIPLICATION 250]
                    [models.operation/DIVISION       250]
                    [models.operation/SQUARE-ROOT    800]
                    [models.operation/RANDOM-STRING  500]]
        username "foo@bar.com"
        password "AYag$vzzM/zXtSBs=$mI+9+Q3/yjkzvxEb2u1X9Sc3LNM="
        initial-credit (Float/parseFloat (:initial-credit config))
        update-count (-> (insert-user-map username password) execute! count-adapter)]
    (execute! (insert-operations-map operations))
    (when (> update-count 0)
      (execute! (insert-record-map 1 1 initial-credit 0.0M nil models.common/INACTIVE)))))

(defn ^:private migrate-users! []
  (execute! create-users-table-map)
  (execute! add-unique-username-map))

(defn ^:private migrate-operations! []
  (execute! create-operations-table-map)
  (execute! add-unique-operation-map))

(defn ^:private migrate-records! []
  (execute! create-records-table-map))

(defn migrate-db! []
  (migrate-users!)
  (migrate-operations!)
  (migrate-records!)
  (insert-defaults-map!))