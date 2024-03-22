package com.example.manager_app.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapperConfig=new ModelMapper();
        mapperConfig.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapperConfig;
    }
}
