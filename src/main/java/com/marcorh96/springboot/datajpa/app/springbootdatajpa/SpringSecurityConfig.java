package com.marcorh96.springboot.datajpa.app.springbootdatajpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.marcorh96.springboot.datajpa.app.springbootdatajpa.auth.handler.LoginSuccessHandler;
import com.marcorh96.springboot.datajpa.app.springbootdatajpa.models.services.JpaUserDetailsService;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SpringSecurityConfig {

    @Autowired
    private LoginSuccessHandler successHandler;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JpaUserDetailsService userDetailsService;

    /*
     * @Bean
     * public UserDetailsService userDetailsService(AuthenticationManagerBuilder
     * build) throws Exception {
     * 
     * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
     * manager.createUser(
     * User.withUsername("marco").password(passwordEncoder.encode("user_pass")).
     * roles("USER").build());
     * manager.createUser(
     * User.withUsername("admin").password(passwordEncoder.encode("admin_pass")).
     * roles("ADMIN", "USER")
     * .build());
     * 
     * 
     * return manager;
     * }
     */

    @Bean
    public UserDetailsService userDetailsService(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return build.getDefaultUserDetailsService();
    }

    @Bean

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests()
                .requestMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale").permitAll()
                /*
                 * .requestMatchers("/ver/**").hasAnyRole("USER")
                 * .requestMatchers("/uploads/**").hasAnyRole("USER")
                 * .requestMatchers("/form/**").hasAnyRole("ADMIN")
                 * .requestMatchers("/eliminar/**").hasAnyRole("ADMIN")
                 * .requestMatchers("/factura/**").hasAnyRole("ADMIN")
                 */
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successHandler)
                .loginPage("/login")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");

        return http.build();

    }
}
