package com.carlisle.brown.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.carlisle.brown.mall.dao")
public class BrownMallApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BrownMallApplication.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
