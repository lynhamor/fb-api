package com.fb.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fb.api.dto.ApiResponse;
import com.fb.api.model.UserDataModel;
import com.fb.api.model.UserModel;
import com.fb.api.service.AppService;
import com.fb.api.util.CommonConstants;


@Service
@SuppressWarnings({"unchecked","rawtypes"})
public class AppServiceImpl implements AppService {

	private static final Logger LOG = LoggerFactory.getLogger(AppServiceImpl.class);

	private List<UserDataModel> users = new ArrayList<>();
	private int id = 100;

	@Override
	public ApiResponse<UserDataModel> addUser(String userName, String email) {
		LOG.trace("addUser... ");
		try {
			boolean isExist = users.stream().anyMatch(user -> user.getUserInfo().getUsername().equals(userName)
					|| user.getUserInfo().getEmail().equals(email));

			UserModel user = new UserModel(userName, email);
			UserDataModel userData = null;

			if (!isExist) {
				LOG.trace("user: {} is avaible ", user.toString());
				user.setId(id);
				userData = new UserDataModel(user, new HashMap<Integer, UserModel>());
				users.add(userData);
				id++;

				LOG.trace("new user added. {} ", userData);

				return new ApiResponse<UserDataModel>(CommonConstants.SUCCESS, "new user added", userData);
			} else {
				userData = new UserDataModel(user, new HashMap<Integer, UserModel>());
				LOG.trace("user already exist. {} ", userData);
				return new ApiResponse<UserDataModel>("UNKNOWN", "user already exist", userData);
			}
		} catch (Exception e) {
			LOG.error("An Error Encountered. {}", e.getMessage());
			return new ApiResponse(CommonConstants.FAILED, "An Error Encountered.");
		}
	}

	@Override
	public ApiResponse<UserDataModel> friendRequest(int userId, int fid) {
		LOG.trace("friendRequest... ");
		try {
			addRequest(fid, userId);
			return addRequest(userId, fid);

		} catch (Exception e) {
			LOG.error("An Error Encountered. {}", e.getMessage());
			return new ApiResponse(CommonConstants.FAILED, "An Error Encountered.");
		}
	}

	private ApiResponse<UserDataModel> addRequest(int id, int fid) {
		Optional<UserDataModel> userOption = users.stream().filter(u -> u.getUserInfo().getId() == id).findFirst();
		if (userOption.isPresent()) {
			UserDataModel userData = userOption.get();
			UserModel friend = userData.getFriends().get(fid);

			if (friend != null) {
				LOG.trace("already friends with {}", friend.getUsername());
				return new ApiResponse<UserDataModel>(CommonConstants.UNKNOWN,
						"already friends with ".concat(friend.getUsername()), userData);
			} else {
				LOG.trace("adding new friend");

				Optional<UserModel> friendOption = users.stream().map(UserDataModel::getUserInfo)
						.filter(u -> u.getId() == fid).findFirst();

				friend = friendOption.get();
				if (userData.getUserInfo().getId() == fid) {
					LOG.trace("Cannot Add yourself {}", friend.getUsername());
					return new ApiResponse<UserDataModel>(CommonConstants.UNKNOWN,
							"Cannot Add yourself ".concat(friend.getUsername()), userData);
				}

				HashMap<Integer, UserModel> friends = userData.getFriends();
				friends.put(fid, friend);
				userData.setFriends(friends);

				LOG.trace("New Friend Added {}", friend.getUsername());
				return new ApiResponse<UserDataModel>(CommonConstants.SUCCESS,
						"New Friend Added ".concat(friend.getUsername()), userData);
			}
		} else {
			LOG.trace("userId not found {}", userOption.get());
			return new ApiResponse<UserDataModel>(CommonConstants.UNKNOWN, "userId not found ");
		}
	}

	@Override
	public ApiResponse<List<UserDataModel>> getAll() {
		return new ApiResponse(CommonConstants.SUCCESS, users);
	}

}
