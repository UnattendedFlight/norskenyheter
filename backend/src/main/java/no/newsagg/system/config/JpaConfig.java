package no.newsagg.system.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {
  @Bean
  public org.hibernate.boot.model.naming.PhysicalNamingStrategy physicalNamingStrategy() {
    return new CamelCaseToUnderscoresNamingStrategy() {
      @Override
      public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        // Handle the vector type specifically
        if (name.getText().equals("embedding")) {
          return name;
        }
        return super.toPhysicalColumnName(name, context);
      }
    };
  }
}