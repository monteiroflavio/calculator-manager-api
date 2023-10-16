(ns calculator-manager-api.mappers.exception-test
  (:require [calculator-manager-api.mappers.exception :refer [->not-enough-credit ->not-found]]
            [calculator-manager-api.models.exception :refer [NOT-ENOUGH-CREDIT NOT-FOUND]]
            [clojure.test :refer :all]))

(deftest ->not-found-test
  (testing "Should build an internal not found exception"
    (is (= (->not-found "Operation") {:entity  "Operation"
                                      :type    NOT-FOUND
                                      :message "Operation not found."})))
  
  (testing "Should build an internal not enough credit exception"
    (is (= (->not-enough-credit "Operation" 10M 11M) {:entity  "Operation"
                                                      :type    NOT-ENOUGH-CREDIT
                                                      :message "Not enough credit - user balance: 10, operation cost: 11"}))))