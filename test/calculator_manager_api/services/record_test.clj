(ns calculator-manager-api.services.record-test
  (:require [calculator-manager-api.models.operation :refer [ADDITION]]
            [calculator-manager-api.ports.repositories.record :as repositories.record]
            [calculator-manager-api.ports.services.operation :as services.operation]
            [calculator-manager-api.ports.services.record :as services.record]
            [clojure.test :refer :all]))

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

(deftest list!-test
  (testing "Should return list of records when they exist on db"
    (with-redefs [repositories.record/list! (fn [_ _ _ _ _ _ _] [record])]
      (is (= (services.record/list! 1 nil nil nil nil nil) [record]))))
  
  (testing "Should throw exception when they don't exist on db"
    (with-redefs [repositories.record/list! (fn [_ _ _ _ _ _ _] [])]
      (is (thrown? Exception (services.record/list! 1 nil nil nil nil nil))))))

(deftest delete!-test
  (testing "Should return count when deletion occurs"
    (with-redefs [repositories.record/delete! (fn [_] [{:next.jdbc/update-count 1}])]
      (is (= (services.record/delete! 1) nil))))
  
  (testing "Should throw exception when deletion does not occur"
    (with-redefs [repositories.record/delete! (fn [_] [{:next.jdbc/update-count 0}])]
      (is (thrown? Exception (services.record/delete! 1))))))

(deftest insert!-test
  (testing "Should return count when operation is addition"
    (with-redefs [repositories.record/list! (fn [_ _ _ _ _ _ _] [record])
                  services.operation/get! (fn [_] {:id 1 :type ADDITION :cost 9M :status "A"})
                  repositories.record/insert! (fn [_ _ _ _ _] {:next.jdbc/update-count 1})]
      (is (= (services.record/insert! 1 2 1M 2M) {:next.jdbc/update-count 1}))))
  
  (testing "Should throw exception when there is no enough credit"
    (with-redefs [repositories.record/list! (fn [_ _ _ _ _ _ _] [record])
                  services.operation/get! (fn [_] {:id 1 :type ADDITION :cost 11M :status "A"})]
      (is (thrown? Exception (services.record/insert! 1 2 1M 2M))))))