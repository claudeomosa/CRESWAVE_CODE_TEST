package creswave.api.service;

import creswave.api.model.AuthenticationResponse;
import creswave.api.model.User;

public interface AuthenticationService {
    AuthenticationResponse register(User request);
    AuthenticationResponse authenticate(User request);
    void logout(String tokenString);
}
