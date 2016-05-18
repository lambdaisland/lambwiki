(ns lambwiki.routes.home
  (:require [compojure.core :refer [defroutes GET POST]]
            [lambwiki.db.core
             :refer
             [create-page! create-revision! find-page-by-uri-slug]]
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
  (GET "/:uri_slug/edit" {{:keys [uri_slug] :as params} :params}
    (if-let [page (find-page-by-uri-slug {:uri_slug uri_slug})]
      (edit-page page)
      (edit-page params)))

  ;; submit the form, creating a new revision
  (POST "/:uri_slug" {{:keys [uri_slug title body] :as params} :params}
    (let [page (or (find-page-by-uri-slug {:uri_slug uri_slug})
                   (create-page! {:uri_slug uri_slug}))]
      (create-revision! {:page_id (:id page)
                         :body body
                         :title title})
      (wiki-page params))))
