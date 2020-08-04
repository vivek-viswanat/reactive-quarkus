# code-with-quarkus project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application is packageable using `./mvnw package`.
It produces the executable `code-with-quarkus-1.0.0-SNAPSHOT-runner.jar` file in `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or you can use Docker to build the native executable using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your binary: `./target/code-with-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image-guide .

## Deployment

### Native deployment in Docker
````
> ./mvnw package -Pnative -Dquarkus.native.container-build=true    
> docker build -f src/main/docker/Dockerfile.native -t quarkus/code-with-quarkus . 
> docker run -d --rm -p 8080:8080 --name java-quarkus-native --cpus 2 quarkus/code-with-quarkus
````

### JVM deployment in Docker
````
> ./mvnw package
> docker build -f src/main/docker/Dockerfile.jvm -t quarkus/code-with-quarkus-jvm .
> docker run -d --rm -p 8080:8080 --name java-quarkus-jvm --cpus 2 quarkus/code-with-quarkus
````

## Benchmark
````
> cargo install drill
> drill --benchmark benchmark.yml --stats
````