package io.github.luankuhlmann.compassUoldesafio3.client.mqueues;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    @Autowired
    JmsTemplate jmsTemplate;

    public void sendMessage(String queue, final String messageStr) {
        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session.createObjectMessage(messageStr);
                return message;
            }
        });
    }
}
