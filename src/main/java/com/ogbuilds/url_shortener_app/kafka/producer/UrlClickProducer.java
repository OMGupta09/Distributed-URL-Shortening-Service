package com.ogbuilds.url_shortener_app.kafka.producer;

import com.ogbuilds.url_shortener_app.kafka.event.UrlClickEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlClickProducer {

    private static final String TOPIC = "url-click-events";

    private final KafkaTemplate<String, UrlClickEvent> kafkaTemplate;

    public void publishClickEvent(UrlClickEvent event) {

        try {

            kafkaTemplate.send(TOPIC, event)
                    .whenComplete((result, ex) -> {

                        if (ex != null) {
                            log.warn(
                                    "Failed to publish click event for urlId={}",
                                    event.getUrlId(),
                                    ex
                            );
                        }

                    });

        } catch (RuntimeException ex) {

            log.warn(
                    "Kafka unavailable; skipping click event for urlId={}",
                    event.getUrlId(),
                    ex
            );
        }
    }
}