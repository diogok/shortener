(ns shorten.test.core
  (:use [shorten.core] :reload-all)
  (:use [shorten.b62] :reload-all)
  (:use [clojure.test :only  [is] ]) 
  (:use [lazytest.deftest :only [deftest ]]))

    (deftest next-urls
     (is (= "c" (next-u "b")))
     (is (= "Aab" (next-u "Aaa")))
     (is (= "zA" (next-u "zz")))) 

    (deftest short-urls
     (let [long-url "http://diogok.net"
           short-url (shorten long-url)]
       (is (= long-url (unshorten short-url))))) 

    (deftest not-short-twice
     (let [long-url "http://diogok.net"
           s1 (shorten long-url)
           s2 (shorten long-url)]
           (is (= s1 s2 )))) 

    (deftest timing-serial
     (time (dotimes [n 99999] (shorten (str "http://diogok.net/" n) )))) 



