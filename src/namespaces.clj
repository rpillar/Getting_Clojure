(ns namespaces
  (:require [def-symbols-vars :as dsv]
            [functional-things :only [dracula cheap?] :as ft]))

;; in Clojure 'vars' (see def_symbols_vars.clj) live in 'namespaces.
;; So -> 

(def amount 12.23) ;; this 'var' lives in the 'namespaces' namespace :)
(println amount) ;; When this is evaluated Clojure will 'look in' the current
;; namespace for the 'amount' var.

;; when using the 'clj' repl the default namespace is 'user' - hence the 'user=>' prompt.

;; to declare a namespace -> see ^^ (ns namespace)
;; namespaces allow us to 'collect' functions / values that have some related meaning.
;; to access a function / value from a different namespace ->
;; use ':require' to 'load' the required namespace
;; use ':as' to give the namespace an alias - useful if the namespace 'name' is long

(println dsv/author) ;; here we have a 'full qualified symbol' - namespace/symbol
;; note the use ^^ of ':require' to enable access to the def-symbols-vars
;; namespace. 

;; also note that the 'namespace' name and 'file names' must match (any '.'s are converted to '/'
;; such that Clojure is able to navigate the filesystem to find the code).

;; Namespace META data
;; -------------------

;; in Clojure a namespace is just another 'var' - so you can get at the current namespace by :-
(println "current namespace : " *ns*) ;; #namespace[namespaces]

;; to look up any existing namespace :-
(find-ns 'clojure.data) ;; note the use of the 'symbol

;; and then to discover all the 'things' in that namespace :-
(def my-ns (find-ns 'clojure.string))
(ns-map my-ns)

(find-ns 'def-symbols-vars) ;; #namespace[def-symbols-vars]
(ns-map (find-ns 'def-symbols-vars))

;; To 'import' specific functions / values from a namespace use ':only' in the ':require' form
(ft/cheap? ft/dracula) ;; true

;; there is more that could be added here about namespaces - but this is sufficient for now.