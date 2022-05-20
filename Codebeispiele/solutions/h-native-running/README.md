# building an uber jar
set application property quarkus.package.type=uber-jar 

# changes
* see all dependencies added to pom.xml (quarkus-container-image-docker, quarkus-container-image-jib, quarkus-kubernetes)
* ./mvnw quarkus:add-extension -Dextensions="container-image-docker"

# Building a linux native
* building the native app leads to an error with the java faker lib -> rewrite CustomerFactory so that it does not use JavaFaker
* in terminal execute ./mvnw clean 
* then ./mvnw package -Pnative -D"quarkus.native.container-build"=true
* binary is then located in target dir -> copy to linux system and run

# Building a docker image using the prepared Dockerfile
* ./mvnw package -Pnative -D"quarkus.native.container-build"=true 
* docker build -f src/main/docker/Dockerfile.native -t quarkus-quickstart/getting-started .
* docker images -> look for image
* run the image -> docker run --name quarkus -d -p8081:8081 mschaffl/quarkus:V1

# Automatically building a docker image
* add the maven dependency: quarkus-container-image-docker
* run ./mvnw package -Pnative -D"quarkus.native.container-build"=true -D"quarkus.container-image.build"=true -D"quarkus.container-image.builder"=docker
* **Note:** version and name of the image are determined by quarkus.application.name=customers
  and quarkus.application.version=V1 in application.properties
cd 
# Alternatives:
* it is also possible building and pushing images without docker using the JIB library
  run ./mvnw package -Pnative -D"quarkus.native.container-build"=true -D"quarkus.container-image.build"=true -D"quarkus.container-image.builder"=jib -D"quarkus.container-image.push"=true -DskipTests
* NOTE: even if image is not build using docker, docker is needed to build the native app (wihtout installed graalvm and compiler)

# Generating Kubernates Resources
* install dependency ./mvn quarkus:add-extension -Dextenstions="quarkus-kubernetes" 
* package: mvn package -D"quarkus.container-image.builder"=docker