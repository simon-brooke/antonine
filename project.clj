(defproject antonine "0.2.0"
  :aot :all
  :description "A calculator which uses Roman numerals."
  :url "http://example.com/FIXME"
  :license {:name "GNU General Public License"
            :url "http://www.gnu.org/licenses/gpl-2.0.html"}
  :main antonine.core
  :dependencies [[instaparse "1.5.0"]
                 [org.clojure/clojure "1.11.3"]
                 [org.clojure/tools.cli "1.1.230"]
                 [org.jline/jline "3.23.0"]]
  :plugins [[lein-cljsbuild "1.1.8"]]
  :profiles {:jar {:aot :all}
             :uberjar {:aot :all}}
  )
