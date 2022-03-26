package com.zayshop.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;

@Service
public class EnvService {
    public String getEnvValue(String key){
        Dotenv dotenv = Dotenv.configure()
                .directory("./")
                .ignoreIfMissing()
                .ignoreIfMalformed()
                .load();
        return dotenv.get(key);
    }
}
