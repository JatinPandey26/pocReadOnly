package com.jatinpandey.readonlypoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy

public class ReadOnlyPocApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadOnlyPocApplication.class, args);
    }

}
