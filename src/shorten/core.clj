(ns shorten.core
 (:use shorten.b62) 
 (:use clojure.java.io clojure.contrib.json) 
 (:use compojure.core ring.adapter.jetty)
 (:require [redis.core :as redis]))

    (def server  (ref nil)) 
    (def config  (ref {:host "0.0.0.0"
                       :short-host "localhost"
                       :port 8080 
                       :redis {:host "localhost" :port 6379 :db 0}}))

    (defn next-u [s]
     (number-to-b62 (inc (b62-to-number s)))) 

   (defn shorten [long-url]
    (redis/with-server (:redis @config) 
     (let [short-url (next-u (redis/get "last-url"))]
      (redis/atomically
        (redis/set short-url long-url)
        (redis/set "last-url" short-url))
      short-url)))

    (defn unshorten [short-url]
     (redis/with-server (:redis @config) 
      (redis/get short-url))) 

    (defroutes app 
     (GET  "/resolve/:url" [url]
      (let [short-url url]
        (unshorten short-url)))
     (GET  "/:url" [url]
      (let [short-url url]
       {:status 301 :headers {"Location" (unshorten short-url)}}))
     (POST "/shorten" [url]
      (if-let [long-url url] 
        (str "http://" (@config :short-host) ":" (@config :port) "/" (shorten long-url))))) 

    (defn -main [& options]
     (dosync 
      (if (.exists (java.io.File. "config.json")) 
       (ref-set config (read-json (slurp "config.json")))) 
      (if-not (nil? @server) (.stop @server))
      (ref-set server (run-jetty app @config))))

