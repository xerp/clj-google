(ns clj-google.core
  (:require [clojure.data.json :as json]))


(defn json
  "Convert string to map"
  [data]
  (json/read-str data :key-fn keyword))


