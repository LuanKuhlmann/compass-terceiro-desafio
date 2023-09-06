package io.github.luankuhlmann.compassUoldesafio3.config;

import jakarta.jms.ConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
@EnableJms
public class JmsConfig {
    public JmsListenerContainerFactory<?> myFactory(
            ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {

        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        factory.setErrorHandler(
                t -> System.err.println("An error has occurred in the transaction"));
        configurer.configure(factory, connectionFactory);

        return factory;
    }
}
