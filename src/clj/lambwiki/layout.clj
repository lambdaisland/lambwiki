(ns lambwiki.layout
  (:require [clojure.string :as str]
            [hiccup.core :as hiccup]
            [markdown.core :refer [md-to-html-string]]
            [ring.middleware.anti-forgery :refer [*anti-forgery-token*]]
            [ring.util
             [anti-forgery :refer [anti-forgery-field]]
             [codec :refer [url-encode]]
             [http-response :refer [content-type ok]]]
            [selmer
             [filters :as filters]
             [parser :as parser]]))

(declare ^:dynamic *app-context*)
(parser/set-resource-path!  (clojure.java.io/resource "templates"))
(parser/add-tag! :csrf-field (fn [_ _] (anti-forgery-field)))
(filters/add-filter! :markdown (fn [content] [:safe (md-to-html-string content)]))

(defn- title->uri-slug [title]
  (str/lower-case (str/replace title #"\W+" "-")))

(defn- link-attrs [uri-slug title]
  (if (find-page-by-uri-slug {:uri_slug uri-slug})
    {:href (str "/" uri-slug)}
    {:href (str "/" uri-slug "/edit?title=" (url-encode title)) :class "new-page-link"}))

(defn wiki-links [content]
  (str/replace content #"\[\[([^\]]+)\]\]"
               (fn [[_ title]]
                 (let [uri-slug (title->uri-slug title)]
                   (hiccup/html
                    [:a (link-attrs uri-slug title) title])))))

(filters/add-filter! :wiki-links wiki-links)

(defn render
  "renders the HTML template located relative to resources/templates"
  [template & [params]]
  (content-type
    (ok
      (parser/render-file
        template
        (assoc params
          :page template
          :csrf-token *anti-forgery-token*
          :servlet-context *app-context*)))
    "text/html; charset=utf-8"))

(defn error-page
  "error-details should be a map containing the following keys:
   :status - error status
   :title - error title (optional)
   :message - detailed error message (optional)

   returns a response map with the error page as the body
   and the status specified by the status key"
  [error-details]
  {:status  (:status error-details)
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (parser/render-file "error.html" error-details)})
