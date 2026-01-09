(ns multimethods)

;; from 'Getting Clojure' - Page 52/53

;; here is a function that is able to handle a number of different 
;; formats (in this case 'book' information structures) - either
;; a vector or a map (with different structures again)

;;(defn normalise-book [book]
;;  (if (vector? book)
;;    {:title (first book) :author (second book)}
;;    (if (contains? book :title)
;;      book
;;      {:title (:book book) :author (:by book)})))

;;(normalise-book {:title "War and Peace" :author "Richard"})

;; this works but can be implemented in a more elegent way using
;; 'multimethods

;; first - create a 'dispatch' function that is able to distinguish between 
;; the different types of 'imput

(defn dispatch-book-format [book]
  (cond
    (vector? book) :vector-book
    (contains? book :title) :standard-map
    (contains? book :book) :alternative-map))

;; decalre our 'multimethod'

(defmulti normalise-book dispatch-book-format)

;; define the different implementations using 'defmethod'

(defmethod normalise-book :vector-book [book]
  {:title (first book) :author (second book)})

(defmethod normalise-book :standard-map [book]
  book)

(defmethod normalise-book :alternative-map [book]
  {:title (:book book) :author (:by book)})

;; examples

(normalise-book {:title "War and Peace" :author "Richard"})

(normalise-book {:book "Emma" :by "Jane Austen"})

(normalise-book ["1984" "George Orwell"])
