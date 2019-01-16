(defproject io.replikativ/hitchhiker-tree "0.1.2"
  :description "A Hitchhiker Tree Library"
  :url "https://github.com/dgrnbrg/hitchhiker-tree"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :aliases {"bench" ["with-profile" "profiling" "run" "-m" "hitchhiker.bench"]}
  :jvm-opts ["-server" "-Xmx3700m" "-Xms3700m"]
  :profiles {:test
             {:dependencies [[org.clojure/test.check "0.9.0"]]}
             :profiling
             {:main hitchhiker.bench
              :source-paths ["env/profiling"]
              :dependencies [[criterium "0.4.4"]
                             [org.clojure/tools.cli "0.3.3"]
                             [org.clojure/test.check "0.9.0"]
                             [com.infolace/excel-templates "0.3.3"]]}
             :dev {:dependencies [#_[binaryage/devtools "0.8.2"]
                                  #_[figwheel-sidecar "0.5.8"]
                                  #_[com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/test.check "0.9.0"]
                                  ;; plotting
                                  [aysylu/loom "1.0.1"]
                                  [cheshire "5.8.0"]]
                   :source-paths ["src" "dev"]
                   ;:plugins [[lein-figwheel "0.5.8"]]
                   :repl-options {; for nREPL dev you really need to limit output
                                  :init (set! *print-length* 50)
                                  #_:nrepl-middleware #_[cemerick.piggieback/wrap-cljs-repl]}}}
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :figwheel true
                :source-paths ["src"]
                :compiler {:main hitchhiker.tree.core
                           :asset-path "js/out"
                           :output-to "resources/public/js/core.js"
                           :output-dir "resources/public/js/out" }}
               ;; inspired by datascript project.clj
               {:id "test"
                :source-paths ["src" "test" "dev"]
                :compiler {
                           :main          hitchhiker-tree.konserve-test
                           :output-to     "target/test.js"
                           :output-dir    "target/none"
                           :optimizations :none
                           :source-map    true
                           :recompile-dependents false
                           :parallel-build true
                           }}
               ]}

  :plugins [[lein-tools-deps "0.4.3"]
            [lein-figwheel "0.5.8"]
            [lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]})
