FROM maven:3.9.4-eclipse-temurin-21 AS builder

COPY keycloak-utils /opt/keycloak-utils

WORKDIR /opt/keycloak-utils
RUN mvn clean package -DskipTests

FROM quay.io/keycloak/keycloak:26.0.2

COPY --from=builder /opt/keycloak-utils/target/keycloak-utils.jar /opt/keycloak/providers/keycloak-utils.jar

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--http-port=8081", "--import-realm"]