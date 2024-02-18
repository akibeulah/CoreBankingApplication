    package com.seaico.corebankingapplication.config;

    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.SignatureAlgorithm;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.stereotype.Service;

    import java.security.Key;
    import java.security.KeyPair;
    import java.security.PrivateKey;
    import java.security.PublicKey;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.Map;
    import java.util.function.Function;

    @Service
    public class JwtService {
        private static final String secretKey = "qvB1sAFsYeF72QYZbo0oxs+e7R7YokHFD66RielE200G7qbxGdssrkdUfb48sHZlyQwOBMQOlM9ePz5XGbGqHGW9bBVn2V7pNzQK4MTuzmpUN7XqxmClFoZttWRL8X5VCyU1CA/1VAwYKv/tWC9TYyaBmKzz/L9p2tau4P6j4LUvjd/CJcId1oKuKHSXwXVOxSmfO/eEdxuBrtXc95FxHnKideiGQaEyHfParA/PKjfzVtOd5+sCgSmT6n9LEIsqLoH5PKf9hiU7pvaifV9ieWob46zrjXrYKeozrUVmpWbIielzbg6T15iQuY+5GEQ8JWFk4rZ9BHqq3TlVgkwWXmitNeb++PSc2g4eUFcNPmw=\n";

        public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
            return Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                    .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                    .compact();
        }

        public String generateToken(UserDetails userDetails) {
            return generateToken(new HashMap<>(), userDetails);
        }

        public boolean isTokenValid(String jwt, UserDetails userDetails) {
            final String username = extractUsername(jwt);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(jwt);
        }

        private boolean isTokenExpired(String jwt) {
            return new Date(System.currentTimeMillis()).after(extractExpiration(jwt));
        }

        private Date extractExpiration(String jwt) {
            return extractClaim(jwt, Claims::getExpiration);
        }

        public String extractUsername(String jwt) {
            return extractClaim(jwt, Claims::getSubject);
        }

        public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(jwt);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String jwt) {
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

        private PrivateKey getPrivateKey() {
            return generateKeyPair().getPrivate();
        }

        private PublicKey getPublicKey() {
            return generateKeyPair().getPublic();
        }

    }