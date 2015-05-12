(ns antonine.calculator
  (:require [clojure.string :only [split trim triml]]))

(defn incordec
  "In reading roman numerals, a lower value character preceding a higher value may imply a decrement
   to the higher value, for some characters in some positions. Otherwise it implies an increment."
  [rhs breakpoint increment]
  (if (>= rhs breakpoint)
    (- rhs increment)
    (+ rhs increment)))

(defn read-roman
  "Read a roman numeral and return its value."
  [[character & remainder]]
  (if (nil? character) 0
    (let [rhs (read-roman remainder)]
      (cond
       (= character \I) (incordec rhs 5 1)
       (= character \V) (+ 5 rhs)
       (= character \X) (incordec rhs 50 10)
       (= character \L) (+ 50 rhs)
       (= character \C) (incordec rhs 500 100)
       (= character \D) (+ 500 rhs)
       (= character \M) (+ 1000 rhs)
       :true (throw
              (NumberFormatException.
               (format "Did not recognise the character '%c'", character)))))))

(defn write-roman
  "Write a roman numeral. This is (obviously) not an optimal solution,
   but will do for now."
  [n]
  (cond
   (> 1 n) ""
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




