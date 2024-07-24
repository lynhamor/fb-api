package com.fb.api.model;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class UserDataModel {

	private UserModel userInfo;
	private HashMap<Integer, UserModel> friends;
}
