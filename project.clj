(defproject shorten "0.1"
  :description "URL shorten using clojure"
  :dependencies [ [org.clojure/clojure "1.3.0-alpha4"]
                  [org.clojure.contrib/json "1.3.0-alpha4"] 
                  [redis-clojure "1.2.6-alpha4"] 
                  [ring/ring-core "0.3.5"] 
                  [ring/ring-jetty-adapter "0.3.5" ]
                  [compojure "0.5.3"]]
  :dev-dependencies [[com.stuartsierra/lazytest "2.0.0-SNAPSHOT"]] 
  :repositories {"central"  "http://repo1.maven.org/maven2/" 
                 "stuartsierra.com snapshots" "http://stuartsierra.com/m2snapshots/"
                 "stuartsierra.com" "http://stuartsierra.com/maven2"})


