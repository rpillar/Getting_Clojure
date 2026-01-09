(ns demo.fundementals-4
  (:require [clojure.string :as string]))

;; transformations
;; functions - 'higher-order functions'

;; the names list from fund-1
(def my-names-list (string/split-lines (slurp "/usr/share/dict/propernames")))

;; a function that 'takes' a function as its arg and applies that to a 
;; random element in the names list (using 'rand-nth')
(defn transform-name [transform-fn]
  (transform-fn (rand-nth my-names-list)))
;; pass the 'identity' function - just returns whatever it is passed
(transform-name identity)
;; or 'string/upper-case'
(transform-name string/upper-case)

;; functions can return a function
(defn make-add [x]
  (fn [y]
    (+ x y)))
(def increase (make-add 1))
(def decrease (make-add -1))
(increase 2) ;; 3
(decrease 10) ;; 9

;; take a 'sequence' and return a 'lazy' sequence (only consuming as much space as is needed)
(type my-names-list) ;; clojure.lang.PersistentVector
(type (take 5 my-names-list)) ;; clojure.lang.LazySeq
(take 5 my-names-list) ;; ("Aaron" "Adam" "Adlai" "Adrian" "Agatha")

;; another example - 'iterate' takes a function and a starting point and calls that
;; function repeastedly
(take 10 (iterate increase 0)) ;; (0 1 2 3 4 5 6 7 8 9)

;; filter a sequence
(filter (fn [name]
          (string/ends-with? name "x"))
        my-names-list)

(filter (fn [name]
          (string/includes? name "anne"))
        my-names-list)

;; map - upper-case all names
(map (fn [name]
       (string/upper-case name))
     my-names-list)

;; 'filter' and 'map' in one go -> 'keep'
;; but 'nil' elements are ignored
(keep (fn [name]
        (when (string/ends-with? name "x")
          (string/upper-case name)))
      my-names-list)

;; 'some' performs a similar function but only returns the first element
(some (fn [name]
        (when (string/ends-with? name "x")
          (string/upper-case name)))
      my-names-list)


;; use 'mapcat' to expand / contract a list
(mapcat (fn [name]
          [(string/upper-case name)
           (string/lower-case name)])
        my-names-list) ;; returns a expanded list with upper and lower case names

;; 'group-by' - creates a 'map' where the 'key' is the result of a function
;; in this example we group the names by the value of the first letter
;; note that this returns a 'has-map' so elements are not ordered.
(group-by (fn [name]
            (subs name 0 1))
          my-names-list)

;; to sort the above
;; note that what is returned is NOT a 'hash-map' but an array
(sort-by key
         (group-by (fn [name]
                     (subs name 0 1))
                   my-names-list))

;; to return a 'sorted' map
(into (sorted-map)
      (group-by (fn [name]
                  (subs name 0 1))
                my-names-list))

;; 'reduce' - takes a function and applys that to the supplied collection
;; returning a 'single' value
;; note that we have not supplied a 'starting' value so in this case it will 'default' to zero
(reduce (fn [acc el]
          (+ acc el))
        [1 2 3 4 5])

;; but we can supply a 'starting' value for the 'accumulator' - this is important
;; if we wanted to 'multiply' all elements 
(reduce (fn [acc el]
          (+ acc el))
        10
        [1 2 3 4])

;; 'reduce' can be used to solve a lot of the 'problems' we see above
;; for example - 'filter'
;; note - 'conj' in this case appends the 'element' to the ' result'
;; note - 'empty' will give us an empty thing .....
(defn new-filter [test-fn coll]
  (reduce (fn [result element]
            (if (test-fn element)
              (conj result element)
              result))
          (empty coll)
          coll))

;; using out new 'filter' function we can filter the names list - returning a 'vector'
(new-filter (fn [name]
              (string/ends-with? name "x"))
            my-names-list)

;; as a further example we can do the same for 'map'
(defn new-map [transform-fn coll]
  (reduce (fn [result element]
            (conj result (transform-fn element)))
          (empty coll)
          coll))

(new-map (fn [e] (* e 2)) [1 2 3 4 5])

;; in these examples we have written functions - but we can ' short-hand' some of these. In this
;; example '+' is a function
(reduce + [1 2 3 4])
(reduce * 10 [1 2 3 4]) ;; here we provide a 'starting' value for the multiplication

;; we can 'follow' this pattern using 'partial' functions
(def p-increase (partial + 1))
(p-increase 2) ;; giving us the result '3'
;; 'partial' takes a function and a list of arguments and 'gives' us a new function with all
;; those arguments pre-pended to the argument list
;; note that 'p-increase' is equivelent to :-
;; (defn increase [y]
;;    (+ 1 y))

(filter (partial = "Winston") my-names-list)

;; finally - complex transformations can be expressed in a better way using the 'threading' macro preventing
;; the need for having many nested functions
;; in this example the vector [1 2 3 4] is passed as the second argument to 'map' etc.
(->> [1 2 3 4]
     (map inc)
     (filter (fn [x] (> x 3)))
     )

;; we can also use '->' to ensure that the argument is passed as the 'first' - this case 'assoc' and 
;; 'update' expect the 'map' as the first arg.
(-> {:a 1}
    (assoc :b 2)
    (update :b inc))

;; using the 'thread-last' to filter our names list
(->> my-names-list
     (filter (fn [name] (string/includes? name "anne"))))

(+ 1 1)