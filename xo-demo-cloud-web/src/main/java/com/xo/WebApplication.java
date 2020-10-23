package com.xo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: xo
 * @NAME: Application
 * @DATE: 2020/8/7
 * @Description: Application
 **/
@SpringBootApplication(scanBasePackages = "com.xo")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }



}