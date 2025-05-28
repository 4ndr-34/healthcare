
package com.example.healthcare.security;

import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.StaffRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigTwo {

    private final PatientRepository patientRepository;
    private final StaffRepository staffRepository;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .requestCache(RequestCacheConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/patient/login", "/staff/login", "/staff/login/**", "/patient/login-register",
                                "/patient/register", "/patient/successful-registration","/static/**",
                                "/css/**",
                                "/js/**").permitAll()
                        .requestMatchers("/patient/**").hasRole("PATIENT")
                        .requestMatchers("/staff/**").hasAnyRole("STAFF", "ADMIN", "DOCTOR")
                        .anyRequest().authenticated())
                .addFilterBefore(multiLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .sessionFixation().migrateSession()
                        .maximumSessions(1).maxSessionsPreventsLogin(false))
                .securityContext(context -> context
                        .securityContextRepository(securityContextRepository()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                            if (authentication != null && authentication.getAuthorities().stream()
                                    .anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"))) {
                                response.sendRedirect("/patient/login-register");
                            } else {
                                response.sendRedirect("/staff/login");
                            }
                        }));

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(patientRepository, staffRepository);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Replace NoOpPasswordEncoder
    }

    @Bean
    public MultiLoginFilter multiLoginFilter() throws Exception {
        MultiLoginFilter filter = new MultiLoginFilter(
                new AntPathRequestMatcher("/patient/login", "POST"),
                new AntPathRequestMatcher("/staff/login", "POST"));
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authentication);

                HttpSession session = request.getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

                System.out.println("=== AUTHENTICATION SUCCESS ===");
                System.out.println("Principal class: " + authentication.getPrincipal().getClass());
                System.out.println("Authorities: " + authentication.getAuthorities());
                System.out.println("SessionID after login: " + session.getId());
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                if (userDetails.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_PATIENT"))) {
                    response.sendRedirect("/patient/home");
                } else {
                    response.sendRedirect("/staff/home");
                }
            }
        });
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.sendRedirect("/login?error=true");
        });
        return filter;
    }
}