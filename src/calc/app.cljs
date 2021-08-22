(ns calc.app
  (:require [reagent.core :as r]
            [reagent.dom :as rdom]
            [clojure.pprint :as pprint]
            [clojure.edn :as edn]
            [calc.graph :as graph]))

(defn svg-node [label x y]
  [:g [:circle {:fill "yellow" :stroke "black" :cx x :cy y :r 18}]
   [:text {:text-anchor "middle" :x x :y (+ 4 y) :font-family "Monospace"
           :font-size 14} label]])

(defn svg-leaf [label x y]
  [:g [:rect {:fill "yellow" :stroke "black" :x (- x 14) :y (- y 14) :width 28 :height 28}]
   [:text {:text-anchor "middle" :x x :y (+ 4 y) :font-family "Monospace"
           :font-size 14} label]])

(defn svg-edge
  "Coordinates are for the text label (edge weight).
   Path is the edge spline.
   Polygon points are for the arrows."
  [label x y path points]
  [:g
   [:path {:fill "none" :stroke "black" :d path}]
   [:polygon {:fill "black" :stroke "black" :points points}]
   [:text {:text-anchor "middle" :x x :y y :font-family "Monospace" :font-size 14.00} label]])

(defn g []
  {:1 {:2 1 :3 2}
   :2 {:4 3 :5 4}
   :3 {:6 5 :7 6}
   :4 {}
   :5 {}
   :6 {}
   :7 {}})

(def g3
  {:1 {:2 1 :3 2}
   :2 {:4 3 :5 4}
   :3 {:6 5 :7 6}
   :4 {:8 1 :9 1}
   :5 {:10 1 :11 1}
   :6 {:12 1 :13 1}
   :7 {:14 1 :15 1}
   :8 {}
   :9 {}
   :10 {}
   :11 {}
   :12 {}
   :13 {}
   :14 {}
   :15 {}})

(defn parent [node tree]
  (first (filter #(contains? (get tree %) node) (keys tree))))

(parent :2 g3)

(defn node-depth [node tree]
  (loop [n node parents []]
    (if (nil? (parent n tree))
      (count parents)
      (recur (parent n tree) (conj parents (parent n tree))))))

(node-depth :8 g3)

(defn tree-height [tree]
  (inc (apply max (map #(node-depth % tree) (keys tree)))))

(tree-height g3)

(defn leaf-nodes [tree]
  (filter #(empty? (get tree %)) (keys tree)))

(count (leaf-nodes g3))

(.-innerWidth js/window)
(.-innerHeight js/window)

(filter (fn [[node children]] (seq children)) g3)

(def node-locs
  [[273 -279]
   [215 -192]                            [315 -192]
   [99 -105]        [215 -105]          [315 -105]          [459 -105]
   [27 -18] [99 -18] [171 -18] [243 -18] [315 -18] [387 -18] [459 -18] [531 -18]])

(defonce my-nodes (r/atom [1]))

(reset! my-nodes [1 2 3])

(get node-locs 1)

(defn svg-nodes []
  (into [:g]
        (for [node (range (count @my-nodes))]
          (svg-node (str (get @my-nodes node)) (first (get node-locs node)) (last (get node-locs node))))))

(def edges 
  [[svg-edge ""  251.5 -231.8 "M262.362,-262.41C253.75,-249.788 241.467,-231.788 231.568,-217.281" "234.295,-215.067 225.767,-208.779 228.512,-219.012 234.295,-215.067"]
   [svg-edge ""  300.5 -231.8 "M281.098,-261.611C287.141,-249.382 295.511,-232.443 302.422,-218.456" "305.635,-219.854 306.927,-209.339 299.359,-216.753 305.635,-219.854"]
   [svg-edge ""  167.5 -144.8 "M197.382,-178.09C178.134,-163.986 147.108,-141.251 125.047,-125.086" "126.919,-122.119 116.784,-119.031 122.781,-127.765 126.919,-122.119"]
   [svg-edge ""  219.5 -144.8 "M215,-173.799C215,-162.163 215,-146.548 215,-133.237" "218.5,-133.175 215,-123.175 211.5,-133.175 218.5,-133.175"]
   [svg-edge ""  319.5 -144.8 "M315,-173.799C315,-162.163 315,-146.548 315,-133.237" "318.5,-133.175 315,-123.175 311.5,-133.175 318.5,-133.175"]
   [svg-edge ""  399.5 -144.8 "M334.721,-179.359C359.603,-164.672 402.56,-139.315 430.879,-122.599" "432.685,-125.597 439.518,-117.5 429.127,-119.569 432.685,-125.597"]
   [svg-edge ""  72.5 -57.8 "M86.1278,-88.8037C75.0702,-75.7495 58.9641,-56.7354 46.353,-41.8473" "48.8949,-39.433 39.7607,-34.0647 43.5535,-43.9574 48.8949,-39.433"]
   [svg-edge ""  103.5 -57.8 "M99,-86.799C99,-75.1626 99,-59.5479 99,-46.2368" "102.5,-46.1754 99,-36.1754 95.5001,-46.1755 102.5,-46.1754"]
   [svg-edge ""  199.5 -57.8 "M206.516,-87.6111C200.186,-75.3817 191.418,-58.4429 184.177,-44.456" "187.163,-42.6103 179.458,-35.3385 180.946,-45.8283 187.163,-42.6103"]
   [svg-edge ""  234.5 -57.8 "M220.532,-87.2067C224.444,-75.332 229.768,-59.1684 234.252,-45.5567" "237.675,-46.3514 237.48,-35.7584 231.027,-44.1613 237.675,-46.3514"]
   [svg-edge ""  319.5 -57.8 "M315,-86.799C315,-75.1626 315,-59.5479 315,-46.2368" "318.5,-46.1754 315,-36.1754 311.5,-46.1755 318.5,-46.1754"]
   [svg-edge ""  359.5 -57.8 "M327.872,-88.8037C338.93,-75.7495 355.036,-56.7354 367.647,-41.8473" "370.446,-43.9574 374.239,-34.0647 365.105,-39.433 370.446,-43.9574"]
   [svg-edge ""  463.5 -57.8 "M459,-86.799C459,-75.1626 459,-59.5479 459,-46.2368" "462.5,-46.1754 459,-36.1754 455.5,-46.1755 462.5,-46.1754"]
   [svg-edge ""  503.5 -57.8 "M471.872,-88.8037C482.93,-75.7495 499.036,-56.7354 511.647,-41.8473" "514.446,-43.9574 518.239,-34.0647 509.105,-39.433 514.446,-43.9574"]])

(defn dag3 []
  [:svg {:width "100%" :viewBox "0 0 566 305"}
   (into
    [:g {:transform "scale(1,1), rotate(0), translate(4,301)"}
     [svg-nodes]]
    (take (dec (count @my-nodes)) edges))])



(defn app []
  [:div#app
   ;[conway/game]
   [graph/function-input]
   [graph/calc-graph]
   ;[dag3]
   #_[:p (str "Tree height: " (tree-height g3))]
   #_[:p (str "Tree width: " (count (leaf-nodes g3)))]
   #_[:center
    ;[:h1 "Dijkstra's Algorithm"]
    ;[sort/sort-widget]
    ;[dag]
    ;[:p (str @graph)]
    ;[:p (str (:nodes @graph-db))]
    ;[:p (str "Unvisited: " (:unvisited @graph-db))]
    ;[:p (str "Current node: " (:current-node @graph-db))]
      #_[:button
         {:on-click
          (fn step-click [e]
            (run! #(update-node! % @graph) (keys ((:current-node @graph-db) @graph)))
            (mark-visited!)
            (swap! step inc))}
         "Next"]
      #_[:button
         {:on-click
          (fn step-click [e]
            (reset! step 0)
            (init-graph! @graph @starting-node))}
         "Reset"]
      #_[:p (str "Step " @step)]
      #_[:button
         {:on-click
          (fn step-click [e]
            (swap! graph (rand-graph @rand-nodes @rand-nodes)))}
         "Random graph"]
;[rand-nodes-input]
      ]])

(defn render []
  (rdom/render [app]
            (.getElementById js/document "root")))

(defn ^:dev/after-load start []
  (render)
  (js/console.log "start"))

(defn ^:export init []
  (js/console.log "init")
  (start))

(defn ^:dev/before-load stop []
  (js/console.log "stop"))
