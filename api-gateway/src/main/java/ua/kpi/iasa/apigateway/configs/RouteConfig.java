package ua.kpi.iasa.apigateway.configs;

import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

  @Value("${route.service.user}")
  private String userService;

  @Value("${route.service.application}")
  private String applicationService;

  @Value("${route.service.invoice}")
  private String invoiceService;

  @Bean
  public RouteLocator routeLocator(RouteLocatorBuilder builder, Function<GatewayFilterSpec, UriSpec> routeCorsFilters) {
    return builder.routes()
        .route(route -> route.path("/api/user/**").filters(routeCorsFilters).uri("lb://" + userService))
        .route(route -> route.path("/api/application/**").filters(routeCorsFilters).uri("lb://" + applicationService))
        .route(route -> route.path("/api/invoice/**").filters(routeCorsFilters).uri("lb://" + invoiceService))
        .build();
  }

  @Bean
  public Function<GatewayFilterSpec, UriSpec> routeCorsFilters() {
    return f -> f
            .setResponseHeader("Access-Control-Allow-Origin", "*")
            .setResponseHeader("Access-Control-Allow-Methods", "*")
            .setResponseHeader("Access-Control-Expose-Headers", "*");
  }

}
