(defproject antonine "0.2.1-SNAPSHOT"
  :aot :all
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to "docs/js/antonine.js"}}]}
  :dependencies [[instaparse "1.5.0"]
                 [org.clojure/clojure "1.11.3"]
                 [org.clojure/clojurescript "1.11.132"]
                 [org.clojure/tools.cli "1.1.230"]
                 [org.jline/jline "3.23.0"]]
  :description "A calculator which uses Roman numerals."
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl-2.0.html"}
  :main antonine.core
  :plugins [[com.bhauman/figwheel-main "0.2.18"]
            [com.bhauman/rebel-readline-cljs "0.1.4"]
            [lein-cljsbuild "1.1.8"]]
  :profiles {:jar {:aot :all}
             :uberjar {:aot :all}}
  :url "http://example.com/FIXME")
