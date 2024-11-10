package com.kateringapp.backend.configurations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  public static final String PREFIX_REALM_ROLE = "ROLE_";
  private static final String CLAIM_REALM_ACCESS = "realm_access";
  private static final String CLAIM_ROLES = "roles";

  @Override
  public List<GrantedAuthority> convert(Jwt jwt) {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);

    if (realmAccess != null && !realmAccess.isEmpty()) {
      Collection<String> roles = realmAccess.get(CLAIM_ROLES);
      if (roles != null && !roles.isEmpty()) {
        Collection<GrantedAuthority> realmRoles = roles.stream()
            .map(role -> new SimpleGrantedAuthority(PREFIX_REALM_ROLE + role))
            .collect(Collectors.toList());
        grantedAuthorities.addAll(realmRoles);
      }
    }

    return grantedAuthorities;
  }
}

