package com.fb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponse<T> {

	private String status;
	private String msg;
	private T data;

	public ApiResponse(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public ApiResponse(String status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
}
