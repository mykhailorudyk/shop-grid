package com.rudyk.shopgrid.common.security.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Object realAccessProp = jwt.getClaims().get("realm_access");
        if (realAccessProp instanceof Map) {
            Map<String, Object> realmAccess = (Map<String, Object>) realAccessProp;
            if (realmAccess.isEmpty()) {
                return Collections.emptyList();
            }
            Object rolesProp = realmAccess.get("roles");
            if (rolesProp instanceof Collection) {
                Collection<String> roles = (Collection<String>) rolesProp;
                if (roles.isEmpty()) {
                    return Collections.emptyList();
                }
                return roles.stream()
                        .map(roleName -> "ROLE_" + roleName.toUpperCase())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

}
