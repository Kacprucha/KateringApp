# KateringApp
Projekt wykonywany w ramach przedmiotu Projektowanie oprogramowania w ramach kursu 7 semestru informatyki stosowanej PW

## Sposób uruchomienia
### Development
Projekt można zbudować z użyciem Docker Compose
#### Backend
```shell
docker compose -f docker-compose-backend-dev.yml up --build
```
#### Frontend
```shell
docker compose -f docker-compose-frontend-dev.yml up --build
```
#### Keycloak
```shell
docker compose -f docker-compose-keycloak-dev.yml up --build
```
#### Wszystkie serwisy
```shell
docker compose -f docker-compose-backend-dev.yml -f docker-compose-frontend-dev.yml -f docker-compose-keycloak-dev.yml up --build
```

###
TETS
