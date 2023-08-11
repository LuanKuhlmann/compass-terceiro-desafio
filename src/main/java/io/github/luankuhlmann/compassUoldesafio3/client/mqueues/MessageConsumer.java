package io.github.luankuhlmann.compassUoldesafio3.client.mqueues;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @JmsListener(destination = "demo")
    public void receiveMessage(String messageStr) throws JsonProcessingException {
        System.out.println(messageStr);
        ObjectMapper mapper = new ObjectMapper();
        PostDto postDto = mapper.readValue(messageStr, PostDto.class);
        System.out.println("Consuming post " + postDto.getId());
    }
}
