(ns calculator-manager-api.mappers.user-test
  (:require [calculator-manager-api.mappers.user :refer [db->internal
                                                         db-record->db-user]]
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
  (testing "Should return an user map from db internal"
    (is (= (db->internal #:users{:id 1 :username "foo@bar.com" :status "A"}) {:id 1 :username "foo@bar.com" :status "A"}))))

(deftest db-record->db-user-test
  (testing "Should return a db operation from a db record"
    (is (= (db-record->db-user db-record) #:users{:id 1 :username "foo@bar.com" :status "A"}))))