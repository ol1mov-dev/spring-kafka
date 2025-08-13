package com.projects.emailnotificationservice.controller;

import com.projects.core.product.ProductCreatedRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "my-topic")
public class ProductEventController {

    @KafkaHandler
    public void onProductCreated(ProductCreatedRes message) {
        log.info("Received message {}", message.name());
    }
}
