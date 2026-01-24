(ns functional-things)

(def dracula {:title "Dracula"
              :genre "Horror"
              :price 8.99})

(def war-and-peace {:title "War and Peace"
                    :genre "History"
                    :price 17.99})

(defn cheap? [book]
  (<= (:price book) 10.00))

(defn horror? [book]
  (= (:genre book) "Horror"))

(cheap? dracula) ;; true
(horror? dracula) ;; true
(cheap? war-and-peace) ;; false

;; we have two functions that check whether a book is expensive or not amd
;; whether a book belongs to the 'horror' genre. Not very interesting.

;; However in Clojure the 'interesting' bit about functions are that they are 
;; 'values'. So we can assign a function to a variable :-

(def cheap-book? cheap?)
(cheap-book? dracula) ;; true

;; so we can 'pass' functions as arguments - here is a trivial example.

(defn check-dracula [f]
  (f dracula))

(check-dracula cheap?) ;; true
(check-dracula horror?) ;; true

;; expanding on this it is possible to use functions to check both 
;; charateristics of a book by passing those functions as arguments

(defn both? [f1 f2 book]
  (when (and (f1 book)
             (f2 book))
    book))

(both? cheap? horror? dracula) ;; {:title "Draculat" ......}

;; it is also possible to create 'anon' functions that can be 'bound'
;; to a variable name.
(fn [x y] (+ x y))

(def anon-add (fn [x y] (+ x y)))
(anon-add 1 2) ;; 3

;; on that basis functions can return functions
(defn buy-this-book [max-price]
  (fn [book]
    (when (<= (:price book) max-price)
      book)))

;; we can then create a set of functions
(def very-cheap? (buy-this-book 1.99))
(def medium-cheap? (buy-this-book 10.00))
(def expensive? (buy-this-book 20.00))
(very-cheap? dracula)
(medium-cheap? war-and-peace)
(expensive? war-and-peace)

;; using 'apply'

;; so instead of the following .....
(+ 1 2 3 4 5) ;; 15

;; we can do the following .....
(def add-function +)
(def args [1 2 3 4 5])
(apply add-function args) ;; 15

;; there is also 'partial' .....
;; partailly fill-in the arguments to a function. For example :-
(defn cheaper-than [max-price book]
  (when (<= (:price book) max-price)
    book))

(def real-cheap? (partial cheaper-than 1.00))
(def sort-of-cheap? (partial cheaper-than 1.99))
(def this-could-be-cheap? (partial cheaper-than 5.99))

;; each call to 'partial' returns a 'new' function that when called 
;; 'calls' cheaper-than with one of the prices as its first arg.
(this-could-be-cheap? dracula)

;; another useful function is 'complement' - this 'creates' the opposite of a function - in other
;; words it wraps a function with a call to 'not'.
(def not-horror? (complement horror?))
(not-horror? dracula) ;; false
(not-horror? war-and-peace) ;; true

;; yet another useful function 'generator' is 'every-pred'. This 'ands' together a set of predicate
;; functions - any number of arguments (functions) including anonymous functions
(def cheap-horror? (every-pred horror? cheap?))
(cheap-horror? dracula) ;; true

;; function literals - or lambdas
;; this is really a more concise way of writing (fn [x y] (+ x y)) except that the syntax is a little 
;; different.

;; this first example takes no arguments
(def my-add #(+ 1 1))
(my-add) ;; 2

;; this example - takes one argument as '%1'. If there was a second argument then this would be '%2' etc.
(def my-double #(* 2 %1))
(my-double 12)

;; note - if there is only one argument to the function then 'my-double' could be re-written. The
;; sing;e '%' represents the only argument
(def my-new-double #(* 2 %))
(my-new-double 32)