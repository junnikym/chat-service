package edu.junnikym.chatservice.member.provider;

import edu.junnikym.chatservice.member.domain.enums.Role;
import edu.junnikym.chatservice.member.dto.LoginDto;
import edu.junnikym.chatservice.member.service.PrincipalDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final String SECRET_KEY = "it's secret";

	private final long ACCESS_EXP_TIME = 1000L * 60 * 30; 	// 30 min

	private final PrincipalDetailsService principalDetailsService;

	public String createAccessToken(Authentication authentication) {
		Date now = new Date();
		User user = (User) authentication.getPrincipal();

		return Jwts.builder()
				.setHeaderParam("typ", "ACCESS_TOKEN")
				.setHeaderParam("alg", "HS256")
				.setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + ACCESS_EXP_TIME))
				.claim("role", Role.USER.toString())
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
				.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getUserInfo(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserInfo(String token) {
		return (String) Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.get("email");
	}

	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("token");
	}

	public boolean validateJwtToken(ServletRequest request, String authToken) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
			return true;
		} catch (MalformedJwtException e) {
			request.setAttribute("exception", "MalformedJwtException");
		} catch (ExpiredJwtException e) {
			request.setAttribute("exception", "ExpiredJwtException");
		} catch (UnsupportedJwtException e) {
			request.setAttribute("exception", "UnsupportedJwtException");
		} catch (IllegalArgumentException e) {
			request.setAttribute("exception", "IllegalArgumentException");
		}
		return false;
	}

}
