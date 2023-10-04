(ns hw2.main
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn main [file]
  (defn word-count [file]
    (with-open [rdr (io/reader file)]
      (let [lines (line-seq rdr)
            words (mapcat #(str/split % #"\s+") lines)]
        (count words))))

  (defn get-word-frequency-map [file]
    (with-open [rdr (io/reader file)]
      (let [lines (line-seq rdr)
            words (mapcat #(str/split % #"\s+") lines)
            counts (reduce (fn [accumulate word]
                             (update accumulate word (fnil inc 0)))
                           {}
                           words)]
        counts)))

  (defn sentiment-analysis [file]
    (with-open [rdr (io/reader file)]
      (let [lines (line-seq rdr)
            words (mapcat #(str/split % #"\s+") lines)
            positive-words #{"popular" "popularised" "like" "Good" "recently" "survived" "essentially" "ethics" "discovered"}
            negative-words #{"random" "obscure" "unhappy" "dummy" "Evil" "not" "unknown" "old" "undoubtable" "simply"}
            positive-count (count (filter positive-words words))
            negative-count (count (filter negative-words words))]
        (cond
          (> positive-count negative-count) "Positive"
          (< positive-count negative-count) "Negative"
          :else "Neutral"))))

  (let [num-words (word-count file)
        word-frequencies (get-word-frequency-map file)
        sentiment (sentiment-analysis file)]
    (println (str "Number of words: " num-words))
    (println (str "Word frequency map: " word-frequencies))
    (println (str "Sentiment of the text file: " sentiment))))

(main "test.txt")
