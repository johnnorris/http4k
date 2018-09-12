title: http4k Authorising requests
description: Recipes for looking up and populating a user principal from a request


### Gradle setup
```
    compile group: "org.http4k", name: "http4k-core", version: "3.37.1"
```

When authorising requests, it is common to need to store some credentials or a user principal object to be accessible by a further Filter or the eventual HttpHandler.

This can be easily achieved by combining the typesafe RequestContext functionality with one of the built-in authorisation Filters:

### Code [<img class="octocat" src="/img/octocat-32.png"/>](https://github.com/http4k/http4k/blob/master/src/docs/cookbook/principal/example.kt)
<script src="https://gist-it.appspot.com/https://github.com/http4k/http4k/blob/master/src/docs/cookbook/principal/example.kt"></script>