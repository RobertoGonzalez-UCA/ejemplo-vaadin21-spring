package es.uca.iw.ejemplo.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Activate User")
@Route(value = "useractivation")
public class UserView extends HorizontalLayout {

	private static final long serialVersionUID = 851217309689685413L;

	UserService service;

	private TextField emailField;
	private TextField keyField;
	private Button activateButton;
	private H4 statusText;

	public UserView(UserService service) {
		this.service = service;
		setMargin(true);

		emailField = new TextField("Your email");
		emailField.setId("emailField");
		keyField = new TextField("Your secret code");
		keyField.setId("keyField");
		activateButton = new Button("Activate");
		activateButton.setId("activateButton");
		statusText = new H4("User status");
		statusText.setId("statusText");
		statusText.setVisible(false);
		add(emailField, keyField, activateButton, statusText);
		setVerticalComponentAlignment(Alignment.END, emailField, keyField, activateButton);
		activateButton.addClickListener(e -> {
			Notification.show(
					"Activando usuario con email: " + emailField.getValue() + " con código " + keyField.getValue());
			if (service.activateUser(emailField.getValue(), keyField.getValue())) {
				statusText.setText("User is activated");
			} else {
				statusText.setText("User is not activated");
			}
			statusText.setVisible(true);
		});
	}

}
