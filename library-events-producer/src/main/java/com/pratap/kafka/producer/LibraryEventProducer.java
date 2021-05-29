package com.pratap.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.kafka.domain.LibraryEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class LibraryEventProducer {

    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    String topic = "library-events";

    public void sendLibraryEventAsynchronously(LibraryEvent libraryEvent) throws JsonProcessingException {

        log.info("Executing sendLibraryEventAsynchronously() with libraryEvent : {}", objectMapper.writeValueAsString(libraryEvent));
        // key and value type are declared application.yml :
        //producer:
        //      bootstrap-servers: localhost:9092,localhost:9093,localhost:9094
        //      key-serializer: org.apache.kafka.common.serialization.IntegerSerializer
        //      value-serializer: org.apache.kafka.common.serialization.StringSerializer
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        log.info("Invoking kafkaTemplate send() by key : {} and value : {}", key, value);
        ListenableFuture<SendResult<Integer, String>> sendResultListenableFuture = kafkaTemplate.sendDefault(key, value);

        log.info("sendResultListenableFuture : {}  going to execute addCallback()", sendResultListenableFuture);
        sendResultListenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.info("handleFailure() : Error sending the message key : {} \n value : {}", key, value);

        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure : {}", throwable.getMessage());
        }
    }

    private void handleSuccess(Integer key, String value, SendResult<Integer, String> result) {
        log.info("handleSuccess() : message sent successfully for the key : {} \n the value : {} partition : {}",key, value, result.getRecordMetadata().partition());
    }

    public SendResult<Integer, String> sendLibraryEventSynchronously(LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

        log.info("Executing sendLibraryEventSynchronously with libraryEvent : {}", objectMapper.writeValueAsString(libraryEvent));
        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        log.info("Invoking kafkaTemplate send() by key : {} and value : {}", key, value);
        SendResult<Integer, String> sendResult = null;
        try {
            sendResult = kafkaTemplate.sendDefault(key, value).get(1, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException e) {
            log.error("InterruptedException/ExecutionException sending the message and message : {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.info("Exception sending the message and Exception : {}", e.getMessage());
        }
        return sendResult;
    }

    public ListenableFuture<SendResult<Integer,String>> sendLibraryEvent_Approach2(LibraryEvent libraryEvent) throws JsonProcessingException {

        Integer key = libraryEvent.getLibraryEventId();
        String value = objectMapper.writeValueAsString(libraryEvent);

        ProducerRecord<Integer,String> producerRecord = buildProducerRecord(key, value, topic);

        ListenableFuture<SendResult<Integer,String>> listenableFuture =  kafkaTemplate.send(producerRecord);

        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(key, value, ex);
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                handleSuccess(key, value, result);
            }
        });

        return listenableFuture;
    }

    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value, String topic) {

        List<Header> recordHeaders = List.of(new RecordHeader("event-source", "scanner".getBytes()));

        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }
}
