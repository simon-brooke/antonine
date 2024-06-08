(ns antonine.core
  (:require [antonine.calculator :refer [calculate write-roman]]
            [antonine.char-reader :refer [read-chars]]
            [clojure.java.io :refer [resource]]
            [clojure.pprint :refer [pprint]]
            [clojure.string :as s :refer [trim upper-case]]
            [clojure.tools.cli :refer [parse-opts]]
            [instaparse.core :refer  [parser]])
  (:gen-class))

(defn- romanise [arg]
  (s/replace
   (s/replace
    (upper-case arg)
    "J" "I")
   "U" "V"))

(def cli-options
  [["-b" "--banner BANNER" "The welcome banner to display"
    :default (romanise "Antonius ornare | Simon rivulus hoc fecit | MMXXIV")]
   ["-p" "--prompt PROMPT" "The prompt to display"
    :default "COMPVTARE | "]
   ["-s" "--stop-word WORD" "The stop word to use at the end of a session"
    :default "FINIS"]
   ;; A non-idempotent option (:default is applied first)
   ["-v" "--verbosity LEVEL" "Verbosity level"
    :default 0
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 6) "Must be a number between 0 and 5"]]
   ;; A boolean option defaulting to nil
   ["-h" "--help"]])

(def grammar (parser (resource "grammar.bnf")))

(defn repl
  "Read/eval/print loop, using these command line `options`."
  [options]
  (let [prompt (:prompt options)
        stop-word (:stop-word options)
        vrb (:verbosity options)]
    (try (loop []
           (flush)
           (try
             (if-let [input (trim (upper-case (read-chars prompt)))]
               (if (or (empty? input) (= input stop-word))
                 (throw 
                  (ex-info 
                   (format "\nVALE %s" (romanise (System/getProperty "user.name"))) 
                   {:cause :quit}))
                 (let [tree (grammar input)
                       v (calculate tree)]
                   (when (> vrb 1) 
                     (println (format "(Parse tree: %s)" tree)))
                   (when (> vrb 0) (println (format "(Arabic: %d)" v)))
                   (println (write-roman v))))
               (println))
             (catch
              Exception
              e
               (let [data (ex-data e)]
                 (println (.getMessage e))
                 (when
                  data
                   (case (:cause data)
                     :parse-failure (println (:failure data))
                     :strict nil ;; the message, which has already been printed, is enough.
                     :quit (throw e)
              ;; default
                     (pprint data))))))
           (recur))
         (catch Throwable i
           (if (= :quit (:cause (ex-data i)))
             nil
             (throw i))))))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    (when errors
      (doall (map println errors))
      (println summary)
      (System/exit 1))
    (when (and (empty? arguments) (:banner options))
      (println (:banner options))
      (println))
    (when (>= (:verbosity options) 5)
      (pprint (parse-opts args cli-options))
      (println))
    (when (:help options)
      (println summary)
      (System/exit 0))

    (if (empty? arguments) 
      (repl options)
      (println (write-roman (calculate (grammar (trim (s/join " " arguments)))))))))