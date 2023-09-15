package com.example.englishclub.user.entity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class Test {
	public static void main(String[] args) {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("User2")
				.password("password2")
				.roles("user")
				.build();
		System.out.println(userDetails.getPassword());
	}
}
