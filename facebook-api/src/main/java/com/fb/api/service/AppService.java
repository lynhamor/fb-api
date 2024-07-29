package com.fb.api.service;

import java.util.List;

import com.fb.api.dto.ApiResponse;
import com.fb.api.model.UserDataModel;

public interface AppService {

	ApiResponse<UserDataModel> addUser(String username, String email);

	ApiResponse<UserDataModel> friendRequest(int userId, int fid);

	ApiResponse<List<UserDataModel>> getAll();

}
