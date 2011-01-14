(defproject shorten "0.1"
  :description "URL shorten using clojure"
  :dependencies [ [org.clojure/clojure "1.3.0-alpha4"]
                [org.clojars.nathell/redis-clojure "1.2.6-SNAPSHOT"] 
                [ring/ring-core "0.3.5"] 
                [ring/ring-jetty-adapter "0.3.5" ] ]
  :dev-dependencies [[com.stuartsierra/lazytest "2.0.0-SNAPSHOT"]] 
  :repositories {"central"  "http://repo1.maven.org/maven2/" 
                 "stuartsierra.com snapshots" "http://stuartsierra.com/m2snapshots/"
                 "stuartsierra.com" "http://stuartsierra.com/maven2"})

