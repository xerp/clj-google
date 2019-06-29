(defproject clj-google "1.5"
  :description "Google APIs Toolbox"
  :url "https://github.com/xerp/clj-google"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.cemerick/url "0.1.1"]
                 [clj-http "3.10.0"]
                 [clj-oauth "1.5.5"]
                 [org.clojure/data.json "0.2.6"]
                 [ring/ring-codec "1.1.2"]
                 [atmos-kernel "0.6.50" :exclusions [compojure
                                                     ring/ring-json
                                                     ring/ring-jetty-adapter
                                                     ring-cors
                                                     ch.qos.logback/logback-classic]]]
  :repositories [["releases" {:url           "https://clojars.org/repo"
                              :username      :env/CLOJAR_USERNAME
                              :password      :env/CLOJAR_PASSWORD
                              :sign-releases false}]]
  :repl-options {:init-ns clj-google.core})
