package com.aamir.service;

import com.aamir.entity.Employee;
import com.aamir.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    Logger log = LoggerFactory.getLogger(KafkaMessagePublisher.class);

    @Value("${service.topic.name}")
    private String kafkaRetryTopic;

    @Autowired
    private KafkaTemplate<String, Object> template;


    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> furtherProcessing = template.send("aaamirKhan-topic", message);
        furtherProcessing.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Partitions is {}", result.getRecordMetadata().partition());
                log.info("In sendMessageToTopic Sent Message= {} with offset= {}", message, result.getRecordMetadata().offset());
            } else {
                log.info("In sendMessageToTopic Unable to send message= {} due to {} : ", message, exception.getMessage());
            }
        });
    }

    public void sendPojoMessage(Employee employee) {
        CompletableFuture<SendResult<String, Object>> furtherProcessing = template.send("pojo-topic", employee);
        furtherProcessing.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("In sendPojoMessage Sent Message= {} with offset= {}", employee.toString(), result.getRecordMetadata().offset());
            } else {
                log.info("In sendPojoMessage Unable to send message= {} due to {} : ", employee.toString(), exception.getMessage());
            }
        });
    }

    public void sendUserMessage(User user) {
        CompletableFuture<SendResult<String, Object>> furtherProcessing = template.send(kafkaRetryTopic, user);
        furtherProcessing.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("In sendUserMessage Sent Message= {} with offset= {}", user.toString(), result.getRecordMetadata().offset());
            } else {
                log.info("In sendUserMessage Unable to send message= {} due to {} : ", user.toString(), exception.getMessage());
            }
        });
    }

}
