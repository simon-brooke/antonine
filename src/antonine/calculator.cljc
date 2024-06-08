(ns antonine.calculator
  (:require [clojure.math :refer [floor]]
            [instaparse.core :refer [get-failure]])
  (:import [java.lang NumberFormatException]))

(defn incordec
  "In reading roman numerals, a lower value character preceding a higher value may imply a decrement
   to the higher value, for some characters in some positions. Otherwise it implies an increment."
  [rhs breakpoint increment]
  (if (>= rhs breakpoint)
    (- rhs increment)
    (+ rhs increment)))

(defn- read-roman-char [c accumulator]
  (cond
    (= c \I) (incordec accumulator 5 1)
    (= c \V) (+ 5 accumulator)
    (= c \X) (incordec accumulator 50 10)
    (= c \L) (+ 50 accumulator)
    (= c \C) (incordec accumulator 500 100)
    (= c \D) (+ 500 accumulator)
    (= c \M) (+ 1000 accumulator)
    :else (throw
           (NumberFormatException.
            (format "Did not recognise the character '%c'", c)))))

(defn read-roman
  "Read this `input`, interpreting it as a Roman numeral, and return the
   integer value."
  [input]
  (loop [c (last input) r (butlast input) accumulator 0]
    (let [v (read-roman-char c accumulator)]
      (if (empty? r) v
          (recur (last r) (butlast r) v)))))

(defn write-roman
  "Write, as a roman numeral, the number `n`."
  [n]
  (cond
    (> 1 n) "" ;; Romans don't bother with pesky fractions. Pi is 3!
    (>= n 1000) (str "M" (write-roman (- n 1000)))
    (>= n 900) (str "CM" (write-roman (- n 900)))
    (>= n 500) (str "D" (write-roman (- n 500)))
    (>= n 400) (str "CD" (write-roman (- n 400)))
    (>= n 100) (str "C" (write-roman (- n 100)))
    (>= n 90) (str "XC" (write-roman (- n 90)))
    (>= n 50) (str "L" (write-roman (- n 50)))
    (>= n 40) (str  "XL" (write-roman (- n 40)))
    (>= n 10) (str "X" (write-roman (- n 10)))
    (= n 9) "IX"
    (>= n 6) (str "VI" (write-roman (- n 6))) ;; workaround for odd behaviour printing VIII
    (>= n 5) (str "V" (write-roman (- n 5)))
    (= n 4) "IV"
    (>= n 1) (str "I" (write-roman (- n 1)))))

(defn r-op
  "Apply this operator to these arguments, expected to be roman numerals and
   return the result as a roman numeral"
  [operator & args]
  (write-roman
   (int
    (apply operator
           (map read-roman args)))))

(defn r-op2
  "Apply this operator to these two arguments, expected to be roman numerals"
  [operator arg1 arg2]
  (r-op operator arg1 arg2))

(defn r-plus
  "Add arbitrarily many roman numerals and return a roman numeral"
  [& args]
  (apply r-op (cons + args)))

(defn r-minus
  "Subtract a roman numeral from a roman numeral and return a roman numeral"
  [arg1 arg2]
  (apply r-op (list - arg1 arg2)))

(defn r-multiply
  "Multiply arbitrarily many roman numerals and return a roman numeral"
  [& args]
  (apply r-op (cons * args)))

(defn r-divide
  "Divide a roman numeral by a roman numeral and return a roman numeral"
  [arg1 arg2]
  (apply r-op (list / arg1 arg2)))

(defn calculate
  [parse-tree]
  (let [failure-text (:text (get-failure parse-tree))]
   (if (= :EXPRESSION (first parse-tree))
    (case (count parse-tree)
      2 (read-roman (second parse-tree))
      4 (let [lhs (read-roman (second parse-tree))
              op (case (first (nth parse-tree 2))
                   :ADD +
                   :MULTIPLY *
                   :SUBTRACT -
                   :DIVIDE /)
              rhs (calculate (nth parse-tree 3))]
          (int (floor (apply op (list lhs rhs)))))
      ;;else
      (throw 
       (ex-info 
        (format "Unexpected parse tree '%s'" failure-text) 
        {:problem parse-tree})))
    (throw 
     (ex-info 
      (format "Unexpected expression: '%s'" failure-text) 
      {:problem parse-tree})))))

