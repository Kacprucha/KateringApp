package com.kateringapp.backend.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class KeycloakIT extends KeycloakTestContainer {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void before() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }

    @Test
    public void userShouldAccessUnprotectedEndpointWithoutToken() throws Exception {

        mvc.perform(
                get("/helloWorld")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void userWithClientRoleShouldAccessSecuredEndpoint() throws Exception {

        String clientToken = getUserToken("janedoe@gmail.com", "password");

        mvc.perform(
                get("/secured/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", clientToken)
                ).andExpect(status().isOk());
    }

    @Test
    public void userWithCateringFirmRoleShouldAccessSecuredEndpoint() throws Exception {

        String clientToken = getUserToken("firm1@gmail.com", "password");

        mvc.perform(
                get("/secured/catering-firm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", clientToken)
        ).andExpect(status().isOk());
    }

    @Test
    public void userWithoutClientRoleShouldAccessSecuredEndpoint() throws Exception {

        String clientToken = getUserToken("janedoe@gmail.com", "password");

        mvc.perform(
                get("/secured/catering-firm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", clientToken)
        ).andExpect(status().isForbidden());
    }

    @Test
    public void userWithoutCateringFirmRoleShouldAccessSecuredEndpoint() throws Exception {

        String clientToken = getUserToken("firm1@gmail.com", "password");

        mvc.perform(
                get("/secured/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", clientToken)
        ).andExpect(status().isForbidden());
    }
}
