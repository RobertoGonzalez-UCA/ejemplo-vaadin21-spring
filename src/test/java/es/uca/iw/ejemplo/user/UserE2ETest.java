package es.uca.iw.ejemplo.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author ivanruizrube
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserE2ETest {

	@LocalServerPort
	private int port;

	private String uribase = "http://localhost:";

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		WebDriverManager.chromedriver().setup();
	}

	@Before
	public void setUp() {

		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);

	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

	

	@Test
	public void shouldNotActivateANotExistingUser() {

		// HTTP web invocation
		driver.get(uribase + port + "/useractivation");

		// user interaction
		driver.findElement(By.id("emailField")).sendKeys("user@uca.es");
		driver.findElement(By.id("keyField")).sendKeys("key");
		driver.findElement(By.id("activateButton")).click();

		// Assertion
		WebElement element = new WebDriverWait(driver, 3).until(driver -> driver.findElement(By.id("statusText")));

		assertThat(element.getText()).isEqualTo("User is not activated");

	}
}
