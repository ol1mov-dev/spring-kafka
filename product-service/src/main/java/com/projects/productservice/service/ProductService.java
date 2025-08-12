package com.projects.productservice.service;

import com.projects.core.product.ProductCreatedReq;
import com.projects.core.product.ProductCreatedRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ProductService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ProductCreatedRes createAsync(com.projects.core.product.ProductCreatedReq productCreatedReq) {
        ProductCreatedRes productCreatedRes = ProductCreatedRes.builder()
                .name(productCreatedReq.name())
                .price(productCreatedReq.price())
                .build();

        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate
                .send("my-topic", "product", productCreatedRes);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending message: {}", ex.getMessage());
            } else {
                log.info("Message sent successfully: {}", result.getProducerRecord().value());
            }
        });

        return productCreatedRes;
    }

    public ProductCreatedRes createSync(ProductCreatedReq productCreatedReq) throws Exception {
        ProductCreatedRes productCreatedRes = ProductCreatedRes.builder()
                .name(productCreatedReq.name())
                .price(productCreatedReq.price())
                .build();

        log.info("Before sending message");
        SendResult<String, Object> result =
                kafkaTemplate.send("my-topic", productCreatedRes).get();

        log.info(
                "Partition: {}, Topic: {}, Offset: {}",
                result.getRecordMetadata().partition(),
                result.getRecordMetadata().topic(),
                result.getRecordMetadata().offset()
        );

        return productCreatedRes;
    }
}
