package com.aamir.consumer;

import com.aamir.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

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
    public void pojoConsumer(Employee employee){
        log.info("pojo object is in String {} and object {} ", employee.toString(), employee);
    }

}
