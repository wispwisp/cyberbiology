(defproject cyberbiology "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/clojurescript "1.11.54"]
                 [org.clojure/core.async "1.6.681"]
                 [cljsjs/react "17.0.2-0"]
                 [cljsjs/react-dom "17.0.2-0"]
                 [reagent "1.2.0"]]

  :min-lein-version "2.5.3"
  :source-paths ["src/cljs"]

  :aliases {"fig" ["trampoline" "run" "-m" "figwheel.main"]
            "build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]}

  :profiles
  {:dev
   {:dependencies [[com.bhauman/rebel-readline-cljs "0.1.4"]
                   [com.bhauman/figwheel-main "0.2.18"]
                   [org.slf4j/slf4j-nop "1.7.30"]]}}

  :cljsbuild
  {:builds
   [{:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            cyberbiology.core
                    :optimizations   :advanced
                    :output-to       "resources/public/js/app.js"
                    :output-dir      "resources/public/js/min"
                    :elide-asserts   true
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})

