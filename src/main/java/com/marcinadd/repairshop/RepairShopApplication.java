package com.marcinadd.repairshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RepairShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepairShopApplication.class, args);
    }

}
