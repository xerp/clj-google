(ns google.drive
  (:require [google.oauth :as oauth]
            [google.core :refer [json]]
            [ring.util.codec :as codec]
            [clojure.string :as string]))


(defn query
  [body]
  (let [translated (map (fn [x]
                          (let [[operator field value escape] x]
                            (vector field (name operator) (if escape value (str "'" value "'" ))))) body)
        each-joined (map #(string/join " " %) translated)
        joined (string/join " and " each-joined)
        encoded (codec/url-encode joined)]
    encoded))


(defn file
  [token file-id]
  (json (str "https://www.googleapis.com/drive/v2/files/" file-id) token))

(defn files
  ([token query-list]
   (let [url "https://www.googleapis.com/drive/v2/files"
         fix-url (if (seq query-list) (str url "?q=" (query query-list)) url)]
     (:items (json fix-url token))))
  ([token]
   (files token nil)))

(defn children
  [token folder-id]
  (:items (json (str "https://www.googleapis.com/drive/v2/files/" folder-id "/children") token)))

(def client-credentials {:client-id ""
                         :client-secret ""
                         :redirect-uri ""})

(def scopes [])


(oauth/consent-url client-credentials scopes)

(def token (oauth/access-token client-credentials "" "authorization_code"))

(files token
        [[:in "'{folder-id}'" "parents" true]
         [:= "trashed" false true]])

(children token "{folder-id}")
