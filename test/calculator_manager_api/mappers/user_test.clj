(ns calculator-manager-api.mappers.user-test
  (:require [calculator-manager-api.mappers.user :refer [db->internal]]
            [clojure.test :refer :all]))

(deftest db->internal-test
  (testing "Should return an user map from db internal"
    (is (= (db->internal #:users{:id 1 :username "foo@bar.com" :status "A"}) {:id 1 :username "foo@bar.com" :status "A"}))))