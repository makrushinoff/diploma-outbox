package ua.kpi.iasa.notificationservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.cloud.aws.mail.simplemail.SimpleEmailServiceJavaMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

  @Bean
  public AmazonSimpleEmailService amazonSimpleEmailService(Environment env) {
    boolean localEnv = env.acceptsProfiles(Profiles.of("default"));
    if(!localEnv) {
      return AmazonSimpleEmailServiceClientBuilder.standard()
          .withRegion(Regions.EU_WEST_1)
          .build();
    }
    BasicAWSCredentials credentials = new BasicAWSCredentials("localstack", "local");
    AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
    AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
        "http://127.0.0.1:4566",
        Regions.EU_WEST_1.getName()
    );
    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(credentialsProvider)
        .withEndpointConfiguration(endpoint)
        .build();
  }

  @Bean
  public JavaMailSender javaMailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
    return new SimpleEmailServiceJavaMailSender(amazonSimpleEmailService);
  }

}
