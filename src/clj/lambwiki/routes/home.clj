(ns lambwiki.routes.home
  (:require [compojure.core :refer [defroutes GET]]
            [lambwiki.db.core :refer [find-page-by-uri-slug]]
            [lambwiki.layout :as layout]
            [ring.util.http-response :refer [found]]))

(defn wiki-page [page]
  (layout/render
    "wiki-page.html" page))

(defroutes home-routes
  (GET "/" []
    (found "/home"))
  (GET "/:uri_slug" [uri_slug]
    (if-let [page (find-page-by-uri-slug {:uri_slug uri_slug})]
      (wiki-page page))))
