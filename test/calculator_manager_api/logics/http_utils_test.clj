(ns calculator-manager-api.logics.http-utils-test
  (:require [calculator-manager-api.logics.http-utils :refer [build-url map-qs]]
            [clojure.test :refer :all]))

(deftest build-url-test
  (testing "Should build URL properly with one parameter"
    (is (= (build-url "foo.bar.com" {:a 1}) "foo.bar.com?a=1")))
  
  (testing "Should build URL properly with more than one parameters"
    (is (= (build-url "foo.bar.com" {:a 1 :b 2}) "foo.bar.com?a=1&b=2")))
  
  (testing "Should build URL properly with none parameters"
    (is (= (build-url "foo.bar.com" {}) "foo.bar.com"))))

(deftest map-qs-test
  (testing "Should return a map from query params string with one parameter"
    (is (= (map-qs "a=1") {:a "1"})))
  
  (testing "Should return a map from query params string more than with one parameters"
    (is (= (map-qs "a=1&b=2") {:a "1" :b "2"})))
  
  (testing "Should return a empty map from query params string none parameters"
    (is (= (map-qs "") {})))
  
  (testing "Should return a empty map from nil"
    (is (= (map-qs nil) {}))))