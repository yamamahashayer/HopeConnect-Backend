package com.example.HopeConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing



@SpringBootApplication
public class HopeConnectApplication {

	public static void main(String[] args)
	{
		System.out.println("Hello world!");
		SpringApplication.run(HopeConnectApplication.class, args);
	}

}
