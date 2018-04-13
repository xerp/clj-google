(ns clj-google.data
  (:require [clojure.data.json :as json]))

(defn json
  "Convert string to map"
  [data]
  (json/read-str data :key-fn keyword))
