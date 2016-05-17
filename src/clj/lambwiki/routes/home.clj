(ns lambwiki.routes.home
  (:require [compojure.core :refer [defroutes GET POST]]
            [lambwiki.db.core :refer [find-page-by-uri-slug]]
            [lambwiki.layout :as layout]
            [ring.util.http-response :refer [found]]))

(defn wiki-page [page]
  (layout/render
    "wiki-page.html" page))

(defn edit-page [page]
  (layout/render
    "edit-page.html" page))

(defroutes home-routes
  (GET "/" []
    (found "/home"))
  (GET "/:uri_slug" [uri_slug]
    (if-let [page (find-page-by-uri-slug {:uri_slug uri_slug})]
      (wiki-page page)))

  ;; show the form to edit a page
  (GET "/:uri_slug/edit" [uri_slug]
    (if-let [page (find-page-by-uri-slug {:uri_slug uri_slug})]
      (edit-page page)))

  ;; submit the form, creating a new revision
  (POST "/:uri_slug" _))
