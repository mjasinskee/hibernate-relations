package com.example.springtraining.springhibernate.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MyConfiguration {

    @Bean MyRepository repository() {
        return new MyRepository();
    }

    @Bean MyService service(MyRepository repository) {
        return new MyService(repository);
    }

}
