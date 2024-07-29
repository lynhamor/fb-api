package com.fb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fb.api.dto.ApiResponse;
import com.fb.api.model.UserDataModel;
import com.fb.api.service.AppService;
import com.fb.api.util.UrlConstants;

@RestController
@RequestMapping(value = UrlConstants.BASE_API)
public class ApplicationController {

	@Autowired
	private AppService service;

	@PostMapping(value = UrlConstants.ADD_USER)
	public ApiResponse<UserDataModel> addUser(@RequestParam(value = "username") String userName,
			@RequestParam(value = "email") String email) {

		return service.addUser(userName, email);
	}

	@PutMapping(value = "/{userId}" + UrlConstants.ADD_FRIEND)
	public ApiResponse<UserDataModel> friendRequest(@PathVariable(value = "userId") int userId,
			@RequestParam(value = "friendId") int fid) {

		return service.friendRequest(userId, fid);

	}

	@GetMapping(value = "/list")
	public ApiResponse<List<UserDataModel>> getAll() {
		return service.getAll();
	}
}
