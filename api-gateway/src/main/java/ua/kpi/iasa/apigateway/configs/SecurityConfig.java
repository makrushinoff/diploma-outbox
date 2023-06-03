package ua.kpi.iasa.apigateway.configs;

import java.util.List;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  public CorsConfigurationSource corsConfigs() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
    corsConfiguration.setMaxAge(3600L);
    corsConfiguration.setAllowedMethods(List.of(CorsConfiguration.ALL));
    corsConfiguration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.setCorsConfigurations(Map.of("/**", corsConfiguration));
    return urlBasedCorsConfigurationSource;
  }
}
