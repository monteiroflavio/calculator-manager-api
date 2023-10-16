(ns calculator-manager-api.mappers.operation-test
  (:require [calculator-manager-api.mappers.operation :refer [db->internal
                                                              db->internals
                                                              db-record->db-operation]]
            [clojure.test :refer :all]))

(def db-record {:users/id                   1
                :users/username             "foo@bar.com"
                :users/status               "A"
                :operations/id              1
                :operations/type            "credit"
                :operations/cost            3M
                :records/id                 1
                :records/user_balance       10M
                :records/amount             10M
                :records/operation_response "1"
                :records/date               "2023-01-01"
                :records/status             "A"})

(deftest db->internal-test
  (testing "Should return an operation map from db internal"
    (is (= (db->internal #:operations{:id 1 :type "credit" :cost 3M}) {:id 1 :type "credit" :cost 3M}))))

(deftest db->internals-test
  (testing "Should return an operation map list from db internals"
    (is (= (db->internals [#:operations{:id 1 :type "credit" :cost 3M}]) [{:id 1 :type "credit" :cost 3M}]))))

(deftest db-record->db-operation-test
  (testing "Should return a db operation from a db record"
    (is (= (db-record->db-operation db-record) #:operations{:id 1 :type "credit" :cost 3M}))))