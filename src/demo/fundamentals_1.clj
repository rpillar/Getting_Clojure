(ns demo.fundamentals-1
  (:require [clojure.string :as string]))

;; VARIOUS RANDOM THINGS THAT ILLUSTRATE THE BASICS OF CLOJURE

(println "Hello"
         (+ 2 2))

;; read a file and print its contents
(println (slurp "/usr/share/dict/connectives"))

;; nil == null
nil

;; booleans 
true
false

;; check the 'type' of a 'thing'
(type "Richard")

;; to create a 'keyword' (like  an atom in 'Erlang')
;; useful for keys in maps etc.
:name

;; a vector
[1 2 3]

;; a 'literal' list - otherwise the '1' will be treated as a 'function'
'(1 2 3)

;; a hash-map (key/value pairs)
{:a 1, :b 2}

;; a 'set'
#{:a :b}

;; using functions from other 'namespaces'
;; note that we need to 'require' it first - see ^^
(println (string/upper-case "Hello"))

;; define a variable or a constant within the current namespace
(def my-name "Richard")
(println my-name) ;; String
(type my-name)
(def my-names (slurp "/usr/share/dict/propernames"))
(println my-names)
(type my-names) ;; String
(def my-names-list (string/split-lines (slurp "/usr/share/dict/propernames")))
(type my-names-list) ;; PersistentVector
(take 10 my-names-list)

;; define a 'symbol' that is 'bound' to a function (the long way)
(def mangle
  (fn [string]
    (string/join "-" (string/reverse string))))
(println (mangle "Richard"))

;; because creating a function is so common a short-cut is :-
;; note that to make a function private use 'defn-'
(defn de-mangle [string]
  (string/reverse (string/replace string "-" "")))
(de-mangle (mangle "Hello"))

;; using 'let' in functions to create 'local' variables
;; note that a clojure convention is to append a '?' to a function name
;; that returns a boolean - true / false
(defn palindrome? [word]
  (let [lower-case (string/lower-case word)
        reversed (string/reverse lower-case)]
    (= reversed lower-case)))
(palindrome? "kajak") ;; true

;; equality - checked using functions such as '=' and 'not='
(= "Richard" (str "Rich" "ard")) ;; true
(not= "Richard" (str "Rich" "ard")) ;; false

;; and also contains? - is the 'key' present in the 'collection'
(contains? {:a 1, :b 2} :b) ;; true

;; branching - if / else etc.
;; id <test> <true> <false>
(if (> 1 2) (println "its true") (println "its false"))

;; or you can use 'cond' - a kind of 'switch' expression
(cond
  (= 1 2) 
  "1"

  (not= 4 4) 
  "2"

  (> 2 1) 
  "3"
  )

;; if you want to do more thatn one 'thing' prior to returning a 'value
(if (> 1 2)
  true
  (do
    (println "no it isn't")
    false))

;; for 'single' conditionals but like the previous example we want to do a few 'things'
(when (> 3 2)
  (println "Its true")
  (println "so what")
  true)

(defn num? [n]
  (if (or (odd? n)
          (and (even? n)
               (< 10 n))) ;; reads 'is 10 less than 'n'
    :good
    :bad))
(num? 8) ;; (is 'n' odd) or (is 'n' even and is 'n' gt 10)

