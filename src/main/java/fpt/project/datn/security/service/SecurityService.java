package fpt.project.datn.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class SecurityService {
    private final String SIGN_IN_KEY = "7A25432A462D4A614E645266556A586E3272357538782F413F4428472B4B6250";
    private final long ACCESS_TOKEN_TIME=15*60*1000;
    private final long REFRESH_TOKEN_TIME=3*60*60*1000;

    public boolean hasToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access-cookie")) {
                return true;
            }
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access-cookie")) {
                return cookie.getValue();
            }
        }
        return "";
    }

    private SecretKey getKey() {
        byte[] key = Base64.getDecoder().decode(SIGN_IN_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimProcessor) {
        return claimProcessor.apply(getClaims(token));
    }

    public String getUsername(HttpServletRequest request) {
        return extractClaims(getToken(request), Claims::getSubject);
    }

    public HashMap<String,Object> ClaimsToHashMap(Claims claims){
        return new HashMap<>(claims);
    }

    public String generateToken(HashMap<String, Object> claims, UserDetails user, long time) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(getKey())
                .compact();
    }

    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user, ACCESS_TOKEN_TIME);
    }

    public String generateRefreshToken(UserDetails user){
        return generateToken(new HashMap<>(), user, REFRESH_TOKEN_TIME);
    }

    public boolean isUserValid(HttpServletRequest request, UserDetails user) {
        return isTokenNonExpired(getToken(request)) || isUserEnable(user);
    }

    public boolean isTokenNonExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public boolean isUserEnable(UserDetails user) {
        return user.isEnabled();
    }
}
