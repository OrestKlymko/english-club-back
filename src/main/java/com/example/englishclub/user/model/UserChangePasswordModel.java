package com.example.englishclub.user.model;


import lombok.Data;

@Data
public class UserChangePasswordModel {
	private String email;
	private String oldPassword;
	private String newPassword;
}
