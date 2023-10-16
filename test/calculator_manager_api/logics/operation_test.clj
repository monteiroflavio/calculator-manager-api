(ns calculator-manager-api.logics.operation-test
  (:require [calculator-manager-api.logics.operation :refer [enough-balance?]]
            [clojure.test :refer :all]))

(deftest name-test
  (testing "Should return true when user-balance greater than operation-cost"
    (is (= (enough-balance? 10M 9M) true)))
  
  (testing "Should return true when user-balance equals operation-cost"
    (is (= (enough-balance? 10M 10M) true)))
  
  (testing "Should return false when user-balance equals operation-cost"
    (is (= (enough-balance? 10M 11M) false))))