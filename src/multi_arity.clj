(ns multi-arity)

;; you can define a function that can take a variable number of args. The
;; args are placed in a collection.

(defn many-args [& args]
  (println args))

(many-args 1 2 3 4) ;; (1 2 3 4)
(many-args 1 2 3 4 5 6 7) ;; (1 2 3 4 5 6 7)
(many-args) ;; nil

;; note that you can place arguments before the '&'

(defn many-args-plus [x y & args]
  (println args) 
  (+ x y))
(many-args-plus 1 2 3 4) ;; (3 4) / 3

;; the examples above are not really multi-arity but are useful examples as to
;; how it is possible to create functions with variable numbers of arguments

(defn multi-arity 
  ([a] a)
  ([a b] (+ a b))
  ([a b c] (+ a b c)))

;; so we can execute this function with 1 / 2 / 3 arguments but
;; not none or 4. 
(multi-arity 1 2 3) ;; 6

;; note that clojure will 'stop' you creating multi-arity functions where the 
;; number of arguments might 'overlap' 