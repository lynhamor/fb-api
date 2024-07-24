package com.fb.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {

	private int id;
	private String username;
	private String email;

	public UserModel(String username, String email) {

		this.username = username;
		this.email = email;
	}
}
