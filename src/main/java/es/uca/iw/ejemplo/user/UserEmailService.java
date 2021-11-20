package es.uca.iw.ejemplo.user;

import org.springframework.stereotype.Service;

@Service
public class UserEmailService {

	public boolean sendEmail(String email, String subject, String body) {
		
		try {
			System.out.println("Sending email to " + email+ "...");
		    int secondsToSleep=5;
			Thread.sleep(secondsToSleep * 1000);
			System.out.println("Email sent.");
			return true;
		} catch (InterruptedException ie) {
		    Thread.currentThread().interrupt();
		    return false;
		}
	}

}
