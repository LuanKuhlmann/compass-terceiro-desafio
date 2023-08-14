package io.github.luankuhlmann.compassUoldesafio3.client.mqueues;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class MessageConsumer {

    @JmsListener(destination = "process_posts")
    public void receiveMessageFromPost(String messageStr) throws JsonProcessingException {
        System.out.println(messageStr);
        ObjectMapper mapper = new ObjectMapper();
        PostDto postDto = mapper.readValue(messageStr, PostDto.class);
        System.out.println("Consuming post " + postDto.getId());
    }

    @JmsListener(destination = "process_comments")
    public void receiveMessageFromComments(String messageStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<CommentDto> commentList = new ArrayList<>();
        CommentDto[] receivedComments = mapper.readValue(messageStr, CommentDto[].class);
        commentList.addAll(Arrays.asList(receivedComments));

        for (CommentDto commentDto : receivedComments) {
            System.out.println("Consuming comment " + commentDto.getId());
        }
    }

}
