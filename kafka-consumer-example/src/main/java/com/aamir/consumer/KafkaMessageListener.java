package com.aamir.consumer;

import com.aamir.entity.Employee;
import com.aamir.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class KafkaMessageListener {

    Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

    @KafkaListener(topics = "aaamirKhan-topic", groupId = "aamirKhan-group")
    public void consumeMessage1(String message) {
        log.info("consumer1 consume the messages is {} ", message);
    }

    // below creating multiple instance of the consumer for consuming same topic
    // All below for the demo purpose in project this is not the way to implements
    // If the partition is 3 then any 3 can consume the data. we are not sure which three instances will consume
    // It is decided by Cardinator

    /*

    @KafkaListener(topics = "aaamirKhan-topic", groupId = "aamirKhan-group")
    public void consumeMessage2(String message) {
        log.info("consumer2 consume the messages is {} ", message);
    }

    @KafkaListener(topics = "aaamirKhan-topic", groupId = "aamirKhan-group")
    public void consumeMessage3(String message) {
        log.info("consumer3 consume the messages is {} ", message);
    }

    @KafkaListener(topics = "aaamirKhan-topic", groupId = "aamirKhan-group")
    public void consumeMessage4(String message) {
        log.info("consumer4 consume the messages is {} ", message);
    }


    */

    @KafkaListener(topics = "pojo-topic", groupId = "pojo-group")
    public void pojoConsumer(Employee employee) {
        log.info("pojo object is in String {} and object {} ", employee.toString(), employee);
    }

    @RetryableTopic(attempts = "4") // default attempt is 3 and internally create 3 topic for retry
    @KafkaListener(topics = "${service.topic.name}", groupId = "retry-group")
    public void consumeEvent(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset) {
        try {
            log.info("Received: {} from {} offset {} ", new ObjectMapper().writeValueAsString(user), topic, offset);
            List<String> restrictedIPAddress = Stream.of("10.100.15.235", "10.100.16.236", "10.100.17.237").toList();
            if (restrictedIPAddress.contains(user.getIpAddress())) {
                throw new RuntimeException("Invalid IP Address is received!");
            }
        } catch (JsonProcessingException e) {
            log.error("error {}", e.getMessage());
        }

    }

    @DltHandler
    public void listenDeadLetterTopic(User user, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Header(KafkaHeaders.OFFSET) long offset){
        log.info("DLT Received: {} from {} offset {} ", user.getId(), topic, offset);
    }

}
