package com.tweetapp.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

//@Data
//@Getter
//@Setter
@Document(collection = "DatabaseSequences")
public class DatabaseSequence {
	@Id
	private String id;
	private int seq;
//	public String getId() {
//		return id;
//	}
//	public void setId(String id) {
//		this.id = id;
//	}
	public int getSeq() {
		return seq;
	}
//	public void setSeq(int seq) {
//		this.seq = seq;
//	}

// getters and setters
}
