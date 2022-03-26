package com.zayshop;

import com.zayshop.config.StorageConfig;
import com.zayshop.service.FileSystemStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication()
@EnableConfigurationProperties(StorageConfig.class)
public class ZayServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZayServiceApplication.class, args);

    }

    @Bean
    CommandLineRunner init(FileSystemStorageService fileSystemStorageService){
        return (args -> {
            fileSystemStorageService.init();
        });
    }

}
