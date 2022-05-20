see also https://quarkus.io/guides/security-jwt
# maven changes
* run mvn quarkus:add-extension -Dextensions="quarkus-smallrye-jwt,quarkus-smallrye-jwt-build"

# generate ssl keys under linux
openssl genrsa -out rsaPrivateKey.pem 2048

openssl rsa -pubout -in rsaPrivateKey.pem -out publicKey.pem

openssl pkcs8 -topk8 -nocrypt -inform pem -in rsaPrivateKey.pem -outform pem -out privateKey.pem

# changes in application.properties
see application.properties

# generate token
Run the GenerateToken Unit Test

