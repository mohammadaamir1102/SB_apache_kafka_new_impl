package com.aamir.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public NewTopic createTopic(){
        return new
                NewTopic("aaamirKhan-topic",3, (short) 1);
    }

    @Bean
    public NewTopic pojoTopic(){
        return new
                NewTopic("pojo-topic",3, (short) 1);
    }
}
