(ns cyberbiology.model1)

(def width 450)
(def height 200)

(def colors [:white :red :green :blue :yellow :cyan])
(def cells-to-spawn 1000)

(defn get-cells []
  (reduce #(conj %1 {:x (rand-int width)
                     :y (rand-int height)
                     :color (get colors (rand-int (count colors)))})
          []
          (range cells-to-spawn)))

(defn move [cord border]
  (let [directional-move (+ cord (- 1 (rand-int 3)))]
    (cond
      (>= directional-move border) (- border 1)
      (< directional-move 0) 0
      :else directional-move)))

(defn make-chaotic-movement [cells]
  (map
   (fn [cell]
     {:x (move (:x cell) width)
      :y (move (:y cell) height)
      :color (:color cell)})
   cells))

(defn sort-by-coords [cells]
  (->> cells
       (sort-by :x)
       (sort-by :y)))

(defn cells-colide? [c1 c2]
  (and (= (:x c1) (:x c2))
       (= (:y c1) (:y c2))))

;; todo: inplace function
(defn mark-dead [cells]
  (reduce (fn [acc curr]
            (conj acc (if-let [prev (last acc)]
                        (if (cells-colide? prev curr)
                          (assoc curr :dead true)
                          curr)
                        curr)))
          []
          cells))

(defn filter-dead-cells [cells]
  (filter #(not= (:dead %) true) cells))

(defn step [cells]
  (-> cells
      make-chaotic-movement
      sort-by-coords
      mark-dead
      filter-dead-cells))

(comment
  (def source [{:x 3 :y 1} {:x 2 :y 2} {:x 3 :y 1 :dead true}])

  (partition 2 1 source)

  (->> source
       (sort-by :x)
       (sort-by :y))

  (conj [1 2 3] 3))

