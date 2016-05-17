# lambwiki

A simple wiki application as seen in the [Lambda Island](https://lambdaisland.com) episode [Introduction to Luminus, part 1](https://lambdaisland.com/episodes/introduction-luminus-1)

<!-- and [part 2](https://lambdaisland.com/episodes/introduction-luminus-2). -->

Assumes a PostgreSQL database. To run locally

```
createdb lambwiki_dev
cp profiles.clj.example profiles.clj
```

Then

- update `profiles.clj` with your database credentials
- run `lein run migrate`
- create the home page:

``` clojure-repl
$ lein repl

Java HotSpot(TM) 64-Bit Server VM 1.8.0_91-b14
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

user=> (start)
{:started ["#'lambwiki.config/env" "#'lambwiki.db.core/*db*" "#'lambwiki.handler/init-app" "#'lambwiki.core/http-server"]}
user=> (lambwiki.db.core/create-page! {:uri_slug "home" :title "Home"})
{:id 1, :created_at #inst "2016-05-17T17:59:55.704-00:00", :uri_slug "home", :title "Home"}
user=> (lambwiki.db.core/create-revision! {:page_id 1 :body "# LambWiki\n\nThis is the home page."})
{:id 1, :created_at #inst "2016-05-17T18:00:27.833-00:00", :page_id 1, :body "# LambWiki\n\nThis is the home page."}
```

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

## License

Copyright © 2016 Arne Brasseur

[Mozilla Public License 2.0](https://www.mozilla.org/en-US/MPL/2.0/")
