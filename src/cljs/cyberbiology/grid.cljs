(ns cyberbiology.grid
  (:require
   [reagent.core :as r]
   [cyberbiology.model1 :as model]))

(def cell-size 4)
(def width (* cell-size model/width))
(def height (* cell-size model/height))

(defn draw-fill [ctx x y w h color]
  (set! (.-fillStyle ctx) color)
  (.fillRect ctx x y w h))

(defn draw [ctx x y color]
  ;; setup color:
  (set! (.-fillStyle ctx) color)
  ;; setup blur:
  (set! (.-shadowBlur ctx) cell-size)
  (set! (.-shadowColor ctx) color)
  ;; draw:
  (.fillRect ctx
             (* x cell-size) (* y cell-size) ;; cell size correction of position
             cell-size cell-size))

(defn draw-cell [ctx cell]
  (draw ctx (:x cell) (:y cell) (name (:color cell))))

(defn start-update-cycle [!canvas cells]
  (when-let [canvas @!canvas]
    (let [ctx (.getContext canvas "2d")]

      ;; prepare backgorund:
      (.clearRect ctx 0 0 width height)
      (draw-fill ctx 0 0 width height "black")

      ;; draw cells
      (doall
       (map (partial draw-cell ctx) cells))

      ;; next simulation step:
      (js/setTimeout
       (partial start-update-cycle !canvas (model/step cells))
       100))))

(defn grid []
  (let [!canvas (r/atom nil)]

    (js/setTimeout
     (partial start-update-cycle !canvas (model/get-cells))
     1000)

    ;; render func
    (fn []
      [:canvas
       {:style {:outline "1px gold solid"}
        :width width
        :height height
        :ref (fn [el]
               (reset! !canvas el))}])))
