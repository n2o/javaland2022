#!/usr/bin/env bb
;; Sucht in einem Verzeichnis nach Duplikaten gemäß eines regulären Ausdrucks.
;;
;; Beispielaufruf: ./04_duplikate_finden.clj duplicates '*'
;; Ergebnis: 
;; ============================================
;; Duplicate(s) of duplicates/rheinjug:
;;
;; duplicates/ClojureUG
;;
;; -----------------------------------------------------------------------------
;; Das Finale
;;
;; Nun wird es etwas spannender. Wir wollen hier ein Skript entwickeln,
;; welches innerhalb eines Verzeichnisses nach Duplikaten sucht. Diese Duplikate
;; können anhand verschiedener Funktionen gefunden werden. Wir wollen hierfür
;; die Hashes der Dateien vergleichen. 

;; Ziel ist es hier in dem Skript die angegebenen Funktionen zu füllen. Dabei 
;; geht es nur um Shellbefehle und Interaktion verschiedener Prozesse. Den
;; Babashka- (bzw. Clojure-)Code lassen wir hier im Repo einfach liegen.
;; Los geht's!

(require '[babashka.fs :as fs]
         '[babashka.process :refer [process]]
         '[clojure.string :as str]
         '[clojure.java.shell :refer [sh]])

;; Diese Funktion bekommt ein directory übergeben, in der die Plagiate gefunden
;; werden sollen. Das zweite ist ein glob, welches anhand eines Patterns die zu 
;; untersuchenden Dateien definiert. Rufe hier die Shell-Funktion `find` auf, 
;; welche die Parameter `dir` und `glob` verwendet. Wenn deine Funktion grob das
;; tut was wir hier haben wollen, müsste der Vergleich `true` ergeben.
;;
;; Gib _nur_ Dateien aus, keine Verzeichnisse.
(defn load-files [dir glob]
  (str/split-lines
   "" ;; hier mit `sh` den `find`-Befehl aufrufen zum Auflisten aller Dateien Verzeichnis
   ))


(comment
  (= (load-files "./scripts/duplicates" "*")
     ["./scripts/duplicates/rheinjug" "./scripts/duplicates/ClojureUG" "./scripts/duplicates/ClojureRocks"])
  nil)










;; Berechnen wir nun den sha-Hash einer übergebenen Datei. Rufe dafür `shasum` 
;; mit der angegeben Anzahl von Bits auf und berechne den SHA-Wert der Datei.
;; Hierbei soll nur der Hash zurückgegeben werden. Es gibt dafür wieder einen
;; Test.
(defn sha [bits file]
  ;; ...
  )

(comment
  (= (sha 256 "./scripts/duplicates/ClojureUG")
     "ea06d7b51ad4f70db151d0af4a1a3acbef73a1a44fb06de08b55bea5f702d756")
  nil)










;; In Babashka gibt es nicht nur `sh`, sondern auch `process`, was einen Wrapper
;; um java.lang.ProcessBuilder darstellt. Einzelne Prozesse können so ineinander
;; gepiped werden. Hier ein Beispiel:

(comment
  (-> (process ["ls"])
      (process ["grep" "README"]) :out slurp)
  nil)

;; liefert "README.md\n". Das Shell-Äquivalent wäre: `ls | grep README`
;;
;; Nun soll der erste Prozess mit `head` die ersten `no-of-lines` Zeilen aus `file` 
;; holen und diese in einen zweiten Process `md5` pipen. 
(defn md5 [no-of-lines file]
  ;; ...
  )

(comment
  (= (md5 1024 "./scripts/duplicates/rheinjug")
     "1f5a6c105bb963e939aa3160866557f4\n")
  nil)






;; Nun sollte das Programm funktionieren! Versuche den Aufruf aus dem oberen
;; Teil der Datei und schau, ob du Duplikate erkennen kannst.
;;
;; Weiter unten folgt nun eine ziemlich imperative Lösung das Problem anzugehen.
;; Wir arbeiten noch an weiteren Möglichkeiten :-) An dieser Stelle muss aber 
;; nicht weiter verstanden werden, was hier passiert, keine Sorge. Dafür habt
;; ihr nun genügend Zeit Clojure und Babashka zu lernen 🎉

(def print-status true)

(defn candidates-by [f files]
  (->> files
       (group-by f)
       (remove (fn [[_size files]] (= 1 (count files))))
       (map second)))

(def candidates-by-size (partial candidates-by fs/size))
(def candidates-by-md5 (partial candidates-by (partial md5 1024)))
(def candidates-by-sha (partial candidates-by (partial sha 256)))

(defn iprintln [& text] (when print-status (apply println text))) 

(defn scan-for-duplicates! [dir glob]
  (iprintln "Starting scan of" dir)
  (let [files-by-size (candidates-by-size (load-files dir glob))
        no-of-partitions (count files-by-size)]
    (iprintln "Eliminated files with unique size. Starting detail scan on" no-of-partitions "partitions.")
    (doseq [[idx files] (map vector (map inc (range)) files-by-size)]
      (iprintln "Analyzing" idx "/" no-of-partitions)
      (let [by-md5 (candidates-by-md5 files)
            partitions (count by-md5)]
        (iprintln " ** Eliminated files with unique partial md5 hashcode. Starting detail scan on" partitions "partitions.")
        (doseq [[idx files] (map vector (map inc (range)) by-md5)]
          (iprintln "Analyzing sub-partition" idx "/" partitions)
          (let [by-sha (candidates-by-sha files)]
            (doseq [[ff & fs] by-sha]
              (println "============================================")
              (println (format "Duplicate(s) of %s:%n" ff))
              (doseq [f fs] (println f))
              (println))))))))

(defn -main [args]
  (let [[directory glob] args]
    (if (= 2 (count args))
      (scan-for-duplicates! directory glob)
      (do
        (.println *err* "Expected Arguments: folder filematcher (e.g. ~ '*.pdf')")
        (java.lang.System/exit -1)))))

(-main *command-line-args*)
