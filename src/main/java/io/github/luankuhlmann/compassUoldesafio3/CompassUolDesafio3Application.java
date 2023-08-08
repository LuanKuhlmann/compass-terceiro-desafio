package io.github.luankuhlmann.compassUoldesafio3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.FeignClient;

@SpringBootApplication
@FeignClient
public class CompassUolDesafio3Application {

	public static void main(String[] args) {
		SpringApplication.run(CompassUolDesafio3Application.class, args);
	}

}
