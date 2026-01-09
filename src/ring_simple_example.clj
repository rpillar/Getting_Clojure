(ns ring-simple-example
  (:require [ring.adapter.jetty :as jetty]))

;; this is an example to add to the 'functional_things' code.
;; This illustrates that when using Clojure it is possible to pass
;; 'functions' around etc.

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello from your minimal web application"})

(defn log-request [msg value]
  (println msg value)
  value)

(defn middlewarre-logging 
  [msg handler]
  (fn [request]
    (log-request msg (handler request))))

(defn app [handler]
  (middlewarre-logging "Response : " handler))

(defn -main []
  (jetty/run-jetty (app handler) {:port 3031}))

;; add start / stop functions ...

(comment
  (-main))
