package com.ogbuilds.url_shortener_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class UrlShortenerAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerAppApplication.class, args);
	}

}
