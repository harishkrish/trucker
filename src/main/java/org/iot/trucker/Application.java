package org.iot.trucker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Trucker, a Spring boot application 
 * @author Harish Krishnamurthi
 *
 */
@EnableAsync
@SpringBootApplication
public class Application 
{
	public static void main(String args[]){
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.run();
	}
}
