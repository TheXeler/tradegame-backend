package com.thexeler.whin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WarhammerApplication {
    public static final Logger logger = LoggerFactory.getLogger(WarhammerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WarhammerApplication.class, args);
    }

}
