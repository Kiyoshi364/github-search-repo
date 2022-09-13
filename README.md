# Github Search Repo [WIP]

Server that uses [Github's Search Repositories API](https://docs.github.com/en/rest/search#search-repositories).

# Building

Requires [Maven 3](https://maven.apache.org) to build.

```console
mvn clean package
```

The jar file is
`target/github-search-repo-1.0-jar-with-dependencies.jar`
and can be ran with

```console
java -jar target/github-search-repo-1.0-jar-with-dependencies.jar
```

# Running the server

* Using Maven 3:
    ```console
    mvn clean install
    mvn exec:java -D exec.mainClass=com.kiyoshi364.gsr.Main
    ```
    > **Note:** May add `-D exec.args=ARGS` to pass arguments.

* Using Java:
    ```console
    mvn clean package
    java -jar target/github-search-repo-1.0-jar-with-dependencies.jar
    ```
    > **Note:** May add the arguments at the end.

* Using Heroku:
    ```console
    mvn clean install
    heroku local
    ```

# CLI

By default, the program runs the server,
but if the first argument is "cli"
it will run in the CLI mode.

Arguments are passed in the form `key=value`,
or only `key` when there is no need for a value.

Consider running with `help` flag:
```console
java -jar target/github-search-repo-1.0-jar-with-dependencies.jar cli help
```

> **Note:** `q` is _required_
