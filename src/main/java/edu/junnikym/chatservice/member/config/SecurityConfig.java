package edu.junnikym.chatservice.member.config;

import edu.junnikym.chatservice.member.filter.AuthenticationFilter;
import edu.junnikym.chatservice.member.filter.JwtAuthorizationFilter;
import edu.junnikym.chatservice.member.provider.JwtTokenProvider;
import edu.junnikym.chatservice.member.service.PrincipalDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(
			HttpSecurity http,
			PasswordEncoder passwordEncoder,
			PrincipalDetailsService principalDetailsService,
			JwtTokenProvider jwtTokenProvider
	) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		http	.csrf().disable()
				.cors()
					.and()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					.and()
				.authenticationManager(authenticationManager)
				.authorizeRequests()
					.antMatchers("/api/**").hasAnyRole("USER", "ADMIN")
					.antMatchers("/join").permitAll()
					.anyRequest().hasAnyRole("USER", "ADMIN")
					.and()
				.addFilter(new AuthenticationFilter(authenticationManager, jwtTokenProvider))
				.addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
				.formLogin()
					.permitAll()
					.and()
				.logout();

		return http.build();
	}

}
