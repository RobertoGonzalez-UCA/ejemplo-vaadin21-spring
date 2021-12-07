package es.uca.iw.ejemplo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	private UserRepository repository;

	private UserEmailService emailService;

	private PasswordEncoder encoder;

	@Autowired
	public UserService(UserRepository repository, UserEmailService emailService, PasswordEncoder encoder) {
		this.repository = repository;
		this.emailService = emailService;
		this.encoder = encoder;
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByUsername(username);
		if (user.isPresent()) {
			return user.get();
		} else {
			throw new UsernameNotFoundException(username);
		}

	}

	public void registerUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
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

	public Optional<User> findById(Integer id) {
		return repository.findById(id);
	}

}
