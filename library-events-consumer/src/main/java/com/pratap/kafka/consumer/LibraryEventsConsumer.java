package com.pratap.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LibraryEventsConsumer {

    /**
     * continuously poll the record from topic 'library-events'
     * @param consumerRecord
     */
    @KafkaListener(topics = {"library-events"})
    public void onMessage(ConsumerRecord<Integer, String> consumerRecord){

        log.info("consumerRecord : {}", consumerRecord);
    }
}
