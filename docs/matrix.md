# Google Matrix API

## Usage

```
(ns example
    (:require [clj-google.matrix :as matrix]))  
  
(def google-api-key "YOUR_KEY")  
 
(def origins [LATITUDE LONGITUDE]) ;Could be [[LATITUDE1 LONGITUDE1] [LATITUDE2 LONGITUDE2]]
(def destination [LATITUDE LONGITUDE]) ;Could be [[LATITUDE1 LONGITUDE1] [LATITUDE2 LONGITUDE2]]
  
(matrix/with-api-key google-api-key
  
    (let [url (matrix-url origins destinations)]
 
        (matrix-data url)))
```