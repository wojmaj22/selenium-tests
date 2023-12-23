package seleniumtests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
//		driver = new ChromeDriver();
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
	}
	
//	@Test
//	@Order(1)
//	public void getMainSite(){
//		// get site
//		driver.get(URL + "/");
//		// tests are to be written here
//	}

	@Test
	public void shouldAddNewOrder(){
		driver.get(URL);

		// Kliknij przycisk o id orders-link
		driver.findElement(By.id("orders-link")).click();

		// Pobierz początkowe id paginacji
		String initialPaginationId = driver.findElement(By.className("order__pagination")).getAttribute("id");
		int initialPaginationIdAsInt = Integer.parseInt(initialPaginationId);

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();

		// Kliknij pierwszy przycisk o klasie add__button
		driver.findElement(By.className("add__button")).click();

		// Kliknij przycisk o id cart-icon
		driver.findElement(By.id("cart-icon")).click();

		// Kliknij przycisk o id create-order
		driver.findElement(By.id("create-order")).click();

		// Sprawdź, czy przycisk o id orders-link jest widoczny
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orders-link")));

		// Kliknij przycisk o id orders-link dwukrotnie
		driver.findElement(By.id("orders-link")).click();
		driver.findElement(By.id("orders-link")).click();

		// Sprawdź, czy paginacja jest widoczna
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("order__pagination")));

		// Pobierz zaktualizowane id paginacji
		String updatedPaginationId = driver.findElement(By.className("order__pagination")).getAttribute("id");
		int updatedPaginationIdAsInt = Integer.parseInt(updatedPaginationId);

		// Sprawdź, czy zaktualizowane id jest większe o 1 od początkowego
		int expectedUpdatedId = initialPaginationIdAsInt + 1;
		Assertions.assertEquals(expectedUpdatedId, updatedPaginationIdAsInt, "Updated pagination id is not as expected.");
	}

	@Test
	public void shouldAddNewProduct() {
		// Otwórz stronę
		driver.get(URL);

		// Pobierz początkowe id paginacji
		String initialPaginationId = driver.findElement(By.className("product__pagination")).getAttribute("id");
		int initialPaginationIdAsInt = Integer.parseInt(initialPaginationId);

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij przycisk o klasie addLink
		driver.findElement(By.className("addLink")).click();

		// Wpisz wartość 'newproduct' do pola o id name-input
		driver.findElement(By.id("name-input")).sendKeys("newproduct");

		// Wpisz wartość '20' do pola o id stockQuantity-input
		driver.findElement(By.id("stockQuantity-input")).sendKeys("20");

		// Wpisz wartość '50.89' do pola o id price-input
		driver.findElement(By.id("price-input")).sendKeys("50.89");

		// Kliknij przycisk o id form-submit-button
		driver.findElement(By.id("form-submit-button")).click();

		// Sprawdź, czy przycisk o id home-link jest widoczny
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("home-link")));

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();

		// Sprawdź, czy paginacja jest widoczna
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product__pagination")));

		// Pobierz zaktualizowane id paginacji
		String updatedPaginationId = driver.findElement(By.className("product__pagination")).getAttribute("id");
		int updatedPaginationIdAsInt = Integer.parseInt(updatedPaginationId);

		// Sprawdź, czy zaktualizowane id jest większe o 1 od początkowego
		int expectedUpdatedId = initialPaginationIdAsInt + 1;
		Assertions.assertEquals(expectedUpdatedId, updatedPaginationIdAsInt, "Updated pagination id is not as expected.");

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij pierwszy przycisk o klasie delete-admin-item-button
		driver.findElement(By.className("delete-admin-item-button")).click();
	}

	@Test
	public void shouldAddItemsToCart() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij pierwszy przycisk o klasie add__button
		driver.findElement(By.className("add__button")).click();

		// Sprawdź, czy przycisk o id cart-icon jest widoczny
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-icon")));

		// Kliknij przycisk o id cart-icon
		driver.findElement(By.id("cart-icon")).click();

		// Sprawdź, czy element o id cart-product istnieje
		WebElement cartProduct = driver.findElement(By.id("cart-product"));
		Assertions.assertNotNull(cartProduct, "Cart product does not exist.");
	}

	@Test
	public void shouldDisplayNumberOfItemsAddedToCart() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij pierwszy przycisk o klasie add__button trzy razy
		for (int i = 0; i < 3; i++) {
			driver.findElement(By.className("add__button")).click();
		}

		// Poczekaj, aż liczba elementów w koszyku zostanie zaktualizowana
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement cartItemsCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart__items__count")));

		// Sprawdź, czy liczba elementów w koszyku to ' 3 '
		Assertions.assertEquals("3", cartItemsCount.getText().trim(), "Incorrect number of items in the cart.");
	}

	@Test
	public void shouldDeleteProduct() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij przycisk o klasie addLink
		driver.findElement(By.cssSelector(".addLink")).click();

		// Wprowadź nazwę, ilość i cenę produktu
		driver.findElement(By.id("name-input")).sendKeys("testproduct");
		driver.findElement(By.id("stockQuantity-input")).sendKeys("20");
		driver.findElement(By.id("price-input")).sendKeys("50.89");

		// Kliknij przycisk o id form-submit-button
		driver.findElement(By.id("form-submit-button")).click();

		// Kliknij ponownie przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Pobierz początkowy numer strony paginacji
		String initialPaginationId = driver.findElement(By.cssSelector(".product__pagination")).getAttribute("id");
		int initialPaginationIdAsInt = Integer.parseInt(initialPaginationId);

		// Kliknij pierwszy przycisk o klasie delete-admin-item-button
		driver.findElements(By.cssSelector(".delete-admin-item-button")).get(0).click();

		// Poczekaj na widoczność paginacji
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product__pagination")));

		// Pobierz aktualny numer strony paginacji
		String updatedPaginationId = driver.findElement(By.cssSelector(".product__pagination")).getAttribute("id");
		int updatedPaginationIdAsInt = Integer.parseInt(updatedPaginationId);

		// Sprawdź, czy numer strony paginacji został zmniejszony o 1
		Assertions.assertEquals(updatedPaginationIdAsInt, initialPaginationIdAsInt - 1, "Pagination is not updated after deletion.");
	}

	@Test
	public void shouldDeleteItemFromCart() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij pierwszy przycisk o klasie add__button
		driver.findElement(By.className("add__button")).click();

		// Sprawdź, czy przycisk o id cart-icon jest widoczny
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart-icon")));

		// Kliknij przycisk o id cart-icon
		driver.findElement(By.id("cart-icon")).click();

		// Sprawdź, czy element o id cart-product jest widoczny
		WebElement cartProduct = driver.findElement(By.id("cart-product"));
		Assertions.assertNotNull(cartProduct, "Cart product is not visible.");

		// Kliknij przycisk o id delete-from-cart
		driver.findElement(By.id("delete-from-cart")).click();

		// Sprawdź, czy element o id no-products-in-cart istnieje
		WebElement noProductsInCart = driver.findElement(By.id("no-products-in-cart"));
		Assertions.assertNotNull(noProductsInCart, "No products in cart message is not visible.");
	}

	@Test
	public void previousPageButtonShouldBeDisabled() {
		// Otwórz stronę
		driver.get(URL);

		// Pobierz wartość strony paginacji
		WebElement paginationPage = driver.findElement(By.id("pagination-page"));
		int paginationPageValue = Integer.parseInt(paginationPage.getText().trim());

		// Sprawdź, czy wartość strony paginacji wynosi 1
		Assertions.assertEquals(1, paginationPageValue, "Pagination page value is not 1.");

		// Sprawdź, czy przycisk o id previous-page-button istnieje
		WebElement previousPageButton = driver.findElement(By.id("previous-page-button"));
		Assertions.assertNotNull(previousPageButton, "Previous page button does not exist.");

		// Sprawdź, czy przycisk o id previous-page-button jest wyłączony
		Assertions.assertTrue(!previousPageButton.isEnabled(), "Previous page button is not disabled.");
	}

	@Test
	public void shouldDisableAddingNewProductWhenNameIsTooShort() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij przycisk o klasie addLink
		driver.findElement(By.className("addLink")).click();

		// Wprowadź za krótką nazwę
		driver.findElement(By.id("name-input")).sendKeys("new");

		// Wprowadź ilość
		driver.findElement(By.id("stockQuantity-input")).sendKeys("20");

		// Wprowadź cenę
		driver.findElement(By.id("price-input")).sendKeys("50.89");

		// Sprawdź, czy przycisk o id form-submit-button jest wyłączony
		WebElement formSubmitButton = driver.findElement(By.id("form-submit-button"));
		Assertions.assertFalse(formSubmitButton.isEnabled(), "Form submit button is not disabled.");

		// Sprawdź, czy element o id wrong-name istnieje
		WebElement wrongNameElement = driver.findElement(By.id("wrong-name"));
		Assertions.assertNotNull(wrongNameElement, "Wrong name element does not exist.");
	}

	@Test
	public void shouldNotBeAbleToAddMoreItemsThanQuantity() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij przycisk o klasie addLink
		driver.findElement(By.className("addLink")).click();

		// Wprowadź nazwę
		driver.findElement(By.id("name-input")).sendKeys("newItemForTestingPurposes");

		// Wprowadź ilość
		WebElement quantityInput = driver.findElement(By.id("stockQuantity-input"));
		quantityInput.clear();
		quantityInput.sendKeys("20");

		// Wprowadź cenę
		driver.findElement(By.id("price-input")).sendKeys("50.89");

		// Sprawdź, czy przycisk o id form-submit-button jest aktywny
		WebElement formSubmitButton = driver.findElement(By.id("form-submit-button"));
		Assertions.assertTrue(formSubmitButton.isEnabled(), "Form submit button is not enabled.");

		// Kliknij przycisk o id form-submit-button
		formSubmitButton.click();

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();

		// Sprawdź, czy pierwszy element o klasie product__title ma tekst 'newItemForTestingPurposes'
		WebElement productTitle = driver.findElement(By.cssSelector(".product__title"));
		Assertions.assertEquals("newItemForTestingPurposes", productTitle.getText(), "Product title is not as expected.");

		// Wprowadź ilość jako 20 i sprawdź, czy przycisk o klasie add__button jest wyłączony
		WebElement amountInput = driver.findElement(By.cssSelector(".amount__input"));
		amountInput.clear();
		amountInput.sendKeys("20");

		WebElement addButton = driver.findElement(By.cssSelector(".add__button"));
		addButton.click();

		WebElement amountInputAgain = driver.findElement(By.cssSelector(".amount__input"));
		amountInput.clear();
		amountInput.sendKeys("1");
		Assertions.assertFalse(addButton.isEnabled(), "Add button is not disabled.");

		// Kliknij przycisk o id admin-link
		driver.findElement(By.id("admin-link")).click();

		// Kliknij pierwszy przycisk o klasie delete-admin-item-button
		driver.findElement(By.className("delete-admin-item-button")).click();
	}

	@Test
	public void shouldProperlyAddManyDifferentItemsToCart() {
		// Otwórz stronę
		driver.get(URL);

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();

		// Pobierz nazwę pierwszego produktu
		String firstProductName = driver.findElement(By.cssSelector(".product__title")).getText();

		// Wprowadź ilość jako 2 i kliknij przycisk o klasie add__button
		WebElement amountInput = driver.findElement(By.cssSelector(".amount__input"));
		amountInput.clear();
		amountInput.sendKeys("2");
		driver.findElement(By.cssSelector(".add__button")).click();

		// Pobierz nazwę drugiego produktu
		String secondProductName = driver.findElements(By.cssSelector(".product__title")).get(1).getText();

		// Wprowadź ilość jako 3 i kliknij przycisk o klasie add__button
		amountInput = driver.findElements(By.cssSelector(".amount__input")).get(1);
		amountInput.clear();
		amountInput.sendKeys("3");
		driver.findElements(By.cssSelector(".add__button")).get(1).click();

		// Sprawdź, czy liczba produktów w koszyku wynosi 5
		WebElement cartItemsCount = driver.findElement(By.cssSelector(".cart__items__count"));
		Assertions.assertEquals("5", cartItemsCount.getText(), "Cart items count is not as expected.");

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();

		// Sprawdź informacje o pierwszym produkcie w koszyku
		checkCartItemInformation(firstProductName, 2, 0);

		// Sprawdź informacje o drugim produkcie w koszyku
		checkCartItemInformation(secondProductName, 3, 1);
	}

	private void checkCartItemInformation(String productName, Integer quantity, int index) {
		// Kliknij przycisk o klasie cart__button
		driver.findElement(By.cssSelector(".cart__button")).click();

		// Sprawdź, czy nagłówek produktu w koszyku ma oczekiwaną nazwę
		WebElement cartItemHeader = driver.findElements(By.cssSelector(".cart__item__header")).get(index);
		Assertions.assertEquals(productName, cartItemHeader.getText(), "Cart item header is not as expected.");

		// Sprawdź, czy ilość produktu w koszyku jest zgodna z oczekiwaniami
		WebElement quantityElement = driver.findElements(By.cssSelector(".quantity")).get(index);
		Assertions.assertEquals(quantity.toString(), quantityElement.getText(), "Quantity is not as expected.");

		// Kliknij przycisk o id home-link
		driver.findElement(By.id("home-link")).click();
	}
}
