package es.uca.iw.ejemplo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.uca.iw.ejemplo.user.User;
import es.uca.iw.ejemplo.user.UserRepository;

/**
 * @author ivanruizrube
 *
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class UserRepositoryUnitTest {

	@Autowired
	private UserRepository userRepository;

	User testUser;

	@Before
	public void setUp() {

		testUser = new User();
		testUser.setFirstName("customer1");
		testUser.setLastName("customer1");

	}

	@Test
	public void shouldSaveAUser() {

		// Given a person not saved on the repo
		assertThat(testUser.getId()).isNull();

		// When invoking the method
		userRepository.save(testUser);

		assertThat(testUser.getId()).isNotNull();

	}

	@Test
	public void shouldNotFindANotExistingUser() {

		// When invoking the method
		Optional<User> foundUser = userRepository.findById(123);

		// Then
		assertThat(foundUser.isPresent()).isFalse();

	}

	@Test
	public void shouldFindAnExistingUser() {

		// Given a person
		userRepository.save(testUser);

		// When invoking the method
		Optional<User> foundUser = userRepository.findById(testUser.getId());

		// Then
		assertThat(foundUser.get()).isEqualTo(testUser);

	}
}
