package io.github.pavello.messaging.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJms
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
