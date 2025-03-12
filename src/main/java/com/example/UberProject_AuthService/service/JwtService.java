package com.example.UberProject_AuthService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.security.Key;

@Service
@PropertySource("classpath:application.properties")
public class JwtService implements CommandLineRunner {

    // JWT expiration time (in seconds) loaded from application.properties
    @Value("${jwt.expiry}")
    private int expiry;

    // Secret key for signing JWT tokens loaded from application.properties
    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * Creates a new JWT token with the given payload and email.
     *
     * @param payload Additional claims (key-value pairs) to include in the token
     * @param email   The subject of the token (typically the user's email)
     * @return        A signed JWT token as a String
     */
    public String createToken(Map<String, Object> payload, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry * 1000L); // Expiration time in milliseconds

        // Generate the signing key
        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

        // Build and return the JWT token
        return Jwts.builder()
                .claims(payload)  // Add claims (payload)
                .issuedAt(now)    // Set issued time
                .expiration(expiryDate) // Set expiration time
                .subject(email)  // Set subject (email)
                .signWith(key)   // Sign with the secret key
                .compact();      // Generate the final token
    }

    /**
     * Extracts all claims (payload data) from a JWT token.
     *
     * @param token JWT token
     * @return      Claims object containing all data from the token
     */
    public Claims extractAllPayloads(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignKey()) // Use the secret key for verification
                .build()
                .parseClaimsJws(token) // Parse and validate the token
                .getBody(); // Extract the payload (claims)
    }

    /**
     * Extracts a specific claim from the JWT token using a function.
     *
     * @param token          JWT token
     * @param claimsResolver Function to extract the required claim
     * @param <T>            Generic type of the extracted claim
     * @return               The extracted claim value
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the expiration date from the JWT token.
     *
     * @param token JWT token
     * @return      Expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts the email (subject) from the JWT token.
     *
     * @param token JWT token
     * @return      The email associated with the token
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if the JWT token has expired.
     *
     * @param token JWT token
     * @return      true if expired, false otherwise
     */
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Check if expiration date is before current time
    }

    /**
     * Generates and returns the signing key used for JWT signing and verification.
     *
     * @return SecretKey for signing JWT tokens
     */
    public Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Validates a JWT token by checking:
     * 1. If the email in the token matches the provided email
     * 2. If the token has not expired
     *
     * @param token JWT token
     * @param email Expected email (subject) in the token
     * @return      true if valid, false otherwise
     */
    public Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    /**
     * Extracts a specific key-value pair (claim) from the JWT token payload.
     *
     * @param token      JWT token
     * @param payloadKey Key to fetch from the payload
     * @return           The value associated with the given key
     */
    public Object extractPayload(String token, String payloadKey) {
        Claims claim = extractAllPayloads(token);
        return claim.get(payloadKey);
    }

    /**
     * Runs a test scenario when the application starts.
     * Generates a sample JWT token with test data and prints it.
     */
    @Override
    public void run(String... args) throws Exception {
        // Sample user details for testing
        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "a@b.com");
        mp.put("phoneNumber", "9999999999");

        // Generate a JWT token for testing
        String result = createToken(mp, "sanket");

        // Print the generated token
        System.out.println("Generated token is: " + result);
    }
}
