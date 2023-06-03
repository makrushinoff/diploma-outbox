package ua.kpi.iasa.usermanagement.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.kpi.iasa.commons.dto.UserPrincipalDto;
import ua.kpi.iasa.usermanagement.entity.User;
import ua.kpi.iasa.usermanagement.service.JwtHandlerService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtHandlerService jwtHandlerService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authorizationHeaderValue)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeaderValue.substring(7);
            UserPrincipalDto user = jwtHandlerService.extractUser(token);
            jwtHandlerService.checkTokenExpired(token);

            if(StringUtils.isBlank(user.getEmail())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            User userDetails = (User) userDetailsService.loadUserByUsername(user.getEmail());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new UserPrincipalDto(user.getId(), userDetails.getUsername(), userDetails.getRole()),
                token,
                userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
