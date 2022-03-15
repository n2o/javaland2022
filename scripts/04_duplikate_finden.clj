#!/usr/bin/env bb
;; Sucht in einem Verzeichnis nach Duplikaten gemäß eines regulären Ausdrucks.
;;
;; Beispielaufruf: ./04_duplikate_finden.clj duplicates '*'

(require '[babashka.fs :as fs]
         '[babashka.process :refer [process]]
         '[clojure.string :as str]
         '[clojure.java.shell :refer [sh]])

(defn load-files [dir glob]
  (str/split-lines
   (:out
    (sh "find" dir
        "-name" glob
        "-type" "f"))))

(defn md5 [size file]
  (-> (process ["head" "-n" size file])
      (process ["md5"]) 
      :out 
      slurp))

(defn sha [bits file]
  (-> (sh "shasum" "-b" (str "-a" bits) file)
      :out
      (str/split #" ")
      first))

(defn candidates-by-fn [f]
  (fn [files]
    (->> files
         (group-by f)
         (remove (fn [[_size files]] (= 1 (count files))))
         (map second))))

(def candidates-by-size (candidates-by-fn fs/size))

(def candidates-by-md5 (candidates-by-fn (partial md5 1024)))

(def candidates-by-sha (candidates-by-fn (partial sha 256)))

(defn iprintln [& text])

(defn scan-for-duplicates! [dir glob]
  (let [files-by-size (candidates-by-size (load-files dir glob))
        no-of-partitions (count files-by-size)]
    (iprintln "Eliminated files with unique size. Starting detail scan on" no-of-partitions "partitions.")
    (doseq [[idx files] (map vector (map inc (range)) files-by-size)]
      (iprintln "Analyzing" idx "/" no-of-partitions)
      (let [by-md5 (candidates-by-md5 files)
            partitions (count by-md5)]
        (iprintln " ** Eliminated files with unique partial md5 hashcode. Starting detail scan on" partitions "partitions.")
        (doseq [[idx files] (map vector (map inc (range)) by-md5)]
          (iprintln "Analyzing sup partition" idx "/" partitions)
          (let [by-sha (candidates-by-sha files)]
            (doseq [[ff & fs] by-sha]
              (println "============================================")
              (println (format "Duplicate(s) of %s:%n" ff))
              (doseq [f fs] (println f))
              (println))))))))

(let [[directory pattern] *command-line-args*]
  (if (= 2 (count *command-line-args*))
    (scan-for-duplicates! directory pattern)
    (do
      (.println *err* "Expected Arguments: folder filematcher (e.g. ~ '*.pdf')")
      (java.lang.System/exit -1))))
