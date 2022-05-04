package coltrolcoin.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import coltrolcoin.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.Date;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtTokenUtil {

	
	@Value("${jwt.expirationMs}")
	public long JWT_TOKEN_VALIDITY;
	
	@Value("${jwt.secret}")
	private String secret;

	
	public String generateToken(User user) {
		return Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject(user.getEmail())
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}
	
	public Claims decodeToken(String token) {
		return Jwts.parser()
			.setSigningKey(this.secret)
			.parseClaimsJws(token)
			.getBody();
	}
	
	public Boolean validateExpirationToken(String token) {
		Claims claims = this.decodeToken(token);
		return !claims.getExpiration().before(new Date(System.currentTimeMillis()));
	}
	
	

}
