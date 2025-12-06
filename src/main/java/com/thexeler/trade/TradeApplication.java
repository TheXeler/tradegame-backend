package com.thexeler.trade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TradeApplication {
    public static final Logger logger = LoggerFactory.getLogger(TradeApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TradeApplication.class, args);
    }

}
