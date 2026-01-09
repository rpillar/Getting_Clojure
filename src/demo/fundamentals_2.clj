(ns demo.fundamentals-2)

;; Java interop
(String. "Richard") ;; new String("Richard")

;; calling a method on a String instance
(let [string (String. "Richard")]
  (.charAt string 1)) ;; string.charAt(1)

;; calling a 'static' method
(System/getProperty "java.version")

;; running a number of methods on a StringBuilder class instance one after the other
(.. (StringBuilder.)
    (append "Running Java ")
    (append (System/getProperty "java.version"))
    (toString))

;; Java 'Runable's
;; check these out .....

;; exceptions - try / catch / finally
(let [result (try
               (/ 1 0)
               (catch Exception e 
                 :unknown_error)
               (catch java.io.IOException e 
                 :io-error)
               (finally
                 (println "I have finished"))
               )]
  result) ;; return the 'result'

;; implement methods on a Java interface using reify
;; why ?
;; further inv needed