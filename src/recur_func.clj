(ns recur-func)

;; recursive functions 

(def books
  [
   {:title "Jaws" :copies-sold 123654}
   {:title "Emma" :copies-sold 998231}
   {:title "Fahrenheit 123" :copies-sold 98733}
  ])

;; this example 'works' but its disadvantage is that on each function call
;; an entry is placed on the 'stack'

(defn sum-copies-1
  ([books] (sum-copies-1 books 0))
  ([books total] 
   (if (empty? books)
     total
     (sum-copies-1 (rest books)
                 (+ total (:copies-sold (first books)))))))

(sum-copies-1 books)

;; To solve the issue of having a recursive function that 'eats' up
;; stack space we can make use of some 'special' Clojure magic. Using
;; 'recur' allows the function to make use of the fact that it is the 
;; last expression in the function to avoid 'accumulating' stack frames.

(defn sum-copies-2
  ([books] (sum-copies-2 books 0))
  ([books total]
   (if (empty? books)
     total
     (recur
      (rest books)
      (+ total (:copies-sold (first books)))))))

(sum-copies-2 books)

(defn sum-copies-3 [books]
  (loop [books books total 0] ;; here we bind 'books' to the arg 'books' and 'total' to '0'
    (if (empty? books)
      total
      (recur
       (rest books)
       (+ total (:copies-sold (first books)))))))

;; A final example
;; Here we can use 'loop' to dispense with the disparity in function arity in 
;; previous examples. 'loop' works with 'recur' - when it hits a 'recur' inside
;; the body of a loop, Clojure will reset the values bound to the symbolks ('books' and
;; 'total') to values passed to 'recur' and then recursively reevaluate the 'loop'
;; body.

(sum-copies-3 books)

;; Having siad all the above a better way of solving this problem is too :-

(defn sum-copies-4 [books] (apply + (map :copies-sold books)))

(sum-copies-4 books)