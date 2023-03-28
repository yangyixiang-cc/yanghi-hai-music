package com.yanghi.haimusic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yanghi.haimusic.mapper")
public class HaiMusicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HaiMusicServerApplication.class, args);
    }

}
