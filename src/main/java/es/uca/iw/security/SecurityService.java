package es.uca.iw.security;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;

import es.uca.iw.ejemplo.user.User;
import es.uca.iw.ejemplo.user.UserRepository;

@Component
public class SecurityService {

	private static final String LOGOUT_SUCCESS_URL = "/";
	private UserRepository userRepository;

	public SecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> getAuthenticatedUser() {
		if (getAuthentication().isPresent()) {
			return userRepository.findByUsername(getAuthentication().get().getName());
		} else {
			return Optional.empty();
		}
	}

	private Optional<Authentication> getAuthentication() {
		SecurityContext context = SecurityContextHolder.getContext();
		return Optional.ofNullable(context.getAuthentication())
				.filter(authentication -> !(authentication instanceof AnonymousAuthenticationToken));
	}

	public void logout() {
		UI.getCurrent().getPage().setLocation(LOGOUT_SUCCESS_URL);
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
	}
}