package com.example.securityapp;


import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SampleSecurityConfig {

  @Autowired
  private DataSource dataSource;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http)
      throws Exception {
    http.csrf().disable();
    http.authorizeHttpRequests(authorize -> {
      authorize.anyRequest().permitAll();
    });
    http.formLogin(form -> {
      form.defaultSuccessUrl("/secret");
    });
    return http.build();
  }

  @Bean
  public UserDetailsManager userDetailsManager(){
    JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
//    users.createUser(makeUser("taro","yamada", "USER"));  //☆
//    users.createUser(makeUser("hanako","flower", "USER"));  //☆
//    users.createUser(makeUser("sachiko","happy", "USER"));  //☆

//    users.createUser(makeUser("admin","kanri", "ADMIN"));  //☆

    return users;
  }

  public UserDetails makeUser(String user, String pass, String role) {
    return User
        .withUsername(user)
        .password(
            PasswordEncoderFactories
                .createDelegatingPasswordEncoder().encode(pass)
        )
        .roles(role)
        .build();
  }
}
