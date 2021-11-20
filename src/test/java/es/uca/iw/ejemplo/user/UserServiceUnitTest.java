package es.uca.iw.ejemplo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author ivanruizrube
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserEmailService emailService;

	private User testUser;

	@Before
	public void setUp() {

		String userName = "customer1";
		String email = userName + "@example.com";

		testUser = new User();
		testUser.setFirstName(userName);
		testUser.setLastName(userName);
		testUser.setEmail(email);

		// Mocking the email service
		Mockito.when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(true);

	}

	@Test
	public void shouldActivateAnExistingUser() {

		// Mocking the repo
		List<User> poslist = new ArrayList<User>();
		poslist.add(testUser);
		Mockito.when(userRepository.findFirstByEmail("customer1@example.com")).thenReturn(Optional.of(testUser));
		Mockito.when(userRepository.findByActiveTrue()).thenReturn(poslist);

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

		// When invoking the method ActivateUser
		boolean result = userService.activateUser(testUser.getEmail(), "key");

		// Then the result method is false
		assertThat(result).isFalse();

		// When invoking the method FindActive
		List<User> returnedUsers = userService.findActiveUsers();

		// Then the result does not include the user
		assertThat(returnedUsers.contains(testUser)).isFalse();

	}

}
