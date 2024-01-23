package dvm.springbootweb.jwt;

import dvm.springbootweb.security.CustomUserDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${dvm.secret}")
    private String jwtSecret;
    @Value("${dvm.expiration}")
    private int jwtExpiration;

    public String generateToken(CustomUserDetail customUserDetail){
        Date date =new Date();
        Date expiration = new Date(date.getTime()+jwtExpiration);
        return Jwts.builder()
            .setSubject(customUserDetail.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS512,jwtSecret).compact();

    }

    public String getUserNameFromJwt(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        }catch (ExpiredJwtException e){
            return null;
        }

    }
    public boolean validateJwtToken(String authToken){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
