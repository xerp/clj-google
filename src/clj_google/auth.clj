(ns clj-google.auth
  (:require [clj-http.client :as http]
            [cemerick.url :refer [url url-encode]]
            [clojure.string :as string]
            [clj-google.core :refer [json]]
            [clojure.java.browse :refer [browse-url]])
  (:import (clojure.lang ExceptionInfo)))

(def ^:dynamic *access-token* nil)

(defn consent-url
  "Get the google tokens, with user consent.

  where:
  - response-type could be :code or :token
  - access-type could be :online or :offline"

  [credentials response-type access-type]
  (let [request-url "https://accounts.google.com/o/oauth2/v2/auth"
        scopes (:scopes credentials)
        scopes (if (vector? scopes) (string/join " " scopes) scopes)
        consent-url (assoc (url request-url)
                      :query {:client_id     (:client-id credentials)
                              :redirect_uri  (:redirect-uri credentials)
                              :scope         scopes
                              :response_type (name response-type)
                              :access_type   (name access-type)})]
    (browse-url (str consent-url))))



(defn access-token
  [credentials token]
  (let [request-url "https://www.googleapis.com/oauth2/v4/token"
        grant-type-str (string/replace (name (:type token)) #"-" "_")
        data {:client_id                       (:client-id credentials)
              :client_secret                   (:client-secret credentials)
              :redirect_uri                    (:redirect-uri credentials)
              :grant_type                      grant-type-str
              (case (keyword (:type token))
                :authorization-code :code
                :refresh-token :refresh_token) (:code token)}]
    (try
      (if-let [response (http/post request-url {:form-params data})]
        (let [response-data (json (:body response))]
          response-data))
      (catch ExceptionInfo _ false))))

(defmacro with-token
  [credentials token & body]
  `(if-let [access-info# (access-token ~credentials ~token)]
     (binding [*access-token* (:access_token access-info#)]
       (do
         ~@body))))
