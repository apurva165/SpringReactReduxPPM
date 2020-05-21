package com.akulkarni.ppmtool.security;

import com.akulkarni.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.akulkarni.ppmtool.security.SecurityConstants.EXPIRATION_TIME;
import static com.akulkarni.ppmtool.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {

    // generate token
    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date now =  new Date(System.currentTimeMillis());

        Date expDate = new Date(now.getTime()+ EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String , Object> claims = new HashMap<>();
        claims.put("id", Long.toString(user.getId()));
        claims.put("fullname", user.getFullname());
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    // validate token
    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Ex");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            System.out.println("JWT Expired");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    // get user from token

    public Long getUserIdFromToken(String token){
     Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
     String id =(String) claims.get("id");

     return Long.parseLong(id);
    }
}
