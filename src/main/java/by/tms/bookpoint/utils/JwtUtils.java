package by.tms.bookpoint.utils;

import by.tms.bookpoint.entity.Account;
import by.tms.bookpoint.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {

    private String SECRET_KEY = "secretKey";

//    public Date extractExpiration (String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }

//    private Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

//    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String generateToken(Account account) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", account.getId());
        claims.put("roles", account.getAuthorities());
        claims.put("name", account.getName());
        return createToken(claims, account.getUsername());
    }

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return createToken(claims, userDetails.getUsername()); // тлько userName в токине
//    }

    public Boolean validateToken(String token) {
        var claims = extractAllClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    public Account getPrincipal(String token) {
        Claims claims = extractAllClaims(token);
        var roles = (List<String>) claims.get("roles");
        var id = (Integer) claims.get("id");
        var name = (String) claims.get("name");
        var username = claims.getSubject();
        var account = new Account();
        account.setId(Long.valueOf(id));
        account.setUsername(username);
        account.setName(name);

        Set<Role> roleSet = new HashSet<>();

        roles.forEach(role -> roleSet.add(Role.valueOf(role)));

        account.setAuthorities(new HashSet<>(roleSet));
        return account;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
