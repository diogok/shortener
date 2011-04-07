# shorten

URL shortener in clojure+redis.

## CONFIG

Take a look at config.json

## HTTP API

Make a POST to /shorten like "url=http://google.com" and will receive the shortened url.
Make a GET to the shortened url to be redirected (301 + Location header).
Make a GET to /resolver/url , and url here is the shortpart to receive where is points to.

## RUNING

Better run with -XX:+UseConcMarkSweepGC and -server .
lein run
java -jar shorten-0.1-standalone.jar -XX:+UseConcMarkSweepGC -server


## License

Copyright (C) 2010 diogok

Distributed under the Eclipse Public License, the same as Clojure.
