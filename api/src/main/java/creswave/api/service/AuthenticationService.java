package creswave.api.service;

/*
 * Service for authentication
 * This interface is used to define the methods for authentication, such as register, authenticate, and logout.
 * The methods are implemented in the AuthenticationServiceImpl class.
 */

import creswave.api.model.AuthenticationResponse;
import creswave.api.model.User;

public interface AuthenticationService {
    AuthenticationResponse register(User request);
    AuthenticationResponse authenticate(User request);
    void logout(String tokenString);
}
