#create project
mvn io.quarkus.platform:quarkus-maven-plugin:2.2.3.Final:create -DprojectGroupId=at.javatraining -DprojectArtifactId=hello-quarkus -DclassName="at.javatraining.quarkus.HelloQuarkus" -Dpath="/hello"

#compile and run the application
./mvnw compile quarkus:dev
