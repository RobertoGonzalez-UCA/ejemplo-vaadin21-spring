package es.uca.iw.ejemplo.user;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import es.uca.iw.ejemplo.user.User;
import es.uca.iw.ejemplo.user.UserService;

/**
 * @author ivanruizrube
 *
 */

@RunWith(SpringRunner.class)
@WebMvcTest()
public class UserRestControllerUnitTest {

	@Autowired
	private MockMvc server;

	@MockBean
	private UserService userService;

	private User testUser;

	@Before
	public void setUp() {
		testUser = new User();
		testUser.setFirstName("customer1");
		testUser.setLastName("customer1");
		List<User> allUsers = Arrays.asList(testUser);

		Mockito.when(userService.findActiveUsers()).thenReturn(allUsers);
	}

	@Test
	public void shouldReturnListOfUsers() {

		// Given a HTTP call
		String input = "/api/Users";

		// When make a HTTP API Rest invocation and assertion
		try {
			server.perform(get(input).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(jsonPath("$", hasSize(1)))
					.andExpect(jsonPath("$[0].firstName", is(testUser.getFirstName())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
