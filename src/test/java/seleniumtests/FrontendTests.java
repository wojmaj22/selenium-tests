package seleniumtests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

@DisplayName("Selenium tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FrontendTests {
	
	private static WebDriver driver;
	
	// base URL for site we are testing
	private final String URL = "http://localhost:4200";
	
	@BeforeAll
	public static void setupAll(){
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	public void setupEach(){
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
	@Test
	@Order(1)
	public void getMainSite(){
		// get site
		driver.get(URL + "/");
		// tests are to be written here
	}
}
