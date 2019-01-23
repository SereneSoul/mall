package com.mmall.user.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableDubbo(scanBasePackages="com.mmall.user.service.impl")
public class DubboConfiguration {
    
    @Value("${dubbo.protocol.name}")
    private String protocolName;

    @Value("${dubbo.protocol.host}")
    private String protocolHost;

    @Value("${dubbo.application.name}")
    private String applicationName;
    
    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol(protocolName);
        registryConfig.setAddress(protocolHost);
        return registryConfig;
    }
    
    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
        return applicationConfig;
    }
}
