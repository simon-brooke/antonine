(ns antonine.char-reader
  "Provide sensible line editing and history recall."
  (:import [org.jline.reader.impl.completer StringsCompleter]
           [org.jline.reader.impl DefaultParser DefaultParser$Bracket]
           [org.jline.reader LineReaderBuilder]
           [org.jline.terminal TerminalBuilder]
           [org.jline.widget AutopairWidgets AutosuggestionWidgets]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;
;;; Copyright (C) 2022-2024 Simon Brooke
;;;
;;; This program is free software; you can redistribute it and/or
;;; modify it under the terms of the GNU General Public License
;;; as published by the Free Software Foundation; either version 2
;;; of the License, or (at your option) any later version.
;;; 
;;; This program is distributed in the hope that it will be useful,
;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;;; GNU General Public License for more details.
;;; 
;;; You should have received a copy of the GNU General Public License
;;; along with this program; if not, write to the Free Software
;;; Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Adapted (simplified) from the Beowulf line reader, this allows history and
;; line editing but really nothing more sophisticated. It ought not to allow
;; brackets, since Antonine doesn't allow these, but presently it does.

(def get-reader
  "Return a reader, first constructing it if necessary.
   
   **NOTE THAT** this is not settled API. The existence and call signature of
   this function is not guaranteed in future versions."
  (memoize (fn []
            (let [term (.build (.system (TerminalBuilder/builder) true))]
              (-> (LineReaderBuilder/builder)
                  (.terminal  term)
                  (.build))))))

(defn read-chars
  "A drop-in replacement for `clojure.core/read-line`, except that line editing
   and history should be enabled."
  [prompt]
  (let [eddie (get-reader)]
    (loop [s (.readLine eddie (str prompt " "))]
      (if (and (= (count (re-seq #"\(" s))
                  (count (re-seq #"\)" s)))
               (= (count (re-seq #"\[]" s))
                  (count (re-seq #"\]" s))))
        s
        (recur (str s " " (.readLine eddie ":: ")))))))
