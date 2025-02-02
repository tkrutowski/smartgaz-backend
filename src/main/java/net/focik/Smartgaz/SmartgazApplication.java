package net.focik.Smartgaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SmartgazApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartgazApplication.class, args);
	}

}
