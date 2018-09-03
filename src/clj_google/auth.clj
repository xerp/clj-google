(ns clj-google.auth
  (:require [clj-http.client :as client]
            [cemerick.url :refer [url url-encode]]))

;(defmacro transform-fn
;  [variable]
;  `(str (string/replace (name '~variable) #"-" "_") "=" ~variable))
;
;(defmacro transform-vector-fn
;  [variable]
;  `(let
;     [transformed# (transform-fn ~variable)
;      splited# (string/split transformed# #"=")]
;     (vector (first splited#) (second splited#))))


(defn consent-url
  [credentials response-type access-type]
  (let [google-auth-url "https://accounts.google.com/o/oauth2/v2/auth"
        consent-url (assoc (url google-auth-url)
                      :query {:client_id     (:client-id credentials)
                              :redirect_uri  (:redirect-uri credentials)
                              :scope         (:scopes credentials)
                              :response_type (name response-type)
                              :access_type   (name access-type)})]
    (str consent-url)))



;(defn access-token
;  [client-credentials code grant-type]
;  (let [request-url "https://www.googleapis.com/oauth2/v3/token"
;        client-id (:client-id client-credentials)
;        client-secret (:client-secret client-credentials)
;        redirect-uri (:redirect-uri client-credentials)
;        form-params (into (hash-map) [(transform-vector-fn code)
;                                      (transform-vector-fn client-id)
;                                      (transform-vector-fn client-secret)
;                                      (transform-vector-fn redirect-uri)
;                                      (transform-vector-fn grant-type)])
;        response (try
;                   (client/post request-url {:form-params form-params})
;                   (catch Exception e ""))
;        response-body (json/parse-string (:body response "") true)]
;    response-body))
