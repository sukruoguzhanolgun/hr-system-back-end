package com.beam.config;

import com.beam.security.JwtTokenFilterConfigurer;
import com.beam.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  public WebSecurityConfig(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()//
        .antMatchers("/api/user/signin").permitAll()//
        .antMatchers("/api/user/signup").permitAll()//
        .antMatchers("/h2-console/**/**").permitAll()
        .anyRequest().authenticated();

    http.exceptionHandling().accessDeniedPage("/api/user/signin");

    http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));


  }

  @Override
  public void configure(WebSecurity web) throws Exception {

    web.ignoring().antMatchers("/v2/api-docs")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/swagger-ui.html")
        .antMatchers("/configuration/**")
        .antMatchers("/webjars/**")
        .antMatchers("/public")
        .and()
        .ignoring()
        .antMatchers("/h2-console/**/**");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
