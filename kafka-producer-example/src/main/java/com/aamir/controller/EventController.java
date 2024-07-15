package com.aamir.controller;

import com.aamir.entity.Employee;
import com.aamir.service.KafkaMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/producer-app")
public class EventController {

    Logger log = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @GetMapping("/publish/{message}")
    public ResponseEntity<?> messagePublisher(@PathVariable String message) {
        try {
            for (int i = 0; i < 100000; i++) {
                kafkaMessagePublisher.sendMessageToTopic(message + " " + i);
            }
            return ResponseEntity.ok("Message published done");
        } catch (Exception e) {
            log.error("error in messagePublisher method of EventController ", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/PublishingPojoData")
    public ResponseEntity<?> publishingPojoData(@RequestBody Employee employee) {
        try {
            kafkaMessagePublisher.sendPojoMessage(employee);
            return ResponseEntity.ok("Pojo Data is published ");
        } catch (Exception e) {
            log.error("error: ", e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
