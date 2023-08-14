package io.github.luankuhlmann.compassUoldesafio3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class Flux {
    @Bean(name = "pool-executer")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(300);
        executor.setThreadNamePrefix("THREAD POOL -");
        executor.initialize();
        return executor;
    }
}
