package pingwit.beautysaloon.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private static final String CLIENTS_ROLE = "CLIENT";
    private static final String ADMIN_ROLE = "ADMIN";

    private static final String CLIENTS_ENDPOINT = "/clients/**";
    private static final String MASTERS_ENDPOINT = "/masters/**";
    private static final String BEAUTY_PROCEDURES_ENDPOINT = "/procedures/**";
    private static final String REGISTRATIONS_ENDPOINT = "/services/**";

    @Bean
    public UserDetailsService users() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails client = users
                .username("client")
                .password("vip")
                .roles(CLIENTS_ROLE)
                .build();
        UserDetails admin = users
                .username("admin")
                .password("superman")
                .roles(ADMIN_ROLE, CLIENTS_ROLE)
                .build();
        return new InMemoryUserDetailsManager(client, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, CLIENTS_ENDPOINT).hasRole(CLIENTS_ROLE)
                        .requestMatchers(HttpMethod.GET, REGISTRATIONS_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.POST, REGISTRATIONS_ENDPOINT, MASTERS_ENDPOINT, BEAUTY_PROCEDURES_ENDPOINT, CLIENTS_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.PUT, REGISTRATIONS_ENDPOINT, MASTERS_ENDPOINT, BEAUTY_PROCEDURES_ENDPOINT, CLIENTS_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.DELETE, REGISTRATIONS_ENDPOINT, MASTERS_ENDPOINT, BEAUTY_PROCEDURES_ENDPOINT, CLIENTS_ENDPOINT).hasRole(ADMIN_ROLE)
                        .requestMatchers(HttpMethod.GET, MASTERS_ENDPOINT, BEAUTY_PROCEDURES_ENDPOINT).permitAll()
                        .requestMatchers("/price").permitAll()
                        .requestMatchers("/currency").permitAll()
                        .anyRequest().authenticated())
                .build();
    }
}
