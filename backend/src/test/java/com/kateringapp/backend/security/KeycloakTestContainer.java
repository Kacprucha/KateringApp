package com.kateringapp.backend.security;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class KeycloakTestContainer {

    public static KeycloakContainer keycloak;

    static {
        keycloak = new KeycloakContainer().withRealmImportFile("realm.json");
        keycloak.start();
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl() + "/realms/kateringapp");
    }

    public static String getUserToken(String username, String password) throws URISyntaxException {
        URI authorizationURI = new URIBuilder(keycloak.getAuthServerUrl() + "/realms/kateringapp/protocol/openid-connect/token").build();
        WebClient webclient = WebClient.create();
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("grant_type", Collections.singletonList("password"));
        formData.put("client_id", Collections.singletonList("KateringAppClientBackend"));
        formData.put("client_secret", Collections.singletonList("58bR3tAcROdKWOM4I1NHbxXAhClCwo67"));
        formData.put("username", Collections.singletonList(username));
        formData.put("password", Collections.singletonList(password));

        String result = webclient.post()
                .uri(authorizationURI)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return "Bearer " + jsonParser.parseMap(result)
                .get("access_token")
                .toString();
    }
}