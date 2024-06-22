package com.lex.netflixserije;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*")
public class NetflixSerijeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetflixSerijeApplication.class, args);
	}

}