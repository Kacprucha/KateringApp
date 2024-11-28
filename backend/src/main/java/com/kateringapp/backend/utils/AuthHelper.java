package com.kateringapp.backend.utils;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;

public class AuthHelper {

    public static boolean isClient(Jwt jwt) {

        Map<String, Object> claims = jwt.getClaims();
        List<String> roles  = (List<String>) ((Map<String, Object>) claims.get("realm_access")).get("roles");
        return roles.contains("client");
    }

    public static boolean isCateringFirm(Jwt jwt) {

        Map<String, Object> claims = jwt.getClaims();
        List<String> roles  = (List<String>) ((Map<String, Object>) claims.get("realm_access")).get("roles");
        return roles.contains("catering-firm");
    }
}
