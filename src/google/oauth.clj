(ns google.oauth
  (:require [clojure.string :as string]
            [clj-http.client :as client]
            [clj-json.core :as json]))


(defmacro transform-fn
  [variable]
  `(str (string/replace (name '~variable) #"-" "_") "=" ~variable))

(defmacro transform-vector-fn
  [variable]
  `(let
     [transformed# (transform-fn ~variable)
      splited# (string/split transformed# #"=")]
     (vector (first splited#) (second splited#))))

(defn consent-url
  [client-credentials scopes]
  (let [token-url "https://accounts.google.com/o/oauth2/auth"
        response-type "code"
        redirect-uri (:redirect-uri client-credentials)
        client-id (:client-id client-credentials)
        scope (string/join "%20" scopes)
        request-url (vector (str token-url "?" (transform-fn scope)) (transform-fn redirect-uri) (transform-fn response-type) (transform-fn client-id))]
    (string/join "&" request-url)))



(defn access-token
  [client-credentials code grant-type]
  (let [request-url "https://www.googleapis.com/oauth2/v3/token"
        client-id (:client-id client-credentials)
        client-secret (:client-secret client-credentials)
        redirect-uri (:redirect-uri client-credentials)
        form-params (into (hash-map) [(transform-vector-fn code)
                                      (transform-vector-fn client-id)
                                      (transform-vector-fn client-secret)
                                      (transform-vector-fn redirect-uri)
                                      (transform-vector-fn grant-type)])
        response (try
                   (client/post request-url {:form-params form-params})
                   (catch Exception e ""))
        response-body (json/parse-string (:body response "") true)]
    response-body))
