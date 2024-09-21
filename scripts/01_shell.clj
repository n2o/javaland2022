#!/usr/bin/env bb
;; In dieser Übung wollen wir schauen, wie wir mit dem Hostsystem direkt
;; interagieren können. Auf der Konsole geht das häufig mit der Funktion `sh`.
;; Um die Funktion zu laden und in unserem Skript verfügbar zu machen, müssen
;; wir es mit dem folgenden Befehl laden:
(require '[clojure.java.shell :refer [sh]])



;; Mit der Funktion `sh` können wir Shell-Befehle ausführen.
;;
;; Experimentiere mit der Funktion und schau dir die Ausgabe an.
(sh "ls")






;; Gib das Verzeichnis als Liste aus: `ls -ahl`




;; Einen für Menschen lesbare Form der Ausgabe erhalten wir, indem wir auf 
;; `:out` zugreifen und das Ergebnis mit `println` ausgeben.
(println (:out (sh "ls")))






;; Mit `sh` können wir auch andere Befehle auf der Konsole ausführen, bspw. 
;; `curl`. 
;;
;; Rufe mit curl eine API auf, bspw. https://api.app.schnaq.com/ping.







;; Als Antwort von der schnaq-API erhalten wir eine JSON-Antwort. JSON müssten
;; wir irgendwie erst verarbeiten, damit wir damit arbeiten können. Die API
;; kann aber auch direkt EDN zurückliefern.
;;
;; Gib den Accept Header per curl mit und setze ihn auf
;; `Accept: application/edn`. 









;; Nun wollen wir die API Antwort direkt verarbeiten können. Dazu holen wir aus
;; dem curl-Aufruf aus :out den String und geben in in die Funktion 
;; `read-string`.





;; Für curl gibt es in Babashka auch einen Wrapper, der den Umgang etwas 
;; erleichtert und uns erlaubt idiomatischeren Clojure Code zu schreiben.

;; Der Wrapper liegt im Namespace babashka.curl und kann mit
;; require geladen werden. 

(require '[babashka.curl :as curl])

;; Der Namespace hat fertige Clojure Funktionen für die Requests, die wir 
;; mit curl erzeugen können, wie z.B. get, post, etc.

(curl/get "http://www.rheinjug.de")

;; Das Resultat des Aufrufs ist eine Clojure-Map, in der wir neben dem 
;; Body auch zusätzliche Informationen über die Response 
;; (z.B. den Statuscode) finden.

(get-in (curl/get "http://www.rheinjug.de") [:headers "date"])

;; Außerdem können wir dem Request auch zusätzliche Header über eine 
;; Datenstruktur mitgeben

(curl/get "http://www.rheinjug.de" {:headers {"DNT" "1"}})

;; Fügen wir nun die Bestandteile zusammen:

(defn ping []
  ;; Schreibe hier den Code, der den Wert des Attributs "text"
  ;; eines Pings auf Schnaq zurückliefert
  )

(println (ping)) ;; soll 🧙‍♂️ ausgeben


;; Führe das Skript nun auf der Konsole aus! Navigiere in deiner Shell in diesen
;; Ordner (`scripts`) und starte es mit: `bb 01_shell.clj`
