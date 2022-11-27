package com.tweetapp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Data
@Getter
@Setter
@Document(collection = "DatabaseSequences")
public class DatabaseSequence {
    @Id
    private String id;
    private int seq;

// getters and setters
}
