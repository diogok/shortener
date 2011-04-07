(ns shorten.test.core
  (:use [shorten.core] :reload-all)
  (:use [shorten.b62] :reload-all)
  (:use [clojure.test :only  [is] ]) 
  (:use [lazytest.deftest :only [deftest]]))

    (deftest next-urls
     (is (= "c" (next-u "b")))
     (is (= "Aab" (next-u "Aaa")))
     (is (= "zA" (next-u "zz")))) 

    (deftest short-urls
     (let [long-url "http://diogok.net"
           short-url (shorten long-url)]
       (is (= long-url (unshorten short-url))))) 

