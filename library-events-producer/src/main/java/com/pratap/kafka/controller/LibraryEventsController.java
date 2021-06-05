package com.pratap.kafka.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.kafka.domain.LibraryEvent;
import com.pratap.kafka.domain.LibraryEventType;
import com.pratap.kafka.producer.LibraryEventsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/library-app")
@Slf4j
public class LibraryEventsController {

    @Autowired
    private LibraryEventsProducer libraryEventProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/v1/libraryevents")
    public ResponseEntity<LibraryEvent> postLibraryEventAsynchronously(@Valid @RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {

        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        log.info("Executing postLibraryEvent() with libraryEvent : {}", objectMapper.writeValueAsString(libraryEvent));
        // invoke kafka producer
        libraryEventProducer.sendLibraryEventAsynchronously(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v2/libraryevents")
    public ResponseEntity<LibraryEvent> postLibraryEventSynchronously(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

        log.info("Executing postLibraryEvent() with libraryEvent : {}", objectMapper.writeValueAsString(libraryEvent));
        // invoke kafka producer
        SendResult<Integer, String> sendResult = libraryEventProducer.sendLibraryEventSynchronously(libraryEvent);
        log.info("sendResult : {}", sendResult.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    @PostMapping("/v3/libraryevents")
    public ResponseEntity<LibraryEvent> postLibraryEventApproachTwo(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

        libraryEvent.setLibraryEventType(LibraryEventType.NEW);
        log.info("Executing postLibraryEvent() with libraryEvent : {}", objectMapper.writeValueAsString(libraryEvent));
        // invoke kafka producer
        ListenableFuture<SendResult<Integer, String>> resultListenableFuture = libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);
        log.info("sendResult : {}", resultListenableFuture.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }

    //PUT
    @PutMapping("/v1/libraryevents")
    public ResponseEntity<?> putLibraryEvent(@RequestBody @Valid LibraryEvent libraryEvent) throws JsonProcessingException, ExecutionException, InterruptedException {

        if(libraryEvent.getLibraryEventId()==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please pass the LibraryEventId");
        }
        libraryEvent.setLibraryEventType(LibraryEventType.UPDATE);
        libraryEventProducer.sendLibraryEvent_Approach2(libraryEvent);
        return ResponseEntity.status(HttpStatus.OK).body(libraryEvent);
    }
}
