(ns vorbereitung
  (:require [cheshire.core :as json]
            [clojure.java.shell :refer [sh]]))

(def meetup-jugs
  ["rheinjug"
   "jug-hamburg"
   "Java-User-Group-Augsburg"
   "JUG-Bonn"
   "JUG-Mainz"
   "JUG-Dortmund"
   "JUG-Oberpfalz"
   "Lightweight-Java-User-Group-Munchen"])

(defn build-api-uri [jug]
  (format "https://api.meetup.com/%s/events?status=upcoming" jug))

(defn parse-meetups [jug]
  (json/parse-string
   (:out (sh "curl" (build-api-uri jug)))
   true))

(def all-meetups
  (mapcat parse-meetups meetup-jugs))

(->> all-meetups
     (sort-by :local_date)
     #_reverse
     (map :name))

(comment

  (count all-meetups)

  (nth all-meetups 6)

  (map :local_date all-meetups)

  nil)


;; -----------------------------------------------------------------------------

(comment
  (require '[babashka.process :refer [process]])

  (-> (process ["find" "./" "-name" ".DS_Store" "-print0"])
      (process ["xargs" "-0" "rm"])
      :out
      slurp)

  nil)