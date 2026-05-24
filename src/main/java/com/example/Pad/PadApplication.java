package com.example.Pad;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PadApplication {

	public static void main(String[] args) {
		SpringApplication.run(PadApplication.class, args);

	}
	@Value("${DB_URL}")
	private String dbUrl;

	@PostConstruct
	public void test(){
		System.out.println(dbUrl);
	}

}
