(ns clj-google.core
  (:require [clj-http.client :as client]
            [clj-json.core :as json]))


(defn content
  [url access-token]
  (client/get url {:headers {:authorization (str "Bearer " (get access-token :access_token))}}))


(defn json
  [url access-token]
  (json/parse-string (:body (content url access-token)) true))
