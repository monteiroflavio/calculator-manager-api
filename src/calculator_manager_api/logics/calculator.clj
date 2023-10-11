(ns calculator-manager-api.logics.calculator)

(defn sqrt [value]
  (if (> value 0) (Math/sqrt value) 0))