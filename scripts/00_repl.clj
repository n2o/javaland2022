(ns javaland.repl)

;; REPL-Koch Session inspiriert von der Clojure UG Bonn
(def 🐔 '🐔)
(def 🐷 '🐷)
(def 🐟 '🐟)
(def 🐮 '🐮)
(def 🦄 '🦄)
(def 🌈 '🌈)
(def 🥩 '🥩)
(def 🥚 '🥚)
(def 🌽 '🌽)
(def 🍈 '🍈)
(def 🍉 '🍉)
(def 🥔 '🥔)
(def 🎂 '🎂)
(def 🍰 '🍰)

(def 🏔 '🏔)
(def 🐉 '🐉)

(defn 🔪
  [x]
  (case x
    🐟 '🍣
    🍈 '🍉
    🎂 '🍰
    🐮 '🥩
    🐉 '🏔
    🦄 '🌈
    x))

(defn 🔥
  [x]
  (case x
    🥚 '🍳
    🌽 '🍿
    🥔 '🍟
    🐔 '🍗
    🐷 '🥓
    🥩 '🍔
    x))















(comment
  ;; Zahlen
  42
  1.3
  4/3

  ;; Strings
  "foo"
  (type "foo")

  ;; Characters
  \f
  (type \f)

  (type +)

  ;; Keywords, evaluieren zu sich selbst
  :foo
  (type :foo)

  ;; Symbole, referenzieren i.d.R. etwas anderes
  ;; `def` definiert global, `let` lokal
  (def x 42)
  x

  (+ x 1)
  x

  (let [y 21]
    y)
  y


  ;; Funktionen, letzte Anweisung im Codeblock ist der Rückgabewert
  +
  (+ 1 2)
  (* 2 (inc 2))

  (def square1
    (fn [n]
      (* n n)))
  (square1 4)


  (defn square2
    [n]
    (* n n))
  (square2 4)

  (def square3 #(* % %))
  (square3 4)

  ;; Threading Macros - Wende Funktionen nacheinander an und verwende jeweils
  ;; das Ergebnis der Funktion davor als Eingabe für die nächste Funktion.
  (-> 42 inc)

  🔥


  🔪




  ;; Lazy Sequences
  (set! *print-length* 20)

  (range)


  ;;;; Collections
  ;; Vektoren
  []
  (type [])

  ;; Listen
  '()

  ;; Sets
  #{"bar" "foo"}

  ;; Maps
  {}
  {"key" "value"}

  (def mymap
    {"key" "value"
     :clojure :rocks})

  (:clojure mymap)


  ;; Higher Order Functions
  ;; Funktionen, die Funktionen als Parameter bekommen oder eine Funktion zurückgeben
  map

  (map inc [1 2 3])
  ;; => ((inc 1) (inc 2) (inc 3))

  (reduce + [1 2 3 4])
  ;; => (+ (+ (+ 1 2) 3) 4)


  ;; There is a function for it...

  (filter
   (fn [n] (= 0 (mod n 2)))
   (range 1 20))





  nil)
