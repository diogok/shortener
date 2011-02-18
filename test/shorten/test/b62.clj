(ns shorten.test.b62
  (:use [shorten.b62] :reload-all)
  (:use [clojure.test :only  [is] ]) 
  (:use [lazytest.deftest :only [deftest ]]))

    (deftest convertion 
     (is (= "b" (number-to-b62 1)))
     (is (= "cb" (number-to-b62 125)))
     (is (= "uJX" (number-to-b62 79099)))
     (is (= 125 (b62-to-number "cb")))
     (is (= 79099 (b62-to-number "uJX")))
     ) 

