(ns temporary-variables)

;; use 'let'

;; NOTE - using 'let' - is like using 'let' as a function and passing a 'name' and
;; a 'value' to it. 

;; in this example we 'assign' the result of a discount calculation to the
;; temporary variable 'discounted-amount' - rather than attemping to perform the
;; calculation 'in place' -  something that could result in 'confusing' code.
(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discounted-amount (* amount (- 1.0 discount-percent))]
    (if (> discounted-amount min-charge)
      (format "%.2f" discounted-amount)
      (format "%.2f" min-charge))))

;; NOTE - the use of 'format' to ensure that the amounts returned have two
;; decimal places.

(compute-discount-amount 12.29 0.12 10.99);; 10.99
(compute-discount-amount 16.29 0.12 10.99) ;; 14.34

;; it is possible (as you might expect) to 'bind' (thats the proper way to think about this)
;; more that one 'name' inside a 'let'. For example here is an updated version of the 
;; 'compute-discount-amount' function ->

(defn compute-discount-amount-2 [amount discount-percent min-charge]
  (let [discount (* amount discount-percent)
        discounted-amount (- amount discount)]
    (if (> discounted-amount min-charge)
      (format "%.2f" discounted-amount)
      (format "%.2f" min-charge))))

;; NOTE - in this exmaple you can see that 'discount' is used as soon as its been
;; 'bound'. Also - although we haven't here - it is possible to have multiple 
;; statements inside the body of a 'let'.

;; combining 'let' and 'fn'
(def user-discounts
  {"John" 0.11 "Richard" 0.12 "Dawn" 0.08})

;; compute in a 'let' and use in an 'fn'
;; in this example - 'discount-percent'

(defn compute-discount-amount-3 [user-name user-discounts min-charge]
  (let [discount-percent (user-discounts user-name)]
    (fn [amount]
      (let [discount (* amount discount-percent)
            discounted-amount (- amount discount)]
        (if (> discounted-amount min-charge)
          discounted-amount
          min-charge)))))

(def compute-richards-price (compute-discount-amount-3 "Richard" user-discounts 10.0))
(compute-richards-price 20.0) ;; 17.7
(compute-richards-price 8.99) ;; 10.0

;; a variation on the 'let' examples ^^ is the 'if ... let'. For example :-
(def book-with-no-author
  {:title "Getting Clojure"})
(def book-with-author
  {:title "getting Clojure" :author "Russ Olsen"})

;; 'if-let' takes a single binding and uses th bound value  (the author's name) as the condition of an 'if'. In
;; this initial example if there is no author in the 'book' map then 'nill' is returned.

(defn uppercase-author [book]
  (if-let [author (:author book)]
    (.toUpperCase author)))

(uppercase-author book-with-author) ;; RUSS OLSEN
(uppercase-author book-with-no-author) ;; nil

;; we can also supply a second expression - that acts as the 'else'

(defn uppercase-author-2 [book]
  (if-let [author (:author book)]
    (.toUpperCase author)
    "ANON"))

(uppercase-author-2 book-with-no-author) ;; "ANON"

(true? (> 2 3))