package edu.junnikym.chatservice.member.provider;

import edu.junnikym.chatservice.member.domain.enums.Role;
import edu.junnikym.chatservice.member.service.PrincipalDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final String TOKEN_PREFIX = "Bearer";

	private final String SECRET_KEY = "it's secret";

	private final String COOKIE_KEY = "AccessToken";

	private final long ACCESS_EXP_TIME = 1000L * 60 * 30; 	// 30 min

	private final PrincipalDetailsService principalDetailsService;

	public String createAccessToken(Authentication authentication) {
		Date now = new Date();
		User user = (User) authentication.getPrincipal();

		return TOKEN_PREFIX+" "+Jwts.builder()
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
		final String email = this.getUserInfo(token);
		final UserDetails userDetails = principalDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserInfo(String token) {
		return (String) Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		var test = request.getCookies();
		return Arrays.stream(test)
				.filter(c -> c.getName().equals(COOKIE_KEY))
				.findFirst()
				.map(Cookie::getValue)
				.orElse(null);
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

	public long getAccessExpTime() {
		return ACCESS_EXP_TIME;
	}

	public String getCookieKey() {
		return COOKIE_KEY;
	}

	public String getTokenPrefix() {
		return TOKEN_PREFIX;
	}

}
