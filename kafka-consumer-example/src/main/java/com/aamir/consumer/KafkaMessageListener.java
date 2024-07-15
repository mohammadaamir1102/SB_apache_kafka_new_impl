package com.aamir.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "khan-topic", groupId = "khan-group")
    public void consumeMessage1(String message) {
        log.info("consumer1 consume the messages is {} ", message);
    }

    // below creating multiple instance of the consumer for consuming same topic

    @KafkaListener(topics = "khan-topic", groupId = "khan-group")
    public void consumeMessage2(String message) {
        log.info("consumer2 consume the messages is {} ", message);
    }

    @KafkaListener(topics = "khan-topic", groupId = "khan-group")
    public void consumeMessage3(String message) {
        log.info("consumer3 consume the messages is {} ", message);
    }

    @KafkaListener(topics = "khan-topic", groupId = "khan-group")
    public void consumeMessage4(String message) {
        log.info("consumer4 consume the messages is {} ", message);
    }

}
