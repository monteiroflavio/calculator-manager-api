(ns calculator-manager-api.mappers.operation-test
  (:require [calculator-manager-api.mappers.operation :refer [db->internal
                                                              db->internals]]
            [clojure.test :refer :all]))

(deftest db->internal-test
  (testing "Should return an operation map from db internal"
    (is (= (db->internal #:operations{:id 1 :type "credit" :cost 3M}) {:id 1 :type "credit" :cost 3M}))))

(deftest db->internals-test
  (testing "Should return an operation map list from db internals"
    (is (= (db->internals [#:operations{:id 1 :type "credit" :cost 3M}]) [{:id 1 :type "credit" :cost 3M}]))))