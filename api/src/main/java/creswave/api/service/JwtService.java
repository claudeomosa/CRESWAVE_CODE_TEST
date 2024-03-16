package creswave.api.service;

import creswave.api.model.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    boolean isValidUser(String token, UserDetails user);
    boolean isTokenExpired(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    String generateToken(User user);
}
