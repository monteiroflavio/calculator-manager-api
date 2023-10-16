(ns calculator-manager-api.mappers.response-test
  (:require [calculator-manager-api.mappers.exception :refer [->not-enough-credit ->not-found]]
            [calculator-manager-api.mappers.response :refer :all]
            [calculator-manager-api.models.response :refer [BAD-REQUEST
                                                            CREATED
                                                            INTERNAL-SERVER-ERROR NO-CONTENT NOT-FOUND OK UNAUTHORIZED]]
            [clojure.test :refer :all]))

(deftest ->ok-test
  (testing "Should return an ok response"
    (is (= (->ok {:foo "bar"}) {:status OK :body {:foo "bar"}}))))

(deftest ->created-test
  (testing "Should return a created response"
    (is (= (->created) {:status CREATED}))))

(deftest ->no-content-test
  (testing "Should return a no-content response"
    (is (= (->no-content) {:status NO-CONTENT}))))

(deftest internal-server-error-response-test
  (testing "Should return an internal server error response"
    (is (= (->exception-response-handler Exception)
           {:status INTERNAL-SERVER-ERROR :body {:message "Unknown error"}}))))

(deftest bad-request-response-test
  (testing "Should return a bad request response"
    (is (= (->exception-response-handler (ex-info "" {:type :schema.core/error :error "some error"}))
           {:status BAD-REQUEST :body {:message "Bad request: some error"}}))))

(deftest not-enough-credit-response-test
  (testing "Should return a not enough credit response"
    (is (= (->exception-response-handler (ex-info "" (->not-enough-credit "Operation" 10M 11M)))
           {:status BAD-REQUEST :body {:message "Not enough credit - user balance: 10, operation cost: 11"}}))))

(deftest unauthorized-response-test
  (testing "Should return an unauthorized response"
    (is (= (->exception-response-handler (ex-info "" {:buddy.auth/type :buddy.auth/unauthorized :message "some error"}))
           {:status UNAUTHORIZED :body {:message "Unauthorized"}}))))

(deftest not-found-response-test
  (testing "Should return a not found response"
    (is (= (->exception-response-handler (ex-info "" (->not-found "Operation")))
           {:status NOT-FOUND :body {:message "Operation not found."}}))))