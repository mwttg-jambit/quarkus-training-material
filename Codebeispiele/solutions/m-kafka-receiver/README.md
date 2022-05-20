# config kafka docker-compose.yaml

when you let kafka run in Docker Desktop configure:
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092

else configure
KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://10.16.1.48:9092
where 10.16.1.48 is the host where the docker container is running on