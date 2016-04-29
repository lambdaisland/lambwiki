(ns user
  (:require [mount.core :as mount]
            lambwiki.core))

(defn start []
  (mount/start-without #'lambwiki.core/repl-server))

(defn stop []
  (mount/stop-except #'lambwiki.core/repl-server))

(defn restart []
  (stop)
  (start))


