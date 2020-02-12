package com.yufeng.activemqspringbootproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ActivemqSpringbootProducerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ActivemqSpringbootProducerApplication.class, args);
	}

}
