package com.erik.creditscores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.erik.creditscores.interfaces.gateway")
public class CreditScoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditScoresApplication.class, args);
	}

}
