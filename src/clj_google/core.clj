(ns clj-google.core
  (:require [clj-http.client :as client]))

(defn content
  [url access-token]
  (client/get url {:headers {:authorization (str "Bearer " (get access-token :access_token))}}))

