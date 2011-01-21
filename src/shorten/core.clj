(ns shorten.core
 (:use clojure.java.io clojure.contrib.base64) 
 (:use compojure.core ring.adapter.jetty))

    (def *log*  "urls.aof") 
    (def *logger* (agent *log*)) 

    (defn log [url] 
     (with-open [wtr (writer *log* :append true)] 
       (spit wtr (str (encode-str url) "\n" ))))

    (defn replay [fun] 
     (with-open [rdr (reader *log*)]
      (dorun (map #(fun (decode-str %)) (line-seq rdr)))))

    (defn next-u [s]
     (Integer/toString (inc (Integer/parseInt s 36)) 36)) 

    (def short-to-long (ref {})) 
    (def long-to-short (ref {})) 
    (def last-url (ref "a")) 

    (defn shorten [long-url]
     (dosync 
      (if-let [short-url (get @long-to-short long-url)] short-url 
       (let [short-url (next-u @last-url)]
        (send *logger* (fn [a] (log long-url))) 
        (alter short-to-long #(assoc % short-url long-url)) 
        (alter long-to-short #(assoc % long-url short-url)) 
        (ref-set last-url short-url)))))

    (defn unshorten [short-url]
     (get @short-to-long short-url)) 

    (def server (atom nil)) 
    (def conf   (atom nil)) 

    (defroutes app 
     (GET  "/resolve/:url" [url]
      (let [short-url url]
        (unshorten short-url) ) )
     (GET  "/:url" [url]
      (let [short-url url]
       {:status 301 :headers {"Location" (unshorten short-url) }} ))
     (POST "/shorten" [url]
      (let [long-url url] 
        (str "http://" (@conf :host) ":" (@conf :port) "/" (shorten long-url) )))) 

    (defn -main [& options]
     (let [ host (if (first options) (first options) "localhost")
            port (if (second options) (Integer/parseInt (second options) ) 8080)
            config {:host host :port port :join? false} ] 
     (if-not (nil? @server) (.stop @server))
     (replay shorten) 
     (swap! conf (fn [c] config)) 
     (swap! server (fn [s] (run-jetty app config )))))

