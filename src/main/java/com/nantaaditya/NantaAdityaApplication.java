package com.nantaaditya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NantaAdityaApplication {

  public static void main(String[] args) {
    SpringApplication.run(NantaAdityaApplication.class, args);
  }
}
