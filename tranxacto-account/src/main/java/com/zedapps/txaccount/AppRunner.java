package com.zedapps.txaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */

@SpringBootApplication
@EnableFeignClients
public class AppRunner {

    public static void main(String[] args) {
        SpringApplication.run(AppRunner.class, args);
    }
}
