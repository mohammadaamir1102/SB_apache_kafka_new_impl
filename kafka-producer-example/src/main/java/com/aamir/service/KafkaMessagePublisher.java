package com.aamir.service;

import com.aamir.controller.EventController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    Logger log = LoggerFactory.getLogger(KafkaMessagePublisher.class);

    @Autowired
    private KafkaTemplate<String, Object> template;


    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> furtherProcessing = template.send("ak-test-topic", message);
        furtherProcessing.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("partiton is " + result.getRecordMetadata().partition());
                log.info("Sent Message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.info("Unable to send message=[" + message + "] due to : " + exception.getMessage());
            }
        });
    }


    /*
    public void sendMessageToTopic(String message) {
        template.send("aamir-topic786", message);
    }

     */
}
