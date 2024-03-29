package com.example.manager_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthEntryPoinJwt authEntryPoinJwt;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling().authenticationEntryPoint(authEntryPoinJwt).and()
//                // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .authorizeRequests()
//                .antMatchers("/api/User/**", "/api/auth/**","/logout").permitAll()
//                .antMatchers("/api/Project/**").hasAnyAuthority("ADMIN");
//
//        http
//                .authorizeRequests(authorizeRequests ->
//                        authorizeRequests
//                                .anyRequest().authenticated() // Cho phép tất cả các yêu cầu không yêu cầu xác thực
//                )
//                .oauth2Login(); // Sử dụng OAuth2 cho việc đăng nhập

        http
                .cors()
                .and()
                .csrf().disable()
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                //.exceptionHandling().authenticationEntryPoint(authEntryPoinJwt).and()
                .authorizeRequests()
                .antMatchers( "/api/auth/google").authenticated()
                .antMatchers( "/api/auth/**","/api/User/**","/api/Project/**","/api/Review/**","/api/Retro/**").permitAll()
              //  .antMatchers("/api/Project/**").hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
               .oauth2Login();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
