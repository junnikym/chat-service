package edu.junnikym.chatservice.member.filter;

import edu.junnikym.chatservice.member.provider.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	public JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws ServletException, IOException {

		final String tokenPrefix = jwtTokenProvider.getTokenPrefix();

		final String tokenOnCookie = URLDecoder.decode(
				jwtTokenProvider.resolveToken(request), StandardCharsets.UTF_8);

		if(tokenOnCookie == null || !tokenOnCookie.startsWith(tokenPrefix)) {
			filterChain.doFilter(request, response);
			return;
		}

		String token = tokenOnCookie.substring((tokenPrefix+" ").length());
		Authentication authorization = jwtTokenProvider.getAuthentication(token);

		try {
			SecurityContextHolder.getContext().setAuthentication(authorization);
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		}
	}

}
