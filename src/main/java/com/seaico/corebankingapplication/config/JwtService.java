package com.seaico.corebankingapplication.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String secretKey = "qvB1sAFsYeF72QYZbo0oxs+e7R7YokHFD66RielE200G7qbxGdssrkdUfb48sHZlyQwOBMQOlM9ePz5XGbGqHGW9bBVn2V7pNzQK4MTuzmpUN7XqxmClFoZttWRL8X5VCyU1CA/1VAwYKv/tWC9TYyaBmKzz/L9p2tau4P6j4LUvjd/CJcId1oKuKHSXwXVOxSmfO/eEdxuBrtXc95FxHnKideiGQaEyHfParA/PKjfzVtOd5+sCgSmT6n9LEIsqLoH5PKf9hiU7pvaifV9ieWob46zrjXrYKeozrUVmpWbIielzbg6T15iQuY+5GEQ8JWFk4rZ9BHqq3TlVgkwWXmitNeb++PSc2g4eUFcNPmw=\n";

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256)
                .compact();
    }

    public String generateToken(UserDetails userDetails) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final String username = extractUsername(jwt);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new Date(System.currentTimeMillis()).after(extractExpiration(jwt));
    }

    private Date extractExpiration(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public String extractUsername(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts
                .parserBuilder()
                .setSigningKey(getPrivateKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private KeyPair generateKeyPair() {
        return Keys.keyPairFor(SignatureAlgorithm.ES256);
    }

    private PrivateKey getPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes =  Base64.getDecoder().decode(Files.readAllBytes(Paths.get("D:\\Projects\\__suites\\seaico-core-banking\\CoreBankingApplication\\src\\main\\java\\com\\seaico\\corebankingapplication\\secrets\\private.key")));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    private PublicKey getPublicKey() {
        return generateKeyPair().getPublic();
    }
}