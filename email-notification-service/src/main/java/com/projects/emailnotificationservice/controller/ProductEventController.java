package com.projects.emailnotificationservice.controller;

import com.projects.core.product.ProductCreatedRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductEventController {

    @KafkaListener(topics = "my-topic", groupId = "my-group")
    public void onProductCreated(ProductCreatedRes message) {
        log.info("Received message {}", message.name());
    }
}
