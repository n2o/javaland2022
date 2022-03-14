#!/usr/bin/env bb

(require '[babashka.fs :as fs]
         '[babashka.process :refer [process]])

(defn load-files [dir glob]
  (str/split-lines
   (:out
    (shell/sh "find" dir
              "-name" glob
              "-type" "f"))))

(defn md5 [size file]
  (-> (process ["head" "-n" size file])
      (process ["md5"]) :out slurp))

(defn sha [bits file]
  (first (str/split
          (:out (shell/sh "shasum" "-b" (str "-a" bits) file))
          #" ")))

(defn candidates-by-fn [f]
  (fn [files]
    (map second (remove (fn [[size vs]] (= 1 (count vs)))
                        (group-by f files)))))

(def candidates-by-size (candidates-by-fn fs/size))

(def candidates-by-partial-md5 (candidates-by-fn (partial md5 1024)))

(def candidates-by-equality (candidates-by-fn (partial sha 256)))

(defn duplicates [dir glob]
  (->>
   (load-files dir glob)
   (candidates-by-size)
   (reduce concat)
   (candidates-by-partial-md5)
   (mapcat candidates-by-equality)))

(defn scan-for-duplicates! [dir glob]
  (doseq [[ff & fs] (duplicates dir glob)]
    (println "============================================")
    (println (format "Duplicate(s) of %s:%n" ff))
    (doseq [f fs]
      (println f))
    (println)))

(if (= 2 (count *command-line-args*))
  (apply scan-for-duplicates! *command-line-args*)
    (do 
      (.println *err* "Expected Arguments: Folder Filematcher (e.g. *.pdf)")
      (java.lang.System/exit -1)))
