;;; HERE BE DRAGONS

(ns tvision.magic
  (:require
    [clojure.core.async :as async]
    [lanterna.constants :as constants]
    [lanterna.screen :as screen]
    [re-frame.core :refer [dispatch dispatch-sync]])
  (:import
    [com.googlecode.lanterna SGR TextCharacter]))

(def screen (atom nil))

(def input-events-ch (atom nil))

(defonce orig-next-tick re-frame.interop/next-tick)

(let [mutex (Object.)]
  (defn dbg [& args]
    (locking mutex
      (apply prn args))))

(alter-var-root
 #'re-frame.interop/next-tick
 (constantly
  (fn [f]
    (when-let [ch @input-events-ch]
      (async/>!! ch :next-tick))
    (orig-next-tick f))))

(defn get-back-buffer [screen]
  (let [method (.getDeclaredMethod com.googlecode.lanterna.screen.AbstractScreen "getBackBuffer" (into-array Class []))]
    (.setAccessible method true)
    (.invoke method screen (into-array []))))

(defn start! []
  (when-not @screen
    (reset! screen (screen/get-screen :swing))
    (screen/start @screen)
    (reset! input-events-ch (async/chan 1))
    (async/thread
      (while @screen
        (async/>!! @input-events-ch (screen/get-keystroke-blocking @screen))))))

(defn stop! []
  (when @screen
    (screen/stop @screen)
    (reset! screen nil)
    (reset! input-events-ch nil)))

(defmulti render-primitive :type)

(defmethod render-primitive :rectangle [{:keys [x1 y1 x2 y2 color]}]
  (let [ch (TextCharacter. \space
                           (constants/colors :default)
                           (constants/colors color)
                           (into-array SGR []))
        buffer (get-back-buffer @screen)]
    (doseq [y (range y1 (inc y2))
            x (range x1 (inc x2))]
      (.setCharacterAt buffer x y ch))))

(defn normalize-components
  [components]
  (mapcat
   (fn [component]
     (if (vector? component)
       (let [[comp-fn & args] component]
         (normalize-components (apply comp-fn args)))
       [component]))
   components))

(defn clear-screen!
  []
  (let [[w h] (screen/get-size @screen)]
    (render-primitive {:type :rectangle, :color :default, :x1 0, :y1 0, :x2 (dec w), :y2 (dec h)})))

(defn render-app! [app]
  (clear-screen!)
  (doseq [primitive (normalize-components app)]
    (render-primitive primitive)))

(defn shutdown! []
  (async/close! @input-events-ch))

(defn run [app]
  (dispatch-sync [:init-db])
  (start!)
  (loop []
    (render-app! app)
    (screen/redraw @screen)
    (when-let [event (async/<!! @input-events-ch)]
      (when-not (= event :next-tick)
        (dispatch [:press-key event]))
      (recur)))
  (stop!))
