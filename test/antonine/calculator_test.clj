(ns antonine.calculator-test
  (:require [clojure.test :refer :all]
            [antonine.calculator :refer :all]))

(deftest reading-test
  (testing "Reading roman numerals"
    (is (= (read-roman "I") 1) "one is one and all alone and ever more shall be so")
    (is (= (read-roman "II") 2))
    (is (= (read-roman "IV") 4))
    (is (= (read-roman "V") 5))
    (is (= (read-roman "VII") 7))
    (is (= (read-roman "IX") 9))
    (is (= (read-roman "X") 10))
    (is (= (read-roman "XI") 11))
    (is (= (read-roman "XXX") 30))
    (is (= (read-roman "XL") 40))
    (is (= (read-roman "L") 50))))

(deftest writing-test
  (testing "Writing roman numerals"
    (is (= (write-roman 1) "I"))
    (is (= (write-roman 3) "III"))
    (is (= (write-roman 4) "IV"))
    (is (= (write-roman 5) "V"))
    (is (= (write-roman 7) "VII"))
    (is (= (write-roman 9) "IX"))
    (is (= (write-roman 10) "X"))
    (is (= (write-roman 12) "XII"))
    (is (= (write-roman 64) "LXIV"))
    (is (= (write-roman 488) "CDLXXXVIII"))
    ))

(deftest kata-test
  (testing "The examples from the kata"
    (is (= (r-multiply "XVI" "LXIV") (write-roman 1024)) "16 towers each with 64 corner stones")
    (is (= (r-divide "MMMD" "XVI") (write-roman 218)) "3500 roof tiles between 16 towers")
    (is (= (r-minus "XVI" (r-divide "MMMD" "DC")) "XI") "At 600 tiles each, 3500 tiles roof 5 towers, leaving 11 to be thatched")
    ))
