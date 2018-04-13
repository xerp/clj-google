(defproject clj-google "0.2.0"
  :description "Google APIs Toolbox"
  :url "https://github.com/xerp/clj-google"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "3.8.0"]
                 [clj-oauth "1.5.5"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-codec "1.1.0"]
                 [atmos-kernel "0.6.2" :exclusions [compojure
                                                    ring/ring-json
                                                    ring/ring-jetty-adapter
                                                    ring-cors
                                                    ch.qos.logback/logback-classic]]]
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/CLOJAR_USERNAME
                              :password      :env/CLOJAR_PASSWORD
                              :sign-releases false}]])
