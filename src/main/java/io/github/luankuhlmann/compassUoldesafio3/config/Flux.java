package io.github.luankuhlmann.compassUoldesafio3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class Flux {

    @Bean(name = "pool-executer")
    public Executor taskExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
