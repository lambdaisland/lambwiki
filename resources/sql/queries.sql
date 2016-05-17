-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)

-- :name update-user! :! :n
-- :doc update an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieve a user given the id.
SELECT * FROM users
WHERE id = :id

-- :name delete-user! :! :n
-- :doc delete a user given the id
DELETE FROM users
WHERE id = :id

-- :name create-page! :insert :raw
INSERT INTO pages (uri_slug) VALUES (:uri_slug)

-- :name create-revision! :insert :raw
INSERT INTO revisions (page_id, body, title) VALUES (:page_id, :body, :title)

-- :name find-page-by-uri-slug :query :one
SELECT pages.id, pages.uri_slug, revisions.body, revisions.title
FROM pages, revisions
WHERE pages.id = page_id
AND uri_slug=:uri_slug
ORDER BY revisions.created_at DESC
LIMIT 1
