package com.fundoo.fundoonotes.jms.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @RabbitListener(queues = "note_queue")

    public void receiveMessage(String message) { System.out.println("Message is received: " + message);

    }
}