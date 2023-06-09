package za.ac.bheki97.testsecspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import za.ac.bheki97.testsecspring.filter.JwtAuthFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;


    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.withUsername("Bheki")
//                .password(passwordEncoder.encode("123"))
//                .roles("admin")
//                .build();
//        UserDetails user = User.withUsername("France")
//                .password(passwordEncoder.encode("123"))
//                .roles("user")
//                .build();
//
//        UserDetails speaker = User.withUsername("France")
//                .password(passwordEncoder.encode("123"))
//                .roles("speaker")
//                .build();

        return new UserEntityUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeHttpRequests().requestMatchers("/account","/account/{id}","/authenticate","/speech/**","/event/**","/event/{id}"
                        ,"/event/leave/{eventKey}/{guestId}","/event/host/{id}","/event/{eventKey}/speaker","/event/{guestId}"
                        ,"/event/{guestId}/{language}","/event/{eventKey}/speaker/{language}").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/account/**").authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();


    }
}