package by.tms.bookpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(SecurityRulesProperties.class)
public class BookPointApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookPointApplication.class, args);
    }

}
