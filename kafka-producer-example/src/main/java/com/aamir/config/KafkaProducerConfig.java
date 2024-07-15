package com.aamir.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new
                NewTopic("ak-test-topic",5, (short) 1);
    }
}
