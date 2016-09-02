(ns geturls.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as string]))

(defn make-url [number]
  (str "https://ndb.nal.usda.gov/ndb/foods/show/" number "?format=Abridged&reportfmt=csv"))

(defn copy [uri file]
  (with-open [in (io/input-stream uri)
              out (io/output-stream file)]
    (io/copy in out)))

(defn -main [& args]
  (let [numbers (range 9315)]
    (doall (map
            (fn [v]
              (let [[f name] v]
                (f name)))
            (map
             vector
             (map #(partial copy %) (map make-url numbers))
             (map #(str "csvs/" % ".csv") numbers))))))
