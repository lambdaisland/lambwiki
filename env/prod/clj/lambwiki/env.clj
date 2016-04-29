(ns lambwiki.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[lambwiki started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[lambwiki has shutdown successfully]=-"))
   :middleware identity})
