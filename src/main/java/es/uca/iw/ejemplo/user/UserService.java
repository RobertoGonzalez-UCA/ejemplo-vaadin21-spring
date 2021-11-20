package es.uca.iw.ejemplo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	private UserRepository repository;

	private UserEmailService emailService;

	@Autowired
	public UserService(UserRepository repository, UserEmailService emailService) {
		this.repository = repository;
		this.emailService = emailService;
	}

	public void registerUser(User user) {
		repository.save(user);
		String subject = "Welcome";
		String body = "You should active your account...";
		emailService.sendEmail(user.getEmail(), subject, body);
	}

	public boolean activateUser(String email, String key) {

		Optional<User> user = repository.findFirstByEmail(email);

		if (user.isPresent() && !key.isEmpty()) {
			user.get().setActive(true);
			repository.save(user.get());
			return true;
		} else {
			return false;
		}

	}

	public List<User> findActiveUsers() {
		return repository.findByActiveTrue();
	}

}
