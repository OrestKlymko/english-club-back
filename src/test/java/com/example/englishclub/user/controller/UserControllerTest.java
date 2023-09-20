package com.example.englishclub.user.controller;

import com.example.englishclub.security.jwt.JWTtoken;
import com.example.englishclub.user.entity.enums.LevelEnglish;
import com.example.englishclub.user.entity.enums.ThemesType;
import com.example.englishclub.user.model.UserResponseModel;
import com.example.englishclub.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private UserService userService;
	@MockBean
	private JWTtoken jwTtoken;

	@Test
	void getAllUsers() throws Exception {
		UserResponseModel user1 = UserResponseModel.builder().id(1L).username("User").themesType(ThemesType.IT).creatingClubsByUser(new HashSet<>()).existClubs(new HashSet<>()).country("Ukraine").email("test@gmail.com").levelOfEnglish(LevelEnglish.Beginner).build();
		UserResponseModel user2 = UserResponseModel.builder().id(2L).username("User2").themesType(ThemesType.IT).creatingClubsByUser(new HashSet<>()).existClubs(new HashSet<>()).country("Ukraine").email("test@gmail.com").levelOfEnglish(LevelEnglish.Beginner).build();
		UserResponseModel user3 = UserResponseModel.builder().id(3L).username("User3").themesType(ThemesType.IT).creatingClubsByUser(new HashSet<>()).existClubs(new HashSet<>()).country("Ukraine").email("test@gmail.com").levelOfEnglish(LevelEnglish.Beginner).build();

		List<UserResponseModel> userList = new LinkedList<>();
		userList.add(user1);
		userList.add(user2);
		userList.add(user3);
		when(userService.getAll()).thenReturn(userList);
		mockMvc.perform(get("/all"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$",hasSize(3)))
				.andExpect(jsonPath("$[*].id",containsInAnyOrder(1,2,3)))
				.andExpect(jsonPath("$[*].username",containsInAnyOrder("User","User2","User3")));

	}
}