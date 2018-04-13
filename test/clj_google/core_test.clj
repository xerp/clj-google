(ns clj-google.core-test
  (:require [clojure.test :refer :all]
            [clj-google.data :refer :all]
            [clj-google.matrix :refer :all]))

(deftest core-test
  (testing "read json data"
    (let [json-string "{\"a\":1,\"b\":2}"
          json-result {:a 1 :b 2}]

      (is (= (json json-string) json-result)))))


(deftest matrix-test
  (testing "matrix url"
    (let [api-key "EXAMPLE_KEY"
          origins [38.9072 -77.0369]
          destinations [40.7128 -74.0060]

          url-result "https://maps.googleapis.com/maps/api/distancematrix/json?&key=EXAMPLE_KEY&departure_time=now&units=international&traffic_model=pessimistic&origins=38.9072,-77.0369&destinations=40.7128,-74.006"

          matrix-url-result (with-api-key api-key
                                          (matrix-url origins destinations))]

      (is (= matrix-url-result url-result)))))





