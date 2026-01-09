(ns pre-and-post)

;; add a 'pre' condition to the function

;; in this case if the 'book' map does not contain. a 'title' element
;; then an exception will be raised - 'Assert failed: (:title book)'

(defn publish-book [book]
  {:pre [(:title book)]}
  (println (:title book)))

;;(publish-book {:title "This is a book", :author "Richard"})
;;(try
;;  (publish-book {:author "Richard", :genre "sci fi"})
;;  (catch Exception e (println "An exception has occurred")) )

(defn calculate-fee [invoice]
  {:pre [(> (:amount invoice) 10)]}
  (println "Add a fee to this invoice."))

;; (calculate-fee {:id 1 :tax 10 :amount 8})
;; (calculate-fee {:id 2 :tax 15 :amount 12})

;; it is possible also to conditionally check the value returned from
;; the function using a 'post' check

;;(defn post-check [amount]
;;  {:post [(> amount 10)]}
;;  amount)

;;(try 
;;  (post-check 9)
;;  (catch Exception e "Post check has failed."))

(try 
  (/ 1 0)
  (catch Exception e))