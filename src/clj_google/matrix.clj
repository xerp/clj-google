(ns clj-google.matrix
  (:require [clj-google.data :refer :all]
            [clj-http.client :as http-client]
            [atmos-kernel.configuration :refer [read-edn]]
            [clojure.string :refer [join]]))

; ----------------------------------------------------
; BEGIN VARS
; ----------------------------------------------------
(def ^:private matrix-api (-> :matrix read-edn :api))

(def ^:private api-url (:url matrix-api))
(def ^:private coordinate-separator (:coordinate-separator matrix-api))
(def ^:private coordinates-separator (:coordinates-separator matrix-api))
(def ^:private default-values (:defaults matrix-api))

(def ^:dynamic *api-key* nil)
; ----------------------------------------------------
; END VARS
; ----------------------------------------------------

(defn- stringify-coordinate
  "Transform coordinates to string.
  Ex:
    [LATITUDE LONGITUDE] => LATITUDE|LONGITUDE"
  [coordinates]
  (apply str [(first coordinates) coordinate-separator (second coordinates)]))

(defn- stringify-coordinates
  "Transform list of coordinates to string.
  Ex:
    [[LATITUDE1 LONGITUDE1] [LATITUDE2 LONGITUDE2]] => LATITUDE1|LONGITUDE1,LATITUDE2|LONGITUDE2"
  [coordinates]
  (join coordinates-separator (map stringify-coordinate coordinates)))

(defn- make-coordinates
  "Make coordinates checking if coordinates is a vector or not"
  [coordinates]
  (if (every? vector? coordinates)
    (stringify-coordinates coordinates)
    (stringify-coordinate coordinates)))

(defn matrix-url
  "Build the url accepted by matrix-api to retrieve data"
  ([origins destinations unit-system traffic-model departure-time]
   (let [origin (str "origins=" (make-coordinates origins))
         destinations (str "destinations=" (make-coordinates destinations))

         unit-system (str "units=" unit-system)
         api-key (str "key=" *api-key*)
         traffic-model (str "traffic_model=" traffic-model)
         departure-time (str "departure_time=" departure-time)]
     (join "&" [api-url api-key departure-time unit-system traffic-model origin destinations])))
  ([origins destinations]
   (matrix-url origins destinations
               (:unit default-values)
               (:traffic-model default-values)
               (:departure-time default-values))))

(defn matrix-data
  "Retrieve data from matrix-api"
  [api-url]
  (try
    (if-let [http-response (http-client/get api-url)]
      (let [http-body (json (:body http-response))
            http-data (-> http-body :rows first :elements)]
        http-data)
      false)
    (catch Exception e false)))


(defmacro with-api-key
  "Using the api key provided by google, query the matrix api"
  [api-key & body]
  `(binding [*api-key* ~api-key]
     (do ~@body)))
