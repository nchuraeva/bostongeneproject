package com.example.secondtask;

import com.example.secondtask.entities.User;
import com.example.secondtask.repositories.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class UserControllerTest {

	private static final Long USER_ONE_ID = 1L;
	private static final Long USER_TWO_ID = 2L;
	private static final Long USER_THREE_ID = 3L;
	private static final Long USER_FOUR_ID = 4L;

	private static MediaType CONTENT_TYPE =
			new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public void setConverters(HttpMessageConverter<?>[] converters) {
		mappingJackson2HttpMessageConverter = Arrays.asList(converters)
				.stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);
		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void init() {
		mockMvc = webAppContextSetup(webApplicationContext).build();
		User user1 = new User(USER_ONE_ID,"Nadia", "Churaeva","churaeva123@yandex.ru",24,"mkyong1A@" );
		User user2 = new User(USER_TWO_ID,"Luis", "Petrova", "luis@yopmail.com", 23, "nkyong1A@");
		User  user3 = new User(USER_THREE_ID ,"Max", "Ivanov","max@yopmail.com", 21, "mlyong1A@");
		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);
	}


	@Test
	public void createUser() throws Exception {
		User user4 = new User(USER_FOUR_ID,"Maria", "Ivanova", "maria@yopmail.com", 20, "mjkong1A@") ;
		mockMvc.perform(post("/user/add")
				.contentType(CONTENT_TYPE)
				.content(json(user4)))
				.andExpect(status().isCreated());
	}

	@Test
	public void findAll() throws Exception{
		mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$", Matchers.hasSize(3)));
	}



	@Test
	public void getExistingUserByEmail() throws Exception {
		mockMvc.perform(get("/user/find/email")
				.param("email", "luis@yopmail.com"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.id", Matchers.is(2)))
				.andExpect(jsonPath("$.email", Matchers.is("luis@yopmail.com")));
	}




	@Test
	public void deleteUser() throws Exception{
		mockMvc.perform(delete("/user/" + USER_ONE_ID))
				.andExpect(status().isOk());
	}


	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}



}
