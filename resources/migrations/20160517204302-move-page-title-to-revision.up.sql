ALTER TABLE revisions ADD COLUMN title TEXT;
--;;
UPDATE revisions SET title=pages.title FROM pages WHERE revisions.page_id=pages.id;
--;;
ALTER TABLE pages DROP COLUMN title;
