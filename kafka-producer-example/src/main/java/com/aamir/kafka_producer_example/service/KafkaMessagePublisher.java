package com.aamir.kafka_producer_example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, Object> template;


    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> furtherProcessing = template.send("test-topic-1", message);
        furtherProcessing.whenComplete((result, exception) -> {
            if (exception == null) {
                System.out.println("partiton is " + result.getRecordMetadata().partition());
                System.out.println("Sent Message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" + message + "] due to : " + exception.getMessage());
            }
        });
    }


    /*
    public void sendMessageToTopic(String message) {
        template.send("aamir-topic786", message);
    }

     */
}
