#!/usr/bin/env bb
;; Weiter geht's mit API Requests!
;;
;; Hier fragen wir die Meetup-API an, um zunächst von einer JUG die Events zu 
;; laden und zu verarbeiten. Danach wollen wir mehrere JUGs bei Meetup abfragen,
;; um eine Liste von 
(require '[cheshire.core :as json] ; Um eine JSON-Antwort zu parsen
         '[clojure.java.shell :refer [sh]])  


;; Formatstrings funktionieren genauso wie wir es auch aus anderen Programmier-
;; sprachen her kennen. Dafür gibt es die Funktion `format`.

(format "https://api.meetup.com/%s/events?status=upcoming" "jug-hamburg")
;; => "https://api.meetup.com/jug-hamburg/events?status=upcoming"



;; Damit haben wir nun also die URL, um zu schauen welche Meetups die JUG
;; Hamburg noch veranstalten wird. Rufen wir diese doch mal mit `curl` ab.









;; Parsen wir die Antwort nun mit cheshire, sodass wir damit weiterarbeiten
;; können. Verwende dafür die Funktion `json/parse-string`, gib als erstes
;; Argument die Ausgabe deines curl-Aufrufs und als zweites Argument `true`, 
;; damit wir wieder Keywords statt Strings erhalten in der Antwort.







;; Erstelle aus dem Aufruf eine Funktion, bei der du den Namen der JUG als 
;; Argument übergibst. Der Aufruf soll wie folgt aussehen:
;;
;; `(load-events "jug-hamburg")`
;; => ({:name "GraphQL Anwendungen mit Spring Boot", :local_time "19:00", :local_date "2022-03-22", ...})










;; Hier ist eine Liste von JUGs, die uns eingefallen sind. Passe die Liste nach
;; deinen Vorstellungen an wenn du magst.
;;
;; Wir wollen nun jede dieser JUGs bei Meetup anfragen. Wir beschränken das hier
;; erst mal auf eine Hand voll JUGs. Du kannst gleich die Kommentare bei den 
;; weiteren JUGs entfernen wenn du möchtest. Lass sie aber fürs Erste aus-
;; kommentiert.

(def jugs
  "Entferne hier die Kommentare, wenn dein Code funktioniert"
  ["jug-hamburg"
   "JUG-Bonn"
   ;; "rheinjug"
   ;; "Java-User-Group-Augsburg"
   ;; "JUG-Mainz"
   ;; "JUG-Dortmund"
   ;; "JUG-Oberpfalz"
   ;; "Lightweight-Java-User-Group-Munchen"
   ])

;; Nun wenden wir die Funktion `load-events` auf jede einzelne JUG an und
;; erstellen eine Liste mit den Antworten. Um eine Funktion auf jedes einzelne
;; Element einer Collection anwenden wollen, können wir `map` verwenden.
;; Jeder einzelne API-Call gibt aber eine Liste von Events zurück. Um diese
;; Listen alle zusammen in eine Liste zu packen, verwenden wir statt `map`
;; `mapcat`, welches `map` anwendet und dann die Listen zusammenbaut.
;;
;; Verwende `mapcat` mit der Funktion `load-events` und den `jugs` an und
;; speichere das Ergebnis in einem Symbol `meetups`.

(map inc [1 2 3])  ;; Beispiel









;; Auf Collections können wir viele Funktionen anwenden, die unsere Daten
;; transformieren. So gibt es bspw. die Funktion `sort-by`, die als ersten 
;; Parameter eine Funktion oder ein Keyword enthält, nach dem sortiert werden
;; soll.
;;
;; Verwende die Funktion `sort-by` mit den `jugs` und sortiere die Meetups nach
;; dem Keyword `:local_date`.









;; Nun haben wir eine sortierte Liste aller Meetups, die bald veranstaltet
;; werden. Leider in der falschen Reihenfolge. Doch dafür gibt es die Funktion
;; `reverse`.
;;
;; Drehe die Reihenfolge deiner sortierten Meetups um.









;; Mit dem Threading-Macro `->>` können wir die Aufrufe ein wenig lesbarer
;; gestalten.
;;
;; Optimiere die Aufrufe von oben mit dem Macro `->>`

(->> [{:name "christian"}
      {:name "jens"}
      {:name "björn"}]
     (sort-by :name) 
     reverse)






;; Nun interessieren uns die Titel der Veranstaltungen
;;
;; Füge noch `(map :name)` zu deinem Threading-Macro hinzu, damit die Namen
;; als Liste zurückgegeben werden.








;; Fertig, wir haben ein kleines Script, um alle upcoming Meetups einer
;; beliebigen Liste von JUGs abzufragen und anzuzeigen. Nun könnten wir ja
;; quasi Kalendereinträge oder einen RSS Feed zusammenbauen 🤔
