(ns demo.fundamentals-3)

;; collections

;; vector
[1 2 3]

; to index into a vector - its _like_ the vector is a function
([1 2 3] 0) ;; returns 1
; or - with a map
({:a 1 :b 2} :b) ;; returns 2
; in a 'let' binding
(let [my-map {:a 1 :b 2}]
  (my-map :a)) ;; returns 1

;; note that for vectors - if you specify a value that is outside of the
;; bounds of the vextor then you get an 'OutOfBounds' exception

;; for maps a more common way to access key values is :-
(let [my-new-map {:name "Richard" :age 21}]
  (:name my-new-map)) ;; "Richard"

;; you can also use 'get'
(get [1 2 3] 1) ;; 2
(get {:a 1 :b 2} :b) ;; 2
(get [1 2 3] 9) ;; returns 'nil' when the index if out-of-bounds
(get {:a 1 :b 2} :c) ;; also returns 'nil'

;; to 'amend' vectors / maps (well not really - remember immutability)
;; NOTE - when applied to a vector returns a new vector with value at index - in
;; this example - value '3' at index '0'.
(assoc [1 2] 0 3) ;; gives us a new vector - [3 2]
(assoc [1 2 3] 1 4) ;; gives us [1 4 3]

;; NOTE - when applied to a map a key / value is supplied. In this first
;; example the key ':a's value is amended to '4'
(assoc {:a 1 :b 2} :a 4) ;; {:a 4 :b 2}
;; if the key / value does not exist it will be added.
(assoc {:a 1} :b 5)

(let [my-map {:a 1 :b 2}]
  (assoc my-map :c 3)) ;; {:a 1 :b 2 : c 3}

;; the 'above' all return a new vector / map etc.

;; to 'remove' stuff from a 'map' - use 'dissoc'
(dissoc {:a 1 :b 2} :a) ;; returns {:b 2}

;; for deeply nested maps you can use 'get-in' :-
(let [my-map {:a 1 :b 3 :c {:c1 4 :c2 5}}]
  (get-in my-map [:c :c2])) ;; returns 5

;; you can also use 'assoc-in' with 'multi-level' maps. In this example we supply
;; 'map key' / 'map key' / index
(let [my-map {:a 1 :b {:b1 1 :b2 4 :b3 [1 2 3 4]}}]
  (assoc-in my-map [:b :b3 2] 33)) ;; returns {:a 1, :b {:b1 1, :b2 4, :b3 [1 2 33 4]}}

;; note use of indexing into the vector .....

