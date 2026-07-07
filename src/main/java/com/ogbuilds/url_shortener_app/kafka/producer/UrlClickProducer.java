package com.ogbuilds.url_shortener_app.kafka.producer;

import com.ogbuilds.url_shortener_app.kafka.event.UrlClickEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UrlClickProducer {

    private static final String TOPIC = "url-click-events";

    private final KafkaTemplate<String, UrlClickEvent> kafkaTemplate;

    public void publishClickEvent(UrlClickEvent event) {
        kafkaTemplate.send(TOPIC, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        ex.printStackTrace();
                    } else {
                        System.out.println("Message sent to Kafka!");
                    }
                });
    }



}
