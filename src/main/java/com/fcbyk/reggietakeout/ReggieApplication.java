package com.fcbyk.reggietakeout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication{
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class);
        log.info("http://localhost/backend/index.html");
        log.info("http://localhost/front/page/login.html");
    }

}

