### Description
This project is a listener for keycloak that assigns roles to users based on the isCateringFirm attribute that was set in registration form.

### How to use this project
To apply changes run:
```shell
mvn install
```
then the .jar file will be generated inside the jar directory. The jar directory is defined as a volume for keycloak inside the docker compose file.