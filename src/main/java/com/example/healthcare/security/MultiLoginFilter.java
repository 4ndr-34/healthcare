package com.example.healthcare.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Arrays;
import java.util.List;

public class MultiLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final List<RequestMatcher> loginRequestMatchers;

    public MultiLoginFilter(RequestMatcher... matchers) {
        this.loginRequestMatchers = Arrays.asList(matchers);
        super.setRequiresAuthenticationRequestMatcher(
                request -> loginRequestMatchers.stream().anyMatch(m -> m.matches(request))
        );
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {
        if (loginRequestMatchers.stream().anyMatch(m -> m.matches(request))) {
            return super.attemptAuthentication(request, response);
        }
        throw new AuthenticationServiceException("Authentication Method not supported");
    }


/*    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        System.out.println("User roles: " + authResult.getAuthorities());
        // Your existing success handler logic
        if (authResult.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_PATIENT"))) {
            response.sendRedirect("/patient/home");
        } else {
            response.sendRedirect("/staff/home");
        }
    }*/
}
