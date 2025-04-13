package shared;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class BaseContainerTest {
    @Container
    private final static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    public static void configure(DynamicPropertyRegistry registry) {
        System.out.println(container.getJdbcUrl());
        registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
        registry.add("spring.liquibase.driver-class-name", container::getDriverClassName);
        registry.add("spring.liquibase.url", container::getJdbcUrl);
        registry.add("spring.liquibase.user", container::getUsername);
        registry.add("spring.liquibase.password", container::getPassword);
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.liquibase.change-log", () -> "db/changelog/master-changelog.xml");
        registry.add("spring.liquibase.enabled", () -> true);
        registry.add("application.create-users-on-bootstrap", () -> false);
        registry.add("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation", () -> true);
    }
}
