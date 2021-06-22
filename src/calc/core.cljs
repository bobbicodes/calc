(ns calc.core
    (:require
     [reagent.core :as r]
     [reagent.dom :as d]
     [calc.math :as math]))

;; -------------------------
;; Views

(defn rect [x y w h]
  [:rect
   {:width        w
    :height      h
    :fill         "none"
    :x            x
    :y            y
    :stroke       "gray"
    :stroke-width 1}])

(defn x-lines [width n]
  (into [:g]
        (for [line (range n)]
          [:line {:x1 (* line (/ width n)) :y1 0 :x2 (* line (/ width n)) :y2 width :stroke "gray" :stroke-width 1}])))

(defn y-lines [width n]
  (into [:g]
        (for [line (range n)]
          [:line {:x1 0 :y1 (* line (/ width n)) :x2 width :y2 (* line (/ width n)) :stroke "gray" :stroke-width 1}])))

(def grid
  (fn []
    [:path {:d "M0 325V25M18.75 325V25M37.5 325V25M56.25 325V25M75 325V25M93.75 325V25M112.5 325V25M131.25 325V25M150 325V25M168.75 325V25M187.5 325V25M206.25 325V25M225 325V25M243.75 325V25M262.5 325V25M281.25 325V25M300 325V25M0 325h300M0 306.25h300M0 287.5h300M0 268.75h300M0 250h300M0 231.25h300M0 212.5h300M0 193.75h300M0 175h300M0 156.25h300M0 137.5h300M0 118.75h300M0 100h300M0 81.25h300M0 62.5h300M0 43.75h300M0 25h300"
            :stroke "#000"
            :stroke-width 2
            :opacity ".1"}]))

