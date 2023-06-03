package ua.kpi.iasa.applicationservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.kpi.iasa.commons.service.JwtService;

@Configuration
public class JwtConfig {

  @Bean
  public JwtService jwtService(@Value("${auth.secret-key}") String secretKey, ObjectMapper objectMapper) {
    return new JwtService(objectMapper, secretKey);
  }

}
