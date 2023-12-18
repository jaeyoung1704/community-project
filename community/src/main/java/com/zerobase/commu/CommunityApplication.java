package com.zerobase.commu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케쥴러 허용
@SpringBootApplication
public class CommunityApplication {

    public static void main(String[] args) {
	SpringApplication.run(CommunityApplication.class, args);
    }

}
