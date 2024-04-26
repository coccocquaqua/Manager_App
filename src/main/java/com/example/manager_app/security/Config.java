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
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true)
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
    public PasswordEncoder passwordEncoder() { //mã hóa mật khâ người dùng
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //xác thực người dùng
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
    //cấu hình quá trình xác thuc ngời dùng ,userdetail-> tìm kiếm thông tin từ csdl và mã hóa pass user
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//cấu hình quy tắc bảo mật cho ứng dụng
        http
                .cors()//cho phép  truy cập từ các domain khácn
                .and()
                .csrf().disable() //tắt tính năng bảo vệ csrf
                .exceptionHandling().authenticationEntryPoint(authEntryPoinJwt).and()
                .authorizeRequests()
                .antMatchers( "/api/auth/google").authenticated()
                .antMatchers( "/api/auth/**","/scope/**","/api/email/**","/api/schedule/**","/api/quartz/**","/language/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/Retro/","/api/Retro/retro/","/api/Retro/end-date/").hasAnyAuthority("USER","PM","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/Review/").hasAnyAuthority("USER","PM","ADMIN")
                .antMatchers(HttpMethod.GET,"/api/Review/getall-user/**","/api/Review/user/**/project/**","/api/Review/user/**").hasAnyAuthority("USER","PM","ADMIN")
                .antMatchers(HttpMethod.GET, "/api/Project/filter-user/**","/api/Retro/project/**").hasAnyAuthority("USER","PM","ADMIN")
                .antMatchers("/api/Account/**").permitAll()
                .antMatchers("/api/User/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/Project/admin/**").hasAnyAuthority("ADMIN","PM")
                .antMatchers("/api/Retro/admin/**").hasAuthority("ADMIN")
                .antMatchers("/api/Review/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/User/admin-pm/getall/**").hasAnyAuthority("ADMIN","PM")
                .anyRequest()
                .authenticated()
                .and()
               .oauth2Login();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //thêm filter để xác thực token của người dùng trước khi xác thực thông tin người dùng
    }
}
