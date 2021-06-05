package com.pratap.kafka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pratap.kafka.domain.Book;
import com.pratap.kafka.domain.LibraryEvent;
import com.pratap.kafka.domain.LibraryEventType;
import com.pratap.kafka.producer.LibraryEventsProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LibraryEventsController.class)
@AutoConfigureMockMvc
public class LibraryEventsControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    LibraryEventsProducer libraryEventProducer;

    @Test
    void testPostLibraryEventAsynchronously() throws Exception {

        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Test")
                .bookName("TestBook")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .libraryEventType(LibraryEventType.NEW)
                .book(book)
                .build();
        String json = objectMapper.writeValueAsString(libraryEvent);
        doNothing().when(libraryEventProducer).sendLibraryEventAsynchronously(isA(LibraryEvent.class));
        //expect
        mockMvc.perform(post("/library-app/v1/libraryevents")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testPostLibraryEventAsynchronously_With_InvalidRequest() throws Exception {

        Book book = Book.builder()
                .bookId(null)
                .bookAuthor("")
                .bookName("TestBook")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .libraryEventType(LibraryEventType.NEW)
                .book(book)
                .build();

        String json = objectMapper.writeValueAsString(libraryEvent);
        doNothing().when(libraryEventProducer).sendLibraryEventAsynchronously(isA(LibraryEvent.class));

        mockMvc.perform(post("/library-app/v1/libraryevents")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testPutLibraryEvent() throws Exception {
        //given
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Test")
                .bookName("TestBook")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(423)
                .book(book)
                .build();
        String json = objectMapper.writeValueAsString(libraryEvent);
        when(libraryEventProducer.sendLibraryEvent_Approach2(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        mockMvc.perform(
                put("/library-app/v1/libraryevents")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void testPutLibraryEvent_withNullLibraryEventId() throws Exception {
        //given
        Book book = Book.builder()
                .bookId(123)
                .bookAuthor("Test")
                .bookName("TestBook")
                .build();

        LibraryEvent libraryEvent = LibraryEvent.builder()
                .libraryEventId(null)
                .book(book)
                .build();
        String json = objectMapper.writeValueAsString(libraryEvent);
        when(libraryEventProducer.sendLibraryEvent_Approach2(isA(LibraryEvent.class))).thenReturn(null);

        //expect
        mockMvc.perform(
                put("/library-app/v1/libraryevents")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("Please pass the LibraryEventId"));

    }
}
