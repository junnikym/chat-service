package edu.junnikym.chatservice.member.config;

import edu.junnikym.chatservice.member.service.PrincipalDetailsService;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

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
			PrincipalDetailsService principalDetailsService
	) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder);

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		http.csrf().disable()
				.authenticationManager(authenticationManager)
				.authorizeRequests()
					.antMatchers("/api/*/user/**").hasAnyRole("USER", "ADMIN")
					.anyRequest().permitAll()
					.and()
				.formLogin()
					.permitAll()
					.and()
				.logout();

		return http.build();
	}

}
