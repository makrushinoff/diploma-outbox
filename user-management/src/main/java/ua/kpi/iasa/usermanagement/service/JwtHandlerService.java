package ua.kpi.iasa.usermanagement.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import ua.kpi.iasa.commons.dto.UserPrincipalDto;
import ua.kpi.iasa.commons.service.JwtService;
import ua.kpi.iasa.usermanagement.entity.User;

@Service
@Slf4j
public class JwtHandlerService extends JwtService {

    public JwtHandlerService(ObjectMapper objectMapper, @Value("${auth.secret-key}") String secretKey) {
        super(objectMapper, secretKey);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) throws JsonProcessingException {
        User user = (User) userDetails;
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(objectMapper.writeValueAsString(new UserPrincipalDto(user.getId(), user.getUsername(), user.getRole())))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
