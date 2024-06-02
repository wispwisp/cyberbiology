(ns cyberbiology.core
  (:require
   [cyberbiology.grid :as grid]
   [reagent.dom :as d]))

(defn apppage []
  [:div.center-single-element
   [grid/grid]])

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (enable-console-print!) ;; println to js console
    (println "dev mode")))

(defn reload []
  (d/render apppage (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))

