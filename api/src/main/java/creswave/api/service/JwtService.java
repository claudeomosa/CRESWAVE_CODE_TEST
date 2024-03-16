package creswave.api.service;
/*
 * Service for JWT
 * This interface is used to define the methods for JWT, such as extractUsername, isValidUser, isTokenExpired, extractClaim, and generateToken.
 * The methods are implemented in the JwtServiceImpl class.
 */
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
    UserDetails getLoggedInUser();
}
