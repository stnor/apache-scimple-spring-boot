package com.selessia.scim.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * JWT authentication
 *
 * Test:
 * curl -H 'Accept: application/json' -H "Authorization: Bearer BhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZW1vMSIsImlzcyI6Im5vbXAuc2UiLCJleHAiOjE1NDQ3MDYyNTksImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiIsIlJPTEVfU1RVREVOVCIsIlVTRV9QUkVNSVVNX1RUUyJdfQ.uFCth8zEp3YB78LfuePEEHtUn6FD-1PeQw5w5KjzsFs" http://127.0.0.1:9090/api/organisations
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

}
