package com.aamir.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "ak-test-topic", groupId = "aamir-group")
    public void consumeMessage(String message) {
        log.info("consumed message is {} ", message);
    }
}
