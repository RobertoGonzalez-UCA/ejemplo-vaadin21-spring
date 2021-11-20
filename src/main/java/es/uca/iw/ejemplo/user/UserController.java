package es.uca.iw.ejemplo.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	private UserService service;


	public UserController(UserService service) {
		this.service = service;
	}
	
	
	@GetMapping("/api/Users")
	public List<User> findAllUsers() {
		return service.findActiveUsers();
	}
}
