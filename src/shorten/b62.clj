(ns shorten.b62)

    (defn v2m [v] (let [v (vec v)] (reduce merge (for [n (range 0 (count v))] {n (get v n) })) ))
    (defn v2m-r [v] (let [v (vec v)] (reduce merge (for [n (range 0 (count v))] {(get v n) n})) ))

    (def b62-map [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z
                  \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z
                  \0 \1 \2 \3 \4 \5 \6 \7 \8 \9]) 

    (def int-to-b62-map (v2m b62-map)) 
    (def b62-to-int-map (v2m-r b62-map)) 

    (defn b62-to-number [n]
      (reduce + (map (fn [t] (apply * (get b62-to-int-map (val t)) (repeat (key t) 62))) (v2m (vec (reverse (str n)))))))

    ;; feling lazy right now so I copied the rest from http://lburja.blogspot.com/2010/07/toy-algorithms-with-numbers-in-clojure.html

(defn char-to-digit
  "Converts a character representing a digit between 0 and 9 to the corresponding integer"
  [ch]
   (- (int ch) (int \0)))
 
(defn str-to-digits
  "Converts the string representation of a number to a sequence of digits"
  [str-num]
  (map char-to-digit str-num))
  
    (defn divide-by-62 [num-digits]
      (loop [digits num-digits
             quotient []
             reminder 0]
        (if (empty? digits)
          {:quotient (drop-while zero? quotient), :reminder reminder}
          (let [x (+ (first digits) (* 10 reminder))]
            (recur (rest digits) (conj quotient (quot x 62)) (rem x 62))))))
     
    (defn to-62
      "Converts a sequence of decimal digits to a sequence of binary digits"
      [num-digits]
      (loop [digits num-digits
             bits []]
        (if (empty? digits)
          (reverse bits)
          (let [res (divide-by-62 digits)]
            (recur (:quotient res) (conj bits (:reminder res)))))))

    (defn number-to-b62 [n] (str (reduce str (map (fn [n] (get int-to-b62-map n)) (to-62 (str-to-digits (str n)))))) ) 
