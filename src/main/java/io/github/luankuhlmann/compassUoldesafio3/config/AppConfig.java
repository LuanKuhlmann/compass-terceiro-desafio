package io.github.luankuhlmann.compassUoldesafio3.config;

import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
