package com.ftn.tseo2021.sf1513282018.studentService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

	@Value("myXAuthSecret")
	private String secret;
	
	@Value("18000") //in seconds (5 hours)
	private Long expiration;
	
	public String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = this.getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(this.secret)
					.parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	
	private boolean isTokenExpired(String token) {
	    final Date expiration = this.getExpirationDateFromToken(token);
	    return expiration.before(new Date(System.currentTimeMillis()));
	  }
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername())
				&& !isTokenExpired(token);
	}
	
	public String generateToken(UserDetails userDetails) {
		CustomPrincipal principal = (CustomPrincipal) userDetails;
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("sub", principal.getUsername());
		claims.put("id", principal.getId());
		claims.put("username", principal.getUsername());
		claims.put("firstName", principal.getFirstName());
		claims.put("lastName", principal.getLastName());
		claims.put("institutionId", principal.getInstitutionId());
		
		List<String> authorities = new ArrayList<>();
		if (principal.isSuperadmin()) authorities.add("SUPERADMIN");
		if (principal.isAdmin()) authorities.add("ADMIN");
		if (principal.isTeacher()) {
			authorities.add("TEACHER");
			claims.put("teacherId", principal.getTeacherId());
		}
		if (principal.isSuperadmin()) {
			authorities.add("STUDENT");
			claims.put("studentId", principal.getStudentId());
		}
		claims.put("authorities", authorities);
		
		claims.put("created", new Date(System.currentTimeMillis()));
		return Jwts.builder().setClaims(claims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}


}
