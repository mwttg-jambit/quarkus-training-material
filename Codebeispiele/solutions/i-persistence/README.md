# start database
... using the docker-compose file in folder docker

# changes
* see maven dependencies panache-hibernate and mariadb maven dependencies
* see intferface for repositories, see JPA repo and add @DefaultBean and @IfProfilesActive to the repos
* add command line Paramater -Dquarkus.profile=jpa to switch to jpa repo
* see class CustomerPanacheRepositoryTest and application.properties in test folder, which demonstrates how to use H2 during tests and how to rollback transactions in DB tests.