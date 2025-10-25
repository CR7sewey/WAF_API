package com.mike.waf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WafApplication {

    public static void main(String[] args) {
        SpringApplication.run(WafApplication.class, args);
    }

}
