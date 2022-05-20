# overwrite properties
change profile in run configuration: -Dquarkus.profile=profile_prod

java -jar target/quarkus-app/myjar.jar -Dmyprop=tralala

java -D"at.javatraining.greeting"="Michael was here" -jar target\quarkus-app\quarkus-run.jar

(Anmerkung -D"..." ist notwendig in Power Shell)