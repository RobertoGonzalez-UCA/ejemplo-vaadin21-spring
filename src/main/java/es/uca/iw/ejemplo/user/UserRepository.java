package es.uca.iw.ejemplo.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

	
	Optional<User> findFirstByFirstName(String firstName);

	Optional<User> findFirstByEmail(String email);

	List<User> findByActiveTrue();
}