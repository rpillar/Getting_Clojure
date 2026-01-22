(ns def-symbols-vars)

;; 'def' binds a symbol to a value - for example
(def title "Emma")

;; in this example the 'symbol' 'title' can be access anywhere in this namespace (or in
;; any other 'ns' that 'requires' this 'ns')

;; 'def' can be useful for creating 'constants' (as above)

;; to get the 'symbol' then prefix the 'symbol name' with a quote.
(str 'title)
(= 'title 'title)

;; also the 'bindings' between a symbol and value (the 'things' created by 'def') are
;; also ordinary values. When you evaluate a 'def' Clojure create a 'var' - something
;; that represents the binding between a symbol and value

(def author "Austen") ;; evaluates to #'def-symbols-vars/author
(def my-var #'author) ;; also evaluates to #'def-symbols-vars/my-var

(.get my-var) ;; "Austen" - 'get' the 'value'
(.-sym my-var) ;; author - get the 'symbol'

;; NOTE - 'vars' are mutable - hence in production code is would be 'best' practice
;; not to 'change' the value bound to a var
;; ALSO - do not use 'def' within a function - you can (and it will work) but 'best'
;; practice is not NOT do it.

;; there are 'dynamic' bindings - see C19 for moew info

;; there are some useful 'dynamic' bindings that Clojure supplies
;; *print-length* - note the '*' at each end of the 'binding' name - this
;; denotes that this is a 'dynamic'' binding. So - for example -

(def books ["Emma" "The Bible" "Oliver Twist" "Huckleberry Finn"])
(set! *print-length* 2)
books ;; not sure why this doesn't work !!!! - but it does work in the 'clj repl'