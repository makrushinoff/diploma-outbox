package ua.kpi.iasa.applicationservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.kpi.iasa.commons.dto.UserPrincipalDto;
import ua.kpi.iasa.commons.service.JwtService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isBlank(authorizationHeaderValue)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = authorizationHeaderValue.substring(7);
            UserPrincipalDto username = jwtService.extractUser(token);
            jwtService.checkTokenExpired(token);

            if(StringUtils.isBlank(username.getEmail())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            UserPrincipalDto user = jwtService.extractUser(token);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                authorizationHeaderValue,
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
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
