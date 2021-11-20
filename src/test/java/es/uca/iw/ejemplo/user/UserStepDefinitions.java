package es.uca.iw.ejemplo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.github.bonigarcia.wdm.WebDriverManager;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserStepDefinitions {

	@LocalServerPort
	private int port;

	private String uribase = "http://localhost:";

	private WebDriver driver;

	@Autowired
	private UserService userService;

	@Before
	public void setUp() {

		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

	}

	
	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	
	@Given("An user with name {string} is registered on the system")
	public void an_user_is_registered_on_the_system(String username) {

		User testUser = new User();
		testUser.setFirstName(username);
		testUser.setLastName(username);
		testUser.setEmail(username + "@uca.es");

		// Given a certain user stored on the repo
		userService.registerUser(testUser);

	
	}

	@When("The user {string} introduces their email {string} and the verification code {string}")
	public void the_user_introduces_their_email_and_the_verification_code(String username, String email, String key) {
		// HTTP web invocation
		driver.get(uribase + port + "/useractivation");

		// user interaction
		driver.findElement(By.id("emailField")).sendKeys(email);
		driver.findElement(By.id("keyField")).sendKeys(key);
		driver.findElement(By.id("activateButton")).click();

	}

	@Then("The user gets a message with the text {string}")
	public void the_user_gets_a_message_with_the_text(String text) {
		// Assertion
		WebElement element = new WebDriverWait(driver, 3).until(driver -> driver.findElement(By.id("statusText")));

		assertThat(element.getText()).isEqualTo(text);

	}

	

}