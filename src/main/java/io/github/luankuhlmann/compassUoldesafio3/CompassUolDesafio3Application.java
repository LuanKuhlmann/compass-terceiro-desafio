package io.github.luankuhlmann.compassUoldesafio3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CompassUolDesafio3Application {

	public static void main(String[] args) {
		SpringApplication.run(CompassUolDesafio3Application.class, args);
	}

}
