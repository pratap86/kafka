package com.pratap.kafka.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    private Integer bookId;
    private String bookName;
    private String bookAuthor;
}
