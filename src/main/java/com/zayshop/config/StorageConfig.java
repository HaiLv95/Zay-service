package com.zayshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

//mac dinh @ConfigurationProperties(prefix = "storage") <=> ("storage")
@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageConfig {
    //config ten thuoc tinh phai map voi thuoc tinh trong application.properties
    private String location;
    public StorageConfig(){
        this.setLocation("upload/images");
    }
}
