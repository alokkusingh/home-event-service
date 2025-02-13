package com.alok.home.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@ConfigurationPropertiesScan({
		"com.alok.home.commons.security.properties"
})
@EnableScheduling
@SpringBootApplication(
		scanBasePackages = {
				"com.alok.home",
				"com.alok.home.event",
				"com.alok.home.commons.exception",
				"com.alok.home.commons.security"
		}
)
public class HomeEventServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeEventServiceApplication.class, args);
	}
}
