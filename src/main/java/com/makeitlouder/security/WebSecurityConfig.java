package com.makeitlouder.security;

import com.makeitlouder.repositories.UserRepository;
import com.makeitlouder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final CustomAuthenticationEntryPoint unauthorizedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .addFilter(getJwtAuthenticationFilter())
            .addFilterAfter(new JwtAuthorizationFilter(authenticationManager(), userRepository), JwtUsernameAndPasswordAuthenticationFilter.class)
            .authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.VERFICATION_EMAIL_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL).permitAll()
                .antMatchers(HttpMethod.GET, SecurityConstants.PET_LIST).permitAll()
                .antMatchers(SecurityConstants.H2_CONSOLE).permitAll()
            .anyRequest()
                .authenticated()
            .and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler);


        http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());

    }

    public JwtUsernameAndPasswordAuthenticationFilter getJwtAuthenticationFilter() throws Exception {
        final JwtUsernameAndPasswordAuthenticationFilter filter = new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/users/login");
        return filter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

// PG 42