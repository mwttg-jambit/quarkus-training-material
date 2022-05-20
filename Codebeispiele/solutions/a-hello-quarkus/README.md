#create project
mvn io.quarkus.platform:quarkus-maven-plugin:2.2.3.Final:create -DprojectGroupId=at.javatraining -DprojectArtifactId=hello-quarkus -DclassName="at.javatraining.quarkus.HelloQuarcd kus" -Dpath="/hello"

# create project via quarkus io:
goto https://code.quarkus.io/

#compile and run the application for development
./mvnw compile quarkus:dev

#list and add  extensions
mvn quarkus:list-extensions

./mvnw quarkus:add-extension -Dextensions="hibernate-validator"


#build and run jar file
./mvnw package -DskipTests

then run in directory /target/quarkus-app