(defn l-arrow []
  [:path {:d "M7.05 169.4c-.35 2.1-4.2 5.25-5.25 5.6 1.05.35 4.9 3.5 5.25 5.6"
          :fill "none"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn l-line []
  [:path {:d "M150 175H1.05"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn r-arrow []
  [:path {:d "M294.45 180.6c.35-2.1 4.2-5.25 5.25-5.6-1.05-.35-4.9-3.5-5.25-5.6"
          :fill "none"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn r-line []
  [:path {:d "M150 175h148.95"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn b-arrow []
  [:path {:d "M145.15 318.7c2.1.35 5.25 4.2 5.6 5.25.35-1.05 3.5-4.9 5.6-5.25"
          :fill "none"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn b-line []
  [:path {:d "M150 175v148.95"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn t-arrow []
  [:path {:d "M156.35 31.3c-2.1-.35-5.25-4.2-5.6-5.25-.35 1.05-3.5 4.9-5.6 5.25"
          :fill "none"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn t-line []
  [:path {:d "M150 175V26.05"
          :stroke "#000"
          :stroke-linejoin "round"
          :stroke-linecap "round"
          :stroke-width 2}])

(defn ticks []
  [:path {:d "M168.75 180v-10M187.5 180v-10M206.25 180v-10M225 180v-10M243.75 180v-10M262.5 180v-10M281.25 180v-10M131.25 180v-10M112.5 180v-10M93.75 180v-10M75 180v-10M56.25 180v-10M37.5 180v-10M18.75 180v-10M145 156.25h10M145 137.5h10M145 118.75h10M145 100h10M145 81.25h10M145 62.5h10M145 43.75h10M145 193.75h10M145 212.5h10M145 231.25h10M145 250h10M145 268.75h10M145 287.5h10M145 306.25h10"
          :stroke "#000"}])

(defn curves []
  [:path {:d "M0 129.25l.5-1.746.5-1.719.5-1.691.5-1.664.5-1.637.5-1.61.5-1.583.5-1.557.5-1.531.5-1.504.5-1.478.5-1.452.5-1.427.5-1.401.5-1.376.5-1.351.5-1.325.5-1.301.5-1.276.5-1.252.5-1.227.5-1.203.5-1.18.5-1.155.5-1.132.5-1.108.5-1.085.5-1.062.5-1.039.5-1.016.5-.994.5-.97.5-.95.5-.926.5-.905.5-.883.5-.861.5-.84.5-.82.5-.797.5-.777.5-.756.5-.735.5-.715.5-.695.5-.675.5-.655.5-.635.5-.616.5-.596.5-.577.5-.558.5-.54.5-.52.5-.502.5-.483.5-.466.5-.447.5-.43.5-.412.5-.395.5-.377.5-.36.5-.343.5-.326.5-.31.5-.293.5-.276.5-.261.5-.245.5-.229.5-.213.5-.198.5-.183.5-.167.5-.153.5-.137.5-.124.5-.108.5-.095.5-.08.5-.067.5-.053.5-.039.5-.026.5-.012h.5l.5.013.5.026.5.039.5.051.5.063.5.075.5.087.5.1.5.11.5.121.5.133.5.144.5.154.5.166.5.175.5.186.5.197.5.206.5.216.5.226.5.235.5.245.5.253.5.263.5.271.5.28.5.288.5.297.5.305.5.313.5.32.5.329.5.335.5.343.5.35.5.356.5.364.5.37.5.377.5.382.5.39.5.394.5.4.5.406.5.412.5.416.5.422.5.426.5.431.5.436.5.44.5.444.5.448.5.452.5.456.5.459.5.463.5.465.5.469.5.471.5.475.5.476.5.479.5.481.5.483.5.485.5.486.5.488.5.49.5.49.5.49.5.493.5.492.5.493.5.493.5.494.5.493.5.493.5.493.5.492.5.49.5.491.5.49.5.487.5.486.5.485.5.483.5.481.5.48.5.476.5.474.5.471.5.469.5.466.5.462.5.46.5.455.5.452.5.448.5.445.5.44.5.435.5.431.5.427.5.421.5.417.5.411.5.406.5.4.5.395.5.389.5.383.5.376.5.37.5.364.5.357.5.349.5.343.5.336.5.328.5.32.5.313.5.305.5.297.5.288.5.28.5.272.5.262.5.254.5.244.5.235.5.226.5.216.5.206.5.197.5.186.5.176.5.165.5.155.5.143.5.133.5.122.5.11.5.099.5.087.5.075.5.063.5.051.5.039.5.026.5.013.5.001.5-.013.5-.026.5-.039.5-.053.5-.066.5-.081.5-.094.5-.109.5-.123.5-.138.5-.153.5-.167.5-.183.5-.198.5-.213.5-.229.5-.245.5-.26.5-.277.5-.293.5-.31.5-.326.5-.343.5-.36.5-.377.5-.395.5-.412.5-.429.5-.448.5-.465.5-.484.5-.502.5-.521.5-.539.5-.558.5-.577.5-.596.5-.616.5-.635.5-.655.5-.675.5-.695.5-.715.5-.735.5-.756.5-.777.5-.798.5-.818.5-.84.5-.862.5-.883.5-.905.5-.926.5-.95.5-.97.5-.994.5-1.016.5-1.039.5-1.062.5-1.085M150.019 268.708l.5-1.121.5-1.11.5-1.102.5-1.092.5-1.082.5-1.074.5-1.064.5-1.055.5-1.046.5-1.037.5-1.028.5-1.02.5-1.011.5-1.002.5-.993.5-.986.5-.976.5-.969.5-.96.5-.952.5-.944.5-.935.5-.928.5-.92.5-.912.5-.904.5-.896.5-.889.5-.881.5-.874.5-.866.5-.859.5-.852.5-.844.5-.837.5-.83.5-.823.5-.815.5-.809.5-.802.5-.795.5-.788.5-.782.5-.775.5-.768.5-.762.5-.755.5-.748.5-.743.5-.736.5-.729.5-.724.5-.717.5-.711.5-.706.5-.699.5-.693.5-.687.5-.681.5-.676.5-.669.5-.664.5-.659.5-.653.5-.647.5-.641.5-.636.5-.631.5-.625.5-.62.5-.615.5-.61.5-.604.5-.599.5-.594.5-.589.5-.584.5-.579.5-.573.5-.57.5-.564.5-.559.5-.555.5-.549.5-.546.5-.54.5-.536.5-.531.5-.527.5-.522.5-.518.5-.514.5-.509.5-.504.5-.501.5-.496.5-.492.5-.487.5-.484.5-.479.5-.475.5-.471.5-.468.5-.463.5-.459.5-.455.5-.452.5-.447.5-.444.5-.44.5-.436.5-.433.5-.429.5-.425.5-.421.5-.418.5-.414.5-.411.5-.407.5-.404.5-.401.5-.396.5-.394.5-.39.5-.387.5-.384.5-.38.5-.377.5-.374.5-.37.5-.368.5-.364.5-.361.5-.358.5-.355.5-.353.5-.349.5-.346.5-.343.5-.34.5-.337.5-.334.5-.332.5-.329.5-.326.5-.323.5-.32.5-.318.5-.314.5-.313.5-.309.5-.307.5-.304.5-.302.5-.299.5-.297.5-.294.5-.291.5-.289.5-.287.5-.284.5-.282.5-.279.5-.277.5-.274.5-.272.5-.27.5-.268.5-.265.5-.263.5-.261.5-.258.5-.257.5-.254.5-.252.5-.25.5-.247.5-.246.5-.243.5-.242.5-.239.5-.237.5-.236.5-.233.5-.231.5-.229.5-.228.5-.225.5-.223.5-.222.5-.22.5-.217.5-.216.5-.214.5-.213.5-.21.5-.209.5-.207.5-.205.5-.203.5-.202.5-.2.5-.198.5-.196.5-.195.5-.193.5-.192.5-.189.5-.189.5-.186.5-.185.5-.184.5-.182.5-.18.5-.179.5-.177.5-.176.5-.174.5-.173.5-.171.5-.17.5-.168.5-.167.5-.166.5-.164.5-.163.5-.161.5-.16.5-.158.5-.158.5-.156.5-.154.5-.153.5-.152.5-.151.5-.149.5-.148.5-.147.5-.146.5-.144.5-.143.5-.142.5-.14.5-.14.5-.138.5-.137.5-.136.5-.135.5-.133.5-.133.5-.131.5-.13.5-.129.5-.128.5-.127.5-.126.5-.125.5-.123.5-.123.5-.121.5-.121.5-.119.5-.119.5-.117.5-.117.5-.115.5-.115.5-.113.5-.113.5-.111.5-.111.5-.11.5-.108.5-.108.5-.107.5-.106.5-.105.5-.104.5-.103.5-.103.5-.101.5-.101.5-.1.5-.099.5-.098.5-.097.5-.097.5-.095.5-.095.5-.094.5-.093.5-.092.5-.092.5-.091.5-.09.5-.089.5-.089.5-.087.5-.087"
          :stroke "#11accd"
          :fill "none"
          :stroke-width 3}])

(defn l-nums []
  [:g 
   #_[:path {:transform "translate(128,195) scale(0.015, -0.015)"
              :d (nth math/small-nums 1)}]
#_[:path {:transform "translate(122,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(109,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 2)}]
[:path {:transform "translate(103,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(90,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 3)}]
[:path {:transform "translate(84,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(71,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 4)}]
[:path {:transform "translate(65,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(53,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 5)}]
[:path {:transform "translate(47,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(34,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 6)}]
[:path {:transform "translate(27,195) scale(0.008, -0.015)"
        :d math/minus-sign}]
[:path {:transform "translate(15,195) scale(0.015, -0.015)"
        :d (nth math/small-nums 7)}]
[:path {:transform "translate(9,195) scale(0.008, -0.015)"
        :d math/minus-sign}]])

(defn b-nums []
  [:g 
   #_[:path {:transform "translate(128,195) scale(0.015, -0.015)"
              :d (nth math/small-nums 1)}]
   #_[:path {:transform "translate(122,195) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,216) scale(0.015, -0.015)"
           :d (nth math/small-nums 2)}]
   [:path {:transform "translate(122,216) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,235) scale(0.015, -0.015)"
           :d (nth math/small-nums 3)}]
   [:path {:transform "translate(122,235) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,255) scale(0.015, -0.015)"
           :d (nth math/small-nums 4)}]
   [:path {:transform "translate(122,255) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,275) scale(0.015, -0.015)"
           :d (nth math/small-nums 5)}]
   [:path {:transform "translate(122,275) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,293) scale(0.015, -0.015)"
           :d (nth math/small-nums 6)}]
   [:path {:transform "translate(122,293) scale(0.008, -0.015)"
           :d math/minus-sign}]
   [:path {:transform "translate(128,313) scale(0.015, -0.015)"
           :d (nth math/small-nums 7)}]
   [:path {:transform "translate(122,313) scale(0.008, -0.015)"
           :d math/minus-sign}]])

(defn t-nums []
  [:g
   [:path {:transform "translate(128,161) scale(0.015, -0.015)"
           :d (nth math/small-nums 1)}]
   [:path {:transform "translate(128,141) scale(0.015, -0.015)"
           :d (nth math/small-nums 2)}]
   [:path {:transform "translate(128,123) scale(0.015, -0.015)"
           :d (nth math/small-nums 3)}]
   [:path {:transform "translate(128,105) scale(0.015, -0.015)"
           :d (nth math/small-nums 4)}]
   [:path {:transform "translate(128,87) scale(0.015, -0.015)"
           :d (nth math/small-nums 5)}]
   [:path {:transform "translate(128,68) scale(0.015, -0.015)"
           :d (nth math/small-nums 6)}]
   [:path {:transform "translate(128,49) scale(0.015, -0.015)"
           :d (nth math/small-nums 7)}]
   #_[:path {:transform "translate(128,31) scale(0.015, -0.015)"
           :d (nth math/small-nums 8)}]
   #_[:path {:transform "translate(128,11) scale(0.015, -0.015)"
           :d (nth math/small-nums 9)}]])

(defn r-nums []
  [:g
   [:path {:transform "translate(165,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 1)}]
   [:path {:transform "translate(184,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 2)}]
   [:path {:transform "translate(203,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 3)}]
   [:path {:transform "translate(220,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 4)}]
   [:path {:transform "translate(240,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 5)}]
   [:path {:transform "translate(258,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 6)}]
   [:path {:transform "translate(278,195) scale(0.015, -0.015)"
           :d (nth math/small-nums 7)}]
   #_[:path {:transform "translate(128,195) scale(0.015, -0.015)"
             :d (nth math/small-nums 8)}]
   #_[:path {:transform "translate(128,195) scale(0.015, -0.015)"
             :d (nth math/small-nums 9)}]])

(defn home-page []
  [:div [:h2 "Limits intro"]
   [:svg {:width    "80%"
         :view-box (str "0 0 325 325")}
    [:g
     [grid]
     [l-arrow]
     [l-line]
     [r-arrow]
     [r-line]
     [b-arrow]
     [b-line]
     [t-arrow]
     [t-line]
     [ticks]
     [curves]
     [:ellipse {:cx "150" :cy "81.668" :rx "4" :ry "4" :fill "#fff" :stroke "#11accd" :stroke-width "2" :stroke-dasharray "0"}]
     [:ellipse {:cx "150" :cy "268.329" :rx "4" :ry "4" :fill "#fff" :stroke "#11accd" :stroke-width "2" :stroke-dasharray "0"}]
     [:ellipse {:cx "150" :cy "81.25" :rx "4" :ry "4" :fill "#11accd" :stroke "#11accd" :stroke-width "2" :stroke-dasharray "0"}]
     [l-nums]
     [b-nums]
     [t-nums]
     [r-nums]
     ]]])
(nth math/small-nums 1)
;; -------------------------
;; Initialize app

(defn mount-root []
  (d/render [home-page] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
