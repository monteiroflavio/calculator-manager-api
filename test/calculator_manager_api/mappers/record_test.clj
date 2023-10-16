(ns calculator-manager-api.mappers.record-test
  (:require [calculator-manager-api.mappers.record :refer [db->internal
                                                           db->internals]]
            [clojure.test :refer :all]))

(def db-record {:users/id                   1
                :users/username             "foo@bar.com"
                :users/status               "A"
                :operations/id              1
                :operations/type            "credit"
                :operations/cost            3M
                :records/id                 1
                :records/user-balance       10M
                :records/amount             10M
                :records/operation-response "1"
                :records/date               "2023-01-01"
                :records/status             "A"})

(def record {:id 1,
             :user {:id 1,
                    :username "foo@bar.com",
                    :status "A"},
             :operation {:id 1,
                         :type "credit",
                         :cost 3M},
             :user-balance 10M,
             :amount 10M,
             :operation-response "1",
             :date "2023-01-01",
             :status "A"})

(deftest db->internal-test
  (testing "Should return an record map from db internal"
    (is (= (db->internal db-record) record))))

(deftest db->internals-test
  (testing "Should return an record map list from db internals"
    (is (= (db->internals [db-record]) [record]))))