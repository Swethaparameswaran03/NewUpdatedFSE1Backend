package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import io.github.kaiso.relmongo.config.EnableRelMongo;

@SpringBootApplication
@EnableRelMongo
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class Demo2Application {
	public static void main(String[] args) {
		SpringApplication.run(Demo2Application.class, args);
	}

}
