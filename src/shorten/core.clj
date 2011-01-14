(ns shorten.core)

    (defn inc-next-1 [nums & b]
     (if (nil? (first nums)) (into b [0]) 
      (if (= (first nums) 25)
       (recur (next nums) (into b [0]))
        (into b (into [(inc (first nums))] (next nums) )))))

    (defn inc-next [nums]
     (reverse (filter number? (inc-next-1 nums) ) )) 

    (defn map-ints [s] 
     (map #(- (int %1) 97) s)) 

    (defn ints-map [ints] 
     (reduce str "" (map #(char (+ %1 97)) ints)))  

    (defn next-u [s] 
     (reduce str ""
      (reverse (ints-map
        (inc-next (reverse (map-ints s)))))))

    (def short-to-long (ref {})) 
    (def long-to-short (ref {})) 
    (def last-url (ref "a")) 

    (defn shorten [long-url]
     (dosync 
      (if-let [short-url (get @long-to-short long-url)] short-url 
       (let [short-url (next-u @last-url)]
        (alter short-to-long #(assoc % short-url long-url)) 
        (alter long-to-short #(assoc % long-url short-url)) 
        (ref-set last-url short-url)))))

    (defn unshorten [short-url]
     (get @short-to-long short-url)) 



