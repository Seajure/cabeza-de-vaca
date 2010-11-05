(ns cabeza.de.vaca.test
  (:use [cabeza.de.vaca] :reload)
  (:use [clojure.test]))

(deftest test-listening
  (let [events (atom {})
        git-listener (partial swap! events assoc :git)
        bogus-listener (partial swap! events assoc :bogus)]
    (add-listener "_git._tcp.local." git-listener)
    (add-listener "_aeou._tcp.local." bogus-listener)
    (Thread/sleep 500)
    (is (:git @events))
    (is (not (:bogus @events)))))

(deftest test-announce
  (let [events (atom [])
        monkey-listener (partial swap! events conj)]
    (register "_monkey._tcp.local." "bobo" 5555 "banana!")
    (add-listener "_monkey._tcp.local." monkey-listener)
    (is (pos? (count @events)))))
