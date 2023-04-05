package com.example.sseemitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SseEmitter {

	public static void main(String[] args) {
		SpringApplication.run(SseEmitter.class, args);
	}

}
