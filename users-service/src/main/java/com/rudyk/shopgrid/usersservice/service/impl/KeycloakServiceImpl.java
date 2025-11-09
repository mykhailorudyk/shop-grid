package com.rudyk.shopgrid.usersservice.service.impl;

import com.rudyk.shopgrid.common.exception.CannotCreateUserException;
import com.rudyk.shopgrid.usersservice.dto.RegisterUserRequestDto;
import com.rudyk.shopgrid.usersservice.mapper.UserMappper;
import com.rudyk.shopgrid.usersservice.service.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloakAdminClient;

    @Value("${keycloak.admin.realm}")
    private String realm;

    @Override
    public String createKeycloakUser(RegisterUserRequestDto registerRequestDto) {
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEnabled(true);
        UserMappper.mapToKeycloakUser(keycloakUser, registerRequestDto);

        CredentialRepresentation credential = createUserCredentials(registerRequestDto);
        keycloakUser.setCredentials(Collections.singletonList(credential));

        String userId = parseKeycloakUserCreateResponse(registerRequestDto, keycloakUser);
        assignRoleToUser(userId);

        return userId;
    }

    private CredentialRepresentation createUserCredentials(RegisterUserRequestDto registerRequestDto) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequestDto.getPassword());
        return credential;
    }

    private String parseKeycloakUserCreateResponse(RegisterUserRequestDto registerRequestDto,
                                                   UserRepresentation keycloakUser) {
        Response response = keycloakAdminClient.realm(realm).users().create(keycloakUser);
        if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
            throw new CannotCreateUserException(registerRequestDto.getUsername(),
                    response.getStatusInfo().getReasonPhrase());
        }
        return CreatedResponseUtil.getCreatedId(response);
    }

    private void assignRoleToUser(String userId) {
        RoleRepresentation userRole = keycloakAdminClient.realm(realm).roles()
                .get("user").toRepresentation();

        keycloakAdminClient.realm(realm).users().get(userId)
                .roles().realmLevel().add(Collections.singletonList(userRole));
    }

}
