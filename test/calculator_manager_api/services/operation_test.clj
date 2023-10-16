(ns calculator-manager-api.services.operation-test
  (:require [calculator-manager-api.models.operation :refer [CREDIT]]
            [calculator-manager-api.ports.repositories.operation :as repositories.operation]
            [clojure.test :refer :all]
            [calculator-manager-api.ports.services.operation :as services.operation]))

(deftest list!-test
  (testing "Should list operations when there is some on database"
    (with-redefs [repositories.operation/list! (fn [] [{:id 1 :type CREDIT :status "A"}])]
      (is (= (services.operation/list!) [{:id 1 :type CREDIT :status "A"}]))))
  
  (testing "Should throw exception when there is no operations on db"
    (with-redefs [repositories.operation/list! (fn [] [])]
      (is (thrown? Exception (services.operation/list!) )))))

(deftest get!-test
  (testing "Should return operation when it exists on database"
    (with-redefs [repositories.operation/get! (fn [_] {:id 1 :type CREDIT :status "A"})]
      (is (= (services.operation/get! 1) {:id 1 :type CREDIT :status "A"}))))

  (testing "Should throw exception when it does not exist on db"
    (with-redefs [repositories.operation/get! (fn [_] nil)]
      (is (thrown? Exception (services.operation/get! 1))))))