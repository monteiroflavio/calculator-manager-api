(ns calculator-manager-api.services.user-test
  (:require [clojure.test :refer :all]
            [calculator-manager-api.ports.repositories.user :as repositories.user]
            [calculator-manager-api.ports.services.user :as services.user]))

(deftest get!-test
  (testing "should return user model when user exists"
    (with-redefs [repositories.user/get! (fn [_ _] {:id 1 :username "foo@bar.com" :status "A"})]
      (is (= (services.user/get! "foo@bar.com" "123abc")
             {:id 1 :username "foo@bar.com" :status "A"}))))
  
  (testing "should return exception when user does not exist"
    (with-redefs [repositories.user/get! (fn [_ _] {})]
      (is (thrown? Exception
           (services.user/get! "foo@bar.com" "123abc"))))))