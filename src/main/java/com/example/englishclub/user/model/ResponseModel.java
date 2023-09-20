package com.example.englishclub.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseModel {
	private int code;
	private String body;
}
