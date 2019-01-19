(defproject clj-tvision "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.4.490"]
                 [org.clojars.nathell/clojure-lanterna "0.11.0"]
                 ;; Look, ma, no cljs!
                 [re-frame "0.10.6" :exclusions [reagent org.clojure/clojurescript]]])
