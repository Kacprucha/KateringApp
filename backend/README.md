# KateringApp Backend

## Profile

**Dev**: Profil do uruchamiania aplikacji lokalnie
 - Wykorzystuje bazę danych H2
 - Połączenie z Keycloak przez localhost (wymagany działający kontener w tle)
 - Dostęp do SwaggerUI

**Test**: Profil do uruchamiania aplikacji przez Docker Compose
 - Baza danych PostgreSQL
 - Dostęp do SwaggerUI
 - Komunikacja między usługami w sieci kontenerowej

**Prod**: Profil do wdrażania
 - Konfiguracja poprzez zmienne środowiskowe w plikach properties
 - Swagger zablokowany

## Swagger
**Adres endpointu SwaggerUI:** http://localhost:8080/swagger-ui/index.html

## Testy
Do uruchomienia testów jednostkowych:
```shell
mvn test
```
Do uruchomienia testów jednostkowych oraz integracyjnych:
```shell
mvn verify
```