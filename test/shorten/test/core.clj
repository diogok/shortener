(ns shorten.test.core
  (:use [shorten.core] :reload-all)
  (:use [clojure.test :only  [is] ]) 
  (:use [lazytest.deftest :only [deftest ]]))

    (deftest next-urls
     (is (= "ab" (next-u "aa")))
     (is (= "ba" (next-u "az")))
     (is (= "aaa" (next-u "zz")))) 

    (deftest will-it-blow?
     (let [n 9999
       zz (apply str (take n (repeat "z"))),
       ex (apply str (take (inc n) (repeat "a")))
       re (next-u zz)]
       (is (= re ex))))

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



