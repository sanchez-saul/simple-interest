package com.aplazo.credit;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SimpleCreditApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SimpleCreditApplication.class)
				.profiles("h2")
				.run(args);
	}

}
