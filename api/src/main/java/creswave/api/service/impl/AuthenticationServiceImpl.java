package creswave.api.service.impl;

import creswave.api.model.AuthenticationResponse;
import creswave.api.model.Role;
import creswave.api.model.Token;
import creswave.api.model.User;
import creswave.api.repository.TokenRepository;
import creswave.api.repository.UserRepository;
import creswave.api.service.AuthenticationService;
import creswave.api.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public AuthenticationResponse register(User request) {

        User user = new User();


        if (request.getFullname() == null || request.getFullname().isEmpty()) {
            return new AuthenticationResponse(null, "Fullname is required");
        } else {
            user.setFullname(request.getFullname());
        }
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            String baseUsername = request.getFullname().replaceAll("\\s", "").toLowerCase();
            String username = baseUsername;
            int suffix = 1;
            while(userRepository.findByUsername(username).isPresent()) {
                username = baseUsername + suffix++;
            }
            user.setUsername(username);
        } else {
            String baseUsername = request.getUsername();
            String username = baseUsername;
            int suffix = 1;
            while(userRepository.findByUsername(username).isPresent()) {
                username = baseUsername + suffix++;
            }
            user.setUsername(username);
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return new AuthenticationResponse(null, "Email is required");
        } else {
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                return new AuthenticationResponse(null, "Email already exist");
            }
            user.setEmail(request.getEmail());
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new AuthenticationResponse(null, "Password is required");
        } else {
            if (request.getPassword().length() < 8) {
                return new AuthenticationResponse(null, "Password must be at least 8 characters");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() == null) {
            user.setRole(Role.valueOf("USER"));
        } else {
            user.setRole(request.getRole());
        }

        user = userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful");

    }

    @Override
    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User login was successful");

    }

    @Override
    public void logout(String tokenString) {
        Token token = tokenRepository.findByToken(tokenString).orElseThrow(() -> new IllegalArgumentException("Invalid token"));
        token.setLoggedOut(true);
        tokenRepository.save(token);
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(Math.toIntExact(user.getId()));
        if(validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t-> t.setLoggedOut(true));
        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
}
