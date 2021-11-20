package es.uca.iw.ejemplo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ivanruizrube
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserIntegrationTest {

	@Autowired
	private UserService userService;

	private User testUser;

	@Before
	public void setUp() {

		String userName = "customer1";
		String email = userName + "@example.com";
		testUser = new User();
		testUser.setFirstName(userName);
		testUser.setLastName(userName);
		testUser.setEmail(email);

	}

	@Test
	public void shouldActivateAnExistingUser() {

		// Given a certain user stored on the repo
		userService.registerUser(testUser);

		// When invoking the method ActivateUser
		boolean result = userService.activateUser(testUser.getEmail(), "key");

		// Then the result method is true
		assertThat(result).isTrue();

		// When invoking the method FindActive
		List<User> returnedUsers = userService.findActiveUsers();

		// Then the result includes the user
		assertThat(returnedUsers.contains(testUser)).isTrue();

	}

	@Test
	public void shouldNotActivateANotExistingUser() {

		// Given a certain user not stored on the repo

		// When invoking the method
		boolean result = userService.activateUser(testUser.getEmail(), "key");

		// Then the result method is false
		assertThat(result).isFalse();

		// When invoking the method FindActive
		List<User> returnedUsers = userService.findActiveUsers();

		// Then the result does not include the user
		assertThat(returnedUsers.contains(testUser)).isFalse();

	}

}
