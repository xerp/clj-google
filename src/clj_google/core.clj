(ns clj-google.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))


(defn content
  [url access-token]
  (client/get url {:headers {:authorization (str "Bearer " (get access-token :access_token))}}))

(defn json
  [data]
  (json/read-str data :key-fn keyword))
