(ns clj-google.drive
  (:require [clj-google.core :refer [json]]
            [ring.util.codec :as codec]
            [clojure.string :as string]
            [clj-http.client :as http]
            [clojure.java.io :as java]))


(defn query
  [body]
  (let [translated (map (fn [x]
                          (let [[operator field value escape] x]
                            (vector field (name operator) (if escape value (str "'" value "'"))))) body)
        each-joined (map #(string/join " " %) translated)
        joined (string/join " and " each-joined)
        encoded (codec/url-encode joined)]
    encoded))


(defn file
  [token file-id]
  (json (str "https://www.googleapis.com/drive/v2/files/" file-id)))

(defn files
  ([token query-list]
   (let [url "https://www.googleapis.com/drive/v2/files"
         fix-url (if (seq query-list) (str url "?q=" (query query-list)) url)]
     (:items (json fix-url))))
  ([token]
   (files token nil)))

(defn children
  [token folder-id]
  (:items (json (str "https://www.googleapis.com/drive/v2/files/" folder-id "/children"))))


(defn download
  [token directory g-file]
  (let [file-stream-response (http/get
                               (str "https://www.googleapis.com/drive/v2/files/" (:id g-file) "?alt=media")
                               {:headers
                                    {:Authorization (str "Bearer " (:access_token token))}
                                :as :stream})]
    (try
      (do
        (java/copy (:body file-stream-response)
                   (java/file (str directory (:title g-file))))
        true)
      (catch Exception e false))))






