(ns tvision.core
  (:require
    [re-frame.core :refer [dispatch dispatch-sync
                           subscribe reg-sub
                           reg-fx
                           reg-event-fx reg-event-db]]
    [tvision.magic :as magic]))

;; Events

(reg-event-db
  :init-db
  (fn [_ _]
    {:x 10, :y 5, :width 20, :height 10, :color :green}))

(reg-event-db
  :move-left
  (fn [db _]
    (update db :x dec)))

(reg-event-db
  :move-right
  (fn [db _]
    (update db :x inc)))

(reg-event-db
  :move-up
  (fn [db _]
    (update db :y dec)))

(reg-event-db
  :move-down
  (fn [db _]
    (update db :y inc)))

(reg-event-db
  :toggle-color
  (fn [db _]
    (update db :color {:green :blue, :blue :red, :red :green})))

;; Subscriptions

(reg-sub
  :x1
  (fn [{:keys [x]} _]
    x))

(reg-sub
  :y1
  (fn [{:keys [y]} _]
    y))

(reg-sub
  :x2
  (fn [{:keys [x width]} _]
    (+ x width)))

(reg-sub
  :y2
  (fn [{:keys [y height]} _]
    (+ y height)))

(reg-sub
  :color
  (fn [{:keys [color]} _]
    color))

;; App

;; I've gotten used to this shortcut
(def <sub (comp deref subscribe))

(defn root []
  [{:type :rectangle,
    :x1 (<sub [:x1]),
    :y1 (<sub [:y1]),
    :x2 (<sub [:x2]),
    :y2 (<sub [:y2]),
    :color (<sub [:color])}])

(def app
  [[root]])

;; Global (!!!) keypress event

(reg-event-fx
  :key-pressed
  (fn [_ [_ {:keys [key] :as keystroke}]]
    (case key
      :escape {:shutdown true}
      \space  {:dispatch [:toggle-color]}
      :left   {:dispatch [:move-left]}
      :right  {:dispatch [:move-right]}
      :up     {:dispatch [:move-up]}
      :down   {:dispatch [:move-down]}
      {})))

(defn run []
  (dispatch-sync [:init-db])
  (magic/run app))
