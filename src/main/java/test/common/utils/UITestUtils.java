package test.common.utils;

import static test.common.config.ApplicationProperties.ApplicationProperty.BROWSER;
import static test.common.config.Constants.WAIT_LONG_SECONDS;
import static test.common.config.Constants.WAIT_NORMAL_SECONDS;
import static test.common.config.Constants.WAIT_SHORT_SECONDS;
import static test.common.config.Constants.WAIT_TOO_LONG_SECONDS;
import static org.junit.Assert.fail;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;
import test.common.config.ApplicationProperties;
import test.common.core.DriverBase;

@Slf4j
public class UITestUtils {

	public static Properties CONFIG;
	private static final String browser = ApplicationProperties.getString(BROWSER).toUpperCase();

	// Generate random alphanumeric value depending on the length given as
	// paramater
	public static String randomAlphanumeric(int len) {
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@$&()_-=~,";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public static void clearField(WebElement username, String elemName) throws Exception {

		log.debug("Clearing field : " + elemName);
		try {
			// Wait for the input-box to load on the page
			waitForElementToLoad(username, elemName);

			// Clear the input-box
			username.clear();

			// Log result
			log.info("PASS :Cleared : '" + elemName + "'");

		} catch (org.openqa.selenium.NoSuchElementException clearFieldException) {
			log.error("Error while clearing - '" + elemName + "' : " + clearFieldException.getMessage());
			throw new RuntimeException(
					"Error while clearing - '" + elemName + "' : " + clearFieldException.getClass().getName());

		}
	}

	public static int randomNumberGenerator(int min, int max) {
		Random random = new Random();
		return random.ints(min, max).findFirst().getAsInt();
	}

	public static void waitForPageLoad() throws Exception {
		Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					return String.valueOf(
							((JavascriptExecutor) DriverBase.getDriver()).executeScript("return document.readyState"))
							.equals("complete");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public void clickEnter(WebElement webElement, String elemName) throws Exception {
		log.debug("Clicking on : " + elemName);
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webElement, elemName);

			webElement.sendKeys(Keys.RETURN);

			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");

		} catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());
			throw new RuntimeException("Error while clicking on - '" + elemName + " " + webElement + "' : "
					+ clickLinkException.getClass().getName());

		}
	}

	public static void clickLink(WebElement webElement, String elemName) throws Exception {
		log.debug("Clicking on : " + elemName);
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webElement, elemName);

			webElement.click();
			/*
			 * JavascriptExecutor executor = (JavascriptExecutor) driver;
			 * executor.executeScript("arguments[0].click()", webElement);
			 */

			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");

		} catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());
			throw new RuntimeException("Error while clicking on - '" + elemName + " " + webElement + "' : "
					+ clickLinkException.getClass().getName());

		}
	}

	public static void clickLinkNew(WebElement webElement, String elemName) throws Exception {
		log.debug("Clicking on : " + elemName);
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webElement, elemName);

			webElement.click();

			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");

		} catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());

			throw new RuntimeException(
					"FAIL : Error while clicking on - '" + elemName + "' : " + clickLinkException.getClass().getName());
		}

	}

	public static void clickLink_JavaScript(WebElement webelement, String elemName) throws Exception {
		log.debug("Clicking on : " + elemName);
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webelement, elemName);

			JavascriptExecutor executor = (JavascriptExecutor) DriverBase.getDriver();
			executor.executeScript("arguments[0].click()", webelement);

			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");

		} catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());

			throw new RuntimeException(
					"FAIL : Error while clicking on - '" + elemName + "' : " + clickLinkException.getClass().getName());
		}

	}

	public static void clickLinkUntillExpectedWebElementDisplayed(WebElement webelement, String elemName,
			WebElement expWebElement) throws Exception {
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webelement, elemName);

			for (int i = 1; i <= 3; i++) {
				JavascriptExecutor executor = (JavascriptExecutor) DriverBase.getDriver();
				executor.executeScript("arguments[0].click()", webelement);
				if (isElementDisplayed(expWebElement)) {
					log.info("PASS: Found Expected webElement On the webpage");
					break;
				}

			}
			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");
		}

		catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());

			throw new RuntimeException(
					"FAIL : Error while clicking on - '" + elemName + "' : " + clickLinkException.getClass().getName());
		}

	}

	public static void clickLinkUntillExpectedElementDisplayed(WebElement webElement, String elemName,
			WebElement expWebElement) throws Exception {
		try {
			// Wait for link to appear on the page
			waitForElementToLoad(webElement, elemName);

			for (int i = 1; i <= 3; i++) {
				webElement.click();
				if (isElementDisplayed(expWebElement)) {
					log.info("PASS: Found Expected webElement On the webpage");
					break;
				}

			}
			// Log result
			log.info("PASS :Clicked on : '" + elemName + "'");
		}

		catch (org.openqa.selenium.NoSuchElementException clickLinkException) {
			// Log error
			log.error("Error while clicking on - '" + elemName + " " + clickLinkException.getMessage());

			throw new RuntimeException(
					"FAIL : Error while clicking on - '" + elemName + "' : " + clickLinkException.getClass().getName());
		}

	}

	public static void clearAndInput(WebElement webElement, String elemName, String value) throws Exception {
		try {
			// Wait for the input box to appear on the page
			waitForElementToLoad(webElement, elemName);

			// Clear the input field before sending values
			clearField(webElement, elemName);

			// Send values to the input box
			webElement.sendKeys(value);

			// Log result
			log.info("PASS :Inputted " + value + " text into " + elemName);

		} catch (org.openqa.selenium.NoSuchElementException inputException) {

			// Log error
			log.error("Error while inputting " + value + " into - '" + elemName + "'" + inputException.getMessage());

			throw new RuntimeException("Error while inputting " + value + " into - '" + elemName + "' "
					+ inputException.getClass().getName());

		}
	}

	public static void Input(WebElement webElement, String elemName, String value) throws Exception {
		try {
			// Wait for the input box to appear on the page
			waitForElementToLoad(webElement, elemName);

			// Clear the input field before sending values
			// clearField(locator, elemName);

			// Send values to the input box
			webElement.sendKeys(value);

			// Log result
			log.info("PASS :Inputted " + value + " text into " + elemName);

		} catch (org.openqa.selenium.NoSuchElementException inputException) {

			// Log error
			log.error("Error while inputting " + value + " into - '" + elemName + "'" + inputException.getMessage());

			throw new RuntimeException("Error while inputting " + value + " into - '" + elemName + "' "
					+ inputException.getClass().getName());

		}

	}

	public static void waitForTextToLoad(WebElement webElement, String text) {

		log.debug("Waiting for text to load on the page");
		try {

			// Waits for 30 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_NORMAL_SECONDS);

			wait.until(ExpectedConditions.textToBePresentInElement(webElement, text));

			// Log result
			log.info("Waiting ends ... " + text + " loaded on the page");

		} catch (Exception waitForElementException) {
			// Log error
			log.error("Error came while waiting for " + text + " to appear : " + waitForElementException.getMessage());

			throw new RuntimeException("Error came while waiting for " + text + " to appear : "
					+ waitForElementException.getClass().getName());
		}

	}

	public static void waitForElementToLoad(By locator, String element) {

		log.debug("Waiting for web element to load on the page");
		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);

			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			// Log result
			// log.info("PASS: Web element '"+element+"' loaded on the
			// page");

		} catch (Exception waitForElementException) {
			// Log error
			log.error("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

			throw new RuntimeException("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getClass().getName());

		}

	}

	public static void waitForElementToLoad(WebElement webElement, String element) throws Exception {

		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);

			wait.until(ExpectedConditions.visibilityOf(webElement));

			// Log result
			log.debug("PASS: Waiting ends ... Web element loaded on the page");

		} catch (org.openqa.selenium.InvalidElementStateException waitForElementException) {
			// Log error
			log.error("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

			throw new RuntimeException("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getClass().getName());

		}

	}

	public static void waitForPresenceOfElement(By locator, String element) throws Exception {

		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));

			// Log result
			log.debug("PASS: Waiting ends ... Web element loaded on the page");

		} catch (org.openqa.selenium.InvalidElementStateException waitForElementException) {
			// Log error
			log.error("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

			throw new RuntimeException("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getClass().getName());

		}

	}

	public static void mouseHoverOnElement(WebElement webElement, String elemName) {
		try {
			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			// Mousehover on the webElement
			Actions action = new Actions(DriverBase.getDriver());
			action.moveToElement(webElement).perform();

			// Log result
			log.info("PASS :Mousehoverd on '" + elemName + "'");

		} catch (Exception mouseHoverException) {
			log.error(
					"Error came while Mousehovering on element : " + elemName + " " + mouseHoverException.getMessage());

			throw new RuntimeException("Error came while Mousehovering on element : " + elemName + " "
					+ mouseHoverException.getClass().getName());
		}
	}

	public static void mouseHoverOnWebElemet(WebElement webElement, String elemName) {
		try {
			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			// Mousehover on the webElement
			Actions action = new Actions(DriverBase.getDriver());
			action.moveToElement(webElement).perform();

			// Log result
			log.info("PASS :Mousehoverd on '" + elemName + "'");

		} catch (Exception mouseHoverException) {
			log.error(
					"Error came while Mousehovering on element : " + elemName + " " + mouseHoverException.getMessage());

			throw new RuntimeException("Error came while Mousehovering on element : " + elemName + " "
					+ mouseHoverException.getClass().getName());
		}
	}

	public static void mouseHoverJScript(WebElement webElement) {
		try {
			if (isElementDisplayed(webElement)) {

				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) DriverBase.getDriver()).executeScript(mouseOverScript, webElement);

			} else {
				log.info("Element was not visible to hover " + "\n");
			}
		} catch (StaleElementReferenceException e) {
			log.error("Element " + webElement + " was not found in DOM" + e.getStackTrace());
		} catch (NoSuchElementException e) {
		} catch (Exception e) {
			log.error("Error occurred while hovering" + e.getStackTrace());
		}
	}

	/*
	 * public static boolean isElementPresent(By locator) { boolean flag = false;
	 * WebElement element = driver.findElement(locator); try { if
	 * (element.isDisplayed() || element.isEnabled()) flag = true; } catch
	 * (NoSuchElementException e) { flag = false; } catch
	 * (StaleElementReferenceException e) { flag = false; } return flag; }
	 */

	public static void selectValueByVisibleText(WebElement webElement, String Option, String elemName) throws Exception {

		try {
			// Wait for drop-down element to load on the page
			waitForElementToLoad(webElement, elemName);

			// Locate drop-down field
			Select select = new Select(webElement);

			// Select value from drop-down
			select.selectByVisibleText(Option);

			// Log result
			log.info("Pass : Selected '" + Option + "' from : " + elemName);

		} catch (org.openqa.selenium.ElementNotVisibleException selectValueException) {

			log.error("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getMessage());
			throw new RuntimeException("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getClass().getName());

		}

	}
	
	public static void selectValueByVisibleText(By locator, String Option, String elemName) throws Exception {

		try {
			// Wait for drop-down element to load on the page
			//waitForElementToLoad(webElement, elemName);
			// Locate drop-down field
			waitForPresenceOfElement(locator, elemName);
			Select select = new Select(DriverBase.getDriver().findElement(locator));
			
			
			// Select value from drop-down
			select.selectByVisibleText(Option);

			// Log result
			log.info("Pass : Selected '" + Option + "' from : " + elemName);

		} catch (org.openqa.selenium.ElementNotVisibleException selectValueException) {

			log.error("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getMessage());
			throw new RuntimeException("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getClass().getName());

		}

	}

	public void selectValueByIndex(WebElement webElement, int index, String elemName) throws Exception {

		try {
			// Wait for drop-down element to load on the page
			waitForElementToLoad(webElement, elemName);

			// Locate drop-down field
			Select select = new Select(webElement);

			// Select value from drop-down
			select.selectByIndex(index);

			// Select value from drop-down
			select.selectByIndex(index);

			// Log result
			log.info("Pass : Selected '" + index + "' from : " + elemName);

		} catch (org.openqa.selenium.ElementNotVisibleException selectValueException) {

			log.error("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getMessage());
			throw new RuntimeException("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getClass().getName());
		}

	}

	public static List<WebElement> getOptionsList(WebElement webElement, String elemName) throws Exception {

		try {
			// Wait for drop-down element to load on the page
			waitForElementToLoad(webElement, elemName);

			// Locate drop-down field
			Select select = new Select(webElement);

			// Log result

			// log.info("Pass : Selected '" + index + "' from : " +
			// elemName);
			return select.getOptions();

		} catch (org.openqa.selenium.ElementNotVisibleException selectValueException) {

			log.error("FAIL : Error while gettingoptions from - '" + elemName + "' : "
					+ selectValueException.getMessage());
			throw new RuntimeException("FAIL : Error while gettingoptions Value from - '" + elemName + "' : "
					+ selectValueException.getClass().getName());
		}

	}

	/*
	 * public List<WebElement> getWebElementsList(By Locator, String elemName) {
	 * List<WebElement> elementsList = null; try { // Wait for drop-down element to
	 * load on the page //waitForElementToLoad(Locator, elemName);
	 * 
	 * // Locate drop-down field elementsList = driver.findElements(Locator);
	 * 
	 * 
	 * 
	 * } catch (org.openqa.selenium.ElementNotVisibleException selectValueException)
	 * {
	 * 
	 * log.error("FAIL : Error while getting element from - '" + elemName + "' : " +
	 * selectValueException.getMessage()); }
	 * 
	 * return elementsList;
	 * 
	 * }
	 */
	public static void selectValueByOption(WebElement webElement, String value, String elemName) throws Exception {

		try {
			// Wait for drop-down element to load on the page
			waitForElementToLoad(webElement, elemName);

			// Locate drop-down field
			Select select = new Select(webElement);

			// Select value from drop-down
			select.selectByValue(value);

			// Log result
			log.info("Pass : Selected '" + value + "' from : " + elemName);

		} catch (org.openqa.selenium.ElementNotVisibleException selectValueException) {

			log.error("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getMessage());
			throw new RuntimeException("FAIL : Error while Selecting Value from - '" + elemName + "' : "
					+ selectValueException.getClass().getName());
		}

	}
	
	
	public static String getSelectedOptionInListBox(WebElement webElement, String elemName) {

		log.info("Retrieving Text from : " + elemName);

		try {
			

			Select select = new Select(webElement);
			WebElement option = select.getFirstSelectedOption();
			String retrievedText = option.getText();
			
			log.debug("Pass : retrieved text '" + retrievedText + "' from listbox :" + elemName);

			return retrievedText;
		} catch (org.openqa.selenium.NoSuchElementException retrieveTextException) {
			// Log error
			log.error("Error while Getting Text from listbox '" + elemName + "' : " + retrieveTextException.getMessage());
			throw new RuntimeException("FAIL : Error while Getting Text from '" + elemName + "' : "
					+ retrieveTextException.getClass().getName());
		}
	}
	

	public static String retrieveText(WebElement webElement, String elemName) throws Exception {

		log.info("Retrieving Text from : " + elemName);

		try {
			waitForElementToLoad(webElement, elemName);

			// Retrieve text from web element
			String retrievedText = webElement.getText().trim();

			// Log result
			log.debug("Pass : retrieved text '" + retrievedText + "' from webelement :" + elemName);

			return retrievedText;
		} catch (org.openqa.selenium.NoSuchElementException retrieveTextException) {
			// Log error
			log.error("Error while Getting Text from '" + elemName + "' : " + retrieveTextException.getMessage());
			throw new RuntimeException("FAIL : Error while Getting Text from '" + elemName + "' : "
					+ retrieveTextException.getClass().getName());
		}
	}

	public static String retrieveAttributeValue(WebElement webElement, String value, String elemName) {

		log.info("Retrieving Attribute value from : " + elemName);
		String attributeValue = null;
		try {
			
			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			// Get attribute value for the web element
			attributeValue = webElement.getAttribute(value);
			log.info("Pass : retrieved Attribute '" + value + "' value from '" + elemName);

		} catch (Exception retrieveAttributeValueException) {
			log.error("Execption while retrieving the attribute'" + elemName + "' : "
					+ retrieveAttributeValueException.getMessage());

			throw new RuntimeException("FAIL : Error while Getting Attribute '" + value + "' value from '" + elemName
					+ "' : " + retrieveAttributeValueException.getClass().getName());
		}

		return attributeValue;
	}

	public static void rightclickOnWebElement(WebElement eleWebElement, String element) {
		try {
			Actions action = new Actions(DriverBase.getDriver());
			action.contextClick(eleWebElement).sendKeys(Keys.ARROW_DOWN).click();
			action.build().perform();
			log.info("PASS : Sucessfully Right clicked on the :" + element);

		} catch (StaleElementReferenceException e) {
			log.error("FAIL: Element is not attached to the page document " + e.getMessage());
			throw new RuntimeException("Element is not attached to the page document " + e.getClass().getName());
		} catch (NoSuchElementException e) {
			log.error("FAIL: Element " + element + " was not found in DOM " + e.getMessage());
			throw new RuntimeException("Element " + element + " was not found in DOM " + e.getClass().getName());
		} catch (Exception e) {
			log.error("FAIL : Element " + element + " was not clickable " + e.getMessage());
			throw new RuntimeException("Element " + element + " was not clickable " + e.getClass().getName());
		}
	}

	public static boolean isClickable(WebElement webElement, String element) {
		try {
			WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
			wait.until(ExpectedConditions.elementToBeClickable(webElement));
			log.info("Element " + element + " was clickable ");
			return true;
		} catch (Exception e) {
			log.info("Element " + element + " was not clickable ");
			return false;
		}
	}

	// add log
	public static boolean isElementDisplayed(WebElement webElement) {

		try {
			return webElement.isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementDisplayed(By locator) throws Exception {

		try {
			return DriverBase.getDriver().findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	// Add logs
	public static boolean elementInVisibilitymethod(By element) {
		try {
			WebDriverWait wait_Tools = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
			wait_Tools.until(ExpectedConditions.invisibilityOfElementLocated(element));
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}

	}

	public static void elementInVisibilitymethod(WebElement element) throws Exception {

		WebDriverWait wait_Tools = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
		wait_Tools.until(ExpectedConditions.invisibilityOf(element));

	}

	public static void deleteAllFilesInDownloads() {
		String downloadPath = System.getProperty("user.home");
		File filePath = new File(downloadPath + "\\Downloads\\");
		try {
			FileUtils.cleanDirectory(filePath);
		} catch (IOException e) {
			throw new RuntimeException(filePath + " is not found");
		}

	}

	public static boolean FileExistanceValidation(String Filename, String Extension) throws InterruptedException {

		String downloadPath = System.getProperty("user.home");
		File filePath = new File(downloadPath + "\\Downloads\\" + Filename + Extension);
		log.info(Filename + Extension + " " + filePath.getName());
		int x = 10;

		do {
			if (filePath.exists()) {
				log.info("Is File Downloaded :" + filePath.isFile() + " Download path is " + filePath.getPath());
				log.info("PASS: " + Filename + Extension + " is in Form of an " + Extension);
				return true;
			}
			x++;

		} while (x < 20);

		log.error(Filename + Extension + " not found in path " + filePath.getPath());
		return false;
	}

	public static void FileDelete(String Filename) {

		String downloadPath = System.getProperty("user.home");
		File filePath = new File(downloadPath + "\\Downloads\\" + Filename);
		File filePathDownload = new File(downloadPath + "\\Downloads\\");

		File[] ListOfFiles = filePathDownload.listFiles();

		if (filePath.exists())
			filePath.delete();
		for (int i = 0; i < ListOfFiles.length; i++) {
			String name = ListOfFiles[i].getName();
			if (name.contains(Filename))

			{
				log.info(name + "is being delted");
				ListOfFiles[i].delete();
				log.info("****** " + name + " deleted from the " + ListOfFiles[i].getParent());
			}

		}
	}

	public static void switchToFrame(By frame) throws Exception {

		WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_TOO_LONG_SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated(frame));
		try {
			DriverBase.getDriver().switchTo().frame(DriverBase.getDriver().findElement(frame));
			log.info("PASS :Switching to iFrame");
			sleep(3);
		} catch (WebDriverException e) {
			log.info("Execption while switching to iFrame,trying again to switch..");
			try {
				DriverBase.getDriver().switchTo().frame(DriverBase.getDriver().findElement(frame));
				sleep(3);
			} catch (WebDriverException frameException) {
				log.error("FAIL: Exception while swithcing to iFrame :" + frameException.getMessage());
				throw new RuntimeException(
						"FAIL: Exception while swithcing to iFrame :" + frameException.getClass().getName());
			}
		}
	}

	public static void switchbackToDefault() throws Exception {
		try {
			DriverBase.getDriver().switchTo().defaultContent();
			sleep(5);
			log.info("PASS :Swithched to Default ");
		} catch (WebDriverException e) {
			log.error("FAIL: Execption while switching to default frame :" + e.getMessage());
			throw new RuntimeException("FAIL: Exception while swithcing to default frame :" + e.getClass().getName());
		}
	}

	public static void clickBrowserBackArrow() {
		try {
			DriverBase.getDriver().navigate().back();
			log.info("User clicked on Browser back arrow");
		} catch (Exception e) {
			log.error("ERROR while clicking on back button");
		}

	}

	public static void clickBrowserFrontArrow() {
		try {
			DriverBase.getDriver().navigate().forward();
			log.info("User clicked on Browser Front arrow");
		} catch (Exception e) {
			log.error("ERROR while clicking on Front button");
		}
	}

	public static void refreshPage() {

		try {
			DriverBase.getDriver().navigate().refresh();
			Thread.sleep(10000);
			log.info("User refreshed the page");
		} catch (Exception e) {
			log.error("ERROR while clicking on Front button");
		}
	}

	public static void certifactionHandeler() throws Exception, InterruptedException {
		int incr = 10;
		if (UITestUtils.switchWindow(Apps.SITE_NOT_SECURE.toString())
				|| UITestUtils.switchWindow(Apps.CERTIFICATE_ERROR.toString())) {
			while ((DriverBase.getDriver().getTitle().equals(Apps.SITE_NOT_SECURE.toString())
					|| DriverBase.getDriver().getTitle().equals(Apps.CERTIFICATE_ERROR.toString())) && incr < 15) {
				DriverBase.getDriver().navigate().to("javascript:document.getElementById('overridelink').click()");
				incr++;
			}
		}
		// Don't remove below statement
		DriverBase.getDriver().manage().window().maximize();
	}

	public static void launchApplication(String url) {
		try {
			DriverBase.getDriver().get(url);
			if (browser.equals("IE") || browser.equals("FIREFOX")) {
				certifactionHandeler();
				 if (DriverBase.getDriver().getTitle().equals("This site isn’t secure") ||
				 DriverBase.getDriver().getTitle().equals("Certificate Error: Navigation Blocked")) {
				
				 DriverBase.getDriver().navigate().to("javascript:document.getElementById('overridelink').click()");
				 }
			}
			log.info("Launched application url :" + url);
		} catch (Exception e) {
			log.error("Exception while launching the browser");
		}

	}

	public static void takeSnapShot(String scenarioName) throws Exception {

		DateFormat df = new SimpleDateFormat("ddMMyyHHmmss");
		Date dateobj = new Date();
		String filename = scenarioName.replaceAll("\"", "");
		filename = filename.replaceAll("\\s+", "_");
		filename = filename + df.format(dateobj) + ".jpg";
		TakesScreenshot scrShot = ((TakesScreenshot) DriverBase.getDriver());
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File("screenshots/" + filename);
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			log.error("Path or File not found: " + "./screenshots/" + filename);
		}
	}

	public static void closeDriver() {
		try {
			if (DriverBase.getDriver() != null) {
				DriverBase.getDriver().close();
			}
		} catch (Exception e) {
			log.error("Exception while closing the driver");
		}
	}

	public static void closeTab(String title) {
		try {
			switchWindow(title);
			if (DriverBase.getDriver() != null) {
				DriverBase.getDriver().close();
			}
		} catch (Exception e) {
			log.error("Exception while closing the driver");
		}
	}

	public static void sleep(int seconds, String message) {
		seconds = 1000 * seconds;
		try {
			Thread.sleep(seconds);
			log.info(message);
		} catch (InterruptedException e) {
			log.error("Exception while Thread Sleep");
		}

	}

	public static void sleep(int seconds) {
		seconds = 1000 * seconds;
		try {
			Thread.sleep(seconds);
			log.info("Waited for " + seconds / 1000 + " seconds");
		} catch (InterruptedException e) {
			log.error("Exception while Thread Sleep");
		}

	}

	public static boolean verifyIsElementDisplayed(WebElement webElement, String elementName) throws Exception {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_NORMAL_SECONDS);
			wait.until(ExpectedConditions.visibilityOf(webElement));
			log.info("PASS : " + elementName + " displayed");
			status = true;
		} catch (AssertionError error) {
			status = false;
			log.error("FAIL :  " + elementName + " not displayed");
		}
		return status;
	}

	public static void WaitForElementToBeInVisible(By element, String elementName) {
		WaitForElementToBeInVisible(element, elementName,WAIT_LONG_SECONDS );

	}
	
	public static void WaitForElementToBeInVisible(By element, String elementName,int maxTimeout) {
		try {
			WebDriverWait wait_Tools = new WebDriverWait(DriverBase.getDriver(), maxTimeout);
			log.info("Waiting for " + elementName + " to dissappear");
			wait_Tools.until(ExpectedConditions.invisibilityOfElementLocated(element));
		} catch (Exception e) {
			log.info(elementName + " is visible");
		}

	}


	public static void WaitForElementToBeInVisible(WebElement element, String elementName) {
		try {
			WebDriverWait wait_Tools = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);
			log.info("Waiting for " + elementName + " to dissappear");
			wait_Tools.until(ExpectedConditions.invisibilityOf(element));
		} catch (Exception e) {
			log.info(elementName + " is visible");
		}

	}

	public static void mouseHoverOnWebElemetAndEnterText(WebElement webElement, String elemName, String value) {
		try {
			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			Actions actions = new Actions(DriverBase.getDriver());
			actions.moveToElement(webElement);
			actions.click();
			log.info("PASS :Clicked on : '" + elemName + "'");

			actions.sendKeys(value);
			log.info("PASS :Sent Txt as: '" + value + "'");
			actions.build().perform();
		} catch (Exception mouseHoverException) {
			log.error("Execption while Entering the text on Field");
		}
	}

	public static void selectAll(WebElement webElement, String elemName, String value) {

		try {
			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			Actions actions = new Actions(DriverBase.getDriver());
			actions.moveToElement(webElement);
			actions.click();
			log.info("PASS :Clicked on : '" + elemName + "'");
			if (browser.equalsIgnoreCase("firefox_local")) {
				actions.keyDown(Keys.CONTROL).sendKeys(String.valueOf('\u0061')).perform();
			} else
				actions.sendKeys(value);
			log.info("PASS :Selected the entire Text: '" + value + "'");
			actions.build().perform();
		} catch (Exception mouseHoverException) {
			log.error("Execption while Selecting the Entire Text");
		}
	}

	public static String getExcelCSVSecondRowData(String filename) throws IOException {
		String downloadPath = System.getProperty("user.home");
		File filePath = new File(downloadPath + "\\Downloads\\" + filename);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		List<String> lines = new ArrayList<>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			lines.add(line);
		}
		return lines.get(1);

	}

	/**
	 * Upload file in IE
	 * 
	 * @return
	 */
	public void uploadFile_IE(String filePath) {
		try {
			Runtime.getRuntime().exec("./HospitalLogoImgUploadNew.exe" + " " + filePath);
		} catch (IOException e) {
			Assert.fail("Given file " + filePath + " does not exist" + e.getMessage());
		}
	}

	/**
	 * This Method checks that none of the tabs are in focus
	 */
	public static void CheckforTabs(String Headernames, WebElement webElement) {
		Assert.assertFalse(isElementDisplayed(webElement));
		log.info("Pass: " + Headernames + " is not in focus");
	}

	/**
	 * This Method disconnects the system out of network
	 */
	public void systemOutOfNetwork() throws IOException {
		log.info("Disconnecting the network");
		Runtime.getRuntime().exec("netsh wlan disconnect");
	}

	/**
	 * This Method reconnects the system to network
	 */
	public void reconnectInXSeconds(int time, String network) throws IOException {
		sleep(time);
		log.info("Reconnecting the network");
		Runtime.getRuntime().exec("netsh wlan connect name=" + network);
	}

	public static void zoomIn() throws AWTException {
		Robot robot = new Robot();

		for (int i = 0; i < 4; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_ADD);
			robot.keyRelease(KeyEvent.VK_CONTROL);
		}
	}

	public static void zoomOut() throws AWTException {
		Robot robot = new Robot();

		for (int i = 0; i < 4; i++) {
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_SUBTRACT);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_SUBTRACT);
		}
	}

	public static void set100() throws Exception { // To set browser to default zoom level 100%
		DriverBase.getDriver().findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL, "0"));
	}

	public static boolean switchWindow(String title) throws Exception {
		WebDriver driver = DriverBase.getDriver();
		// String currentWindow = driver.getWindowHandle();
		Thread.sleep(2000);
		Set<String> availableWindows = driver.getWindowHandles();
		if (!availableWindows.isEmpty()) {
			for (String windowId : availableWindows) {
				String currentTitle = driver.switchTo().window(windowId).getTitle();
				log.debug("Navigated to " + currentTitle + " window");
				if (currentTitle.equals(title)) {
					log.info("Navigated to " + title + " window");
					return true;
				}
			}
		}

		// driver.switchTo().window(currentWindow);

		return false;
	}

	public static void waitForVisibility(WebElement element) throws Exception {
		WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_NORMAL_SECONDS);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (TimeoutException te) {
			log.error(te.getMessage());
			fail("Element '" + element + "' not found after waiting for it's visibility");
		} catch (NoSuchElementException ne) {
			log.error(ne.getMessage());
			fail("Element '" + element + "' not found, unable to locate it in the DOM");
		} catch (Exception e) {
			log.error(e.getMessage());
			fail("Element '" + element + "' not found");
		}
	}

	public static String substractDate(String currentTimeStamp, int days) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		Date date = (Date) formatter.parse(currentTimeStamp);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -days);
		date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		String date1 = format1.format(date);
		return date1;
	}

	public static boolean compareIgnoreCase(String expected, String actual) {
		String actualResult = " Actual Result: ";
		String expResult = " Expected Result: ";
		String msg = actualResult + actual + ", " + expResult + expected + ".";

		log.info(actual.equals(expected) ? "PASS :" + msg : "FAIL :" + msg);
		return StringUtils.equalsIgnoreCase(expected, actual);

	}

	public static By getLocatorByXpath(String dynamicXpath) {

		log.debug("Return locator of xpath " + dynamicXpath);
		return By.xpath(dynamicXpath);
	}

	public static void scrollToElement(WebElement element, String elemName) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) DriverBase.getDriver();
			js.executeScript("window.scrollTo(0," + element.getLocation().x + ")");
			log.info("PASS :Browser window scroll to '" + elemName + "'");
		} catch (Exception scrollToElement) {
			log.error("Error came while Scrolling to element : " + elemName + " " + scrollToElement.getMessage());
			throw new RuntimeException(
					"Error came while Scrolling to element : " + elemName + " " + scrollToElement.getClass().getName());
		}
	}

	public static void scrollIntoViewElement(WebElement element, String elemName) {
		try {
			JavascriptExecutor je = (JavascriptExecutor) DriverBase.getDriver();
			je.executeScript("arguments[0].scrollIntoView(true);", element);
			log.info("PASS :scrolled the page until the  '" + elemName + " is visible");
		} catch (Exception scrollToElement) {
			log.error("Error came while Scrolling to element : " + elemName + " " + scrollToElement.getMessage());
			throw new RuntimeException(
					"Error came while Scrolling to element : " + elemName + " " + scrollToElement.getClass().getName());
		}
	}

	public static void scrollToPageEnd() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) DriverBase.getDriver();
			js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			log.info("PASS :Browser window scroll to ");
		} catch (Exception scrollToElement) {
			log.error("Error came while Scrolling to Page End" + scrollToElement.getMessage());
			scrollToElement.printStackTrace();
			throw new RuntimeException("Error came while Scrolling to Page End" + scrollToElement.getClass().getName());
		}
	}
	
	public static void scrollToPageTop() {
		try {
			JavascriptExecutor js = (JavascriptExecutor) DriverBase.getDriver();
			js.executeScript("window.scrollTo(0, 0)");
			log.info("PASS :Browser window scroll to ");
		} catch (Exception scrollToElement) {
			log.error("Error came while Scrolling to Page End" + scrollToElement.getMessage());
			scrollToElement.printStackTrace();
			throw new RuntimeException("Error came while Scrolling to Page End" + scrollToElement.getClass().getName());
		}
	}

	public static boolean isEnabled(WebElement element, String elemName) {

		try {
			return element.isEnabled();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	public static void dragNDrop(WebElement From, WebElement To, String elemName) {

		try {
			// Wait for web element to load
			waitForElementToLoad(From, "From");
			waitForElementToLoad(To, "To");
			// Mousehover on the webElement
			Actions action = new Actions(DriverBase.getDriver());
			// Action act =
			// action.clickAndHold(from).moveToElement(to).release(to).build();
			// act.perform();
			action.dragAndDrop(From, To).build().perform();
			log.info("PASS :DragnDrop on '" + elemName + "'");

		} catch (Exception dragAndDropException) {
			log.error("Error came while DragnDrop on element : " + elemName + " " + dragAndDropException.getMessage());

			throw new RuntimeException("Error came while DragnDrop on element : " + elemName + " "
					+ dragAndDropException.getClass().getName());
		}
	}

	public static void clearAndInputAndPressEnterKey(WebElement locator, String elemName, String value) throws Exception {
		try {
			// Wait for the input box to appear on the page
			UITestUtils.waitForElementToLoad(locator, elemName);

			// Clear the input field before sending values
			clearField(locator, elemName);

			// Send values to the input box
			locator.sendKeys(value, Keys.ENTER);

			// Log result
			log.info("PASS :Inputted '" + value + "' text into " + elemName + "\tthen pressed enter key from keyboard");

		} catch (org.openqa.selenium.NoSuchElementException inputException) {

			// Log error
			log.error("Error while inputting " + value + " into - '" + elemName + "'" + inputException.getMessage());

			throw new RuntimeException("Error while inputting " + value + " into - '" + elemName + "' "
					+ inputException.getClass().getName());

		}
	}

	public static WebElement getWebElementByLocator(final By locator) throws Exception {

		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_NORMAL_SECONDS);

			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			// Log result
			log.debug("PASS: Waiting ends ... Web element loaded on the page");
			return DriverBase.getDriver().findElement(locator);
		} catch (org.openqa.selenium.InvalidElementStateException waitForElementException) {
			// Log error
			log.error(
					"FAIL: Error came while waiting for element to appear :  " + waitForElementException.getMessage());

			throw new RuntimeException("FAIL: Error came while waiting for element to appear :  "
					+ waitForElementException.getClass().getName());

		}

	}

	public static void popupHandel(String actionValue) throws Exception {
		if (actionValue.equals("ok")) {
			DriverBase.getDriver().switchTo().alert().accept();
		} else {
			DriverBase.getDriver().switchTo().alert().dismiss();
		}
	}

	public static int getNumberOfRowsInExcelSheet(String path) throws Exception {
		XSSFWorkbook workbook = null;
		try {
			
			File file = new File(path).getAbsoluteFile();
			FileInputStream inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int count = sheet.getPhysicalNumberOfRows() - 1;
			return count;
		} catch (Exception e) {
			log.error("Error came while reading from excel sheet, Failed");
			throw new Exception(e);
		} finally {
			workbook.close();
		}
	}

	public static void clickMouseOverAction(WebElement element, String elemnt) {
		try {
			// click on the webElement
			Actions action = new Actions(DriverBase.getDriver());
			action.click(element).perform();

			// Log result
			log.info("PASS :clicked on element");

		} catch (Exception mouseHoverException) {
			log.error("Error came while Mousehovering on element : " + mouseHoverException.getMessage());

			throw new RuntimeException(
					"Error came while Mousehovering on element :  " + mouseHoverException.getClass().getName());
		}
	}

	public static void clickMouseOverAction() {
		try {
			// click on the webElement
			Actions action = new Actions(DriverBase.getDriver());
			action.click().perform();

			// Log result
			log.info("PASS :clicked on element");

		} catch (Exception mouseHoverException) {
			log.error("Error came while Mousehovering on element : " + mouseHoverException.getMessage());

			throw new RuntimeException(
					"Error came while Mousehovering on element :  " + mouseHoverException.getClass().getName());
		}
	}

	public static String getHexValueOfColor(String checkHexValueForColor) {

		String valueForColor = null;
		switch (checkHexValueForColor) {
		case "violet":
			valueForColor = "#631d76";
			break;
		case "blue":
			valueForColor = "#005a8b";
			break;
		case "yellow":
			valueForColor = "#f5f52b";
			break;
		case "grey":
			valueForColor = "#888888";
			break;
		default:
			log.info("Required color is not available for comparison");
		}
		return valueForColor;
	}

	public static String retrieveCssAttributeValue(WebElement webElement, String attribute, String elemName) {

		log.info("Retrieving Attribute value from : " + elemName);
		String attributeValue = null;
		try {

			// Wait for web element to load
			waitForElementToLoad(webElement, elemName);

			// Get attribute value for the web element
			attributeValue = webElement.getCssValue(attribute);
			log.info("Pass : retrieved Attribute '" + attribute + "' value from '" + elemName);

		} catch (Exception retrieveAttributeValueException) {
			log.error("Execption while retrieving the attribute'" + elemName + "' : "
					+ retrieveAttributeValueException.getMessage());

			throw new RuntimeException("FAIL : Error while Getting Attribute '" + attribute + "' value from '"
					+ elemName + "' : " + retrieveAttributeValueException.getClass().getName());
		}

		return attributeValue;
	}

	public static void waitForElementInvisibleIfExist(By locator, String element) {

		log.debug("Waiting for web element to load on the page");
		try {
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);

			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		} catch (Exception waitForElementException) {
			// Log error
			log.debug("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

		}

		try {
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);

			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

		} catch (Exception waitForElementException) {
			// Log error
			log.debug("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

		}

	}

	public static void waitForPresenceOfAllElementsLocated(By locator, String element) {

		log.debug("Waiting for web element to load on the page");
		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_LONG_SECONDS);

			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

			// Log result
			// log.info("PASS: Web element '"+element+"' loaded on the
			// page");

		} catch (Exception waitForElementException) {
			// Log error
			log.error("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

			throw new RuntimeException("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getClass().getName());

		}

	}

	public static WebElement getWebElementByXpath(String dynamicXpath, int maxTimeOut) {

		By locator = getLocatorByXpath(dynamicXpath);
		log.debug("Waiting for the element to be present on the page");
		try {

			// Waits for 30 seconds implicitly until expected condition is
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), maxTimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));

			log.debug("Waiting ends ... element loaded on the page");
			return DriverBase.getDriver().findElement(locator);
		} catch (Exception waitForElementException) {
			log.error("No element is present with locator : " + locator.toString()
					+ waitForElementException.getMessage());

			throw new RuntimeException("No element is present with locator : " + locator.toString()
					+ waitForElementException.getClass().getName());
		}

	}

	public static WebElement getWebElementByXpath(String dynamicXpath) {

		return getWebElementByXpath(dynamicXpath, WAIT_NORMAL_SECONDS);
	}
	
	
	public static List<WebElement> getWebElementsByXpath(String dynamicXpath, int maxTimeOut) {

		By locator = getLocatorByXpath(dynamicXpath);
		log.debug("Waiting for the element to be present on the page");
		try {

			// Waits for 30 seconds implicitly until expected condition is
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), maxTimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));

			log.debug("Waiting ends ... element loaded on the page");
			return DriverBase.getDriver().findElements(locator);
		} catch (Exception waitForElementException) {
			log.error("No element is present with locator : " + locator.toString()
					+ waitForElementException.getMessage());

			throw new RuntimeException("No element is present with locator : " + locator.toString()
					+ waitForElementException.getClass().getName());
		}

	}
	
	public static List<WebElement> getWebElementsByXpath(String dynamicXpath) {

		return getWebElementsByXpath(dynamicXpath, WAIT_NORMAL_SECONDS);
	}

	public static boolean isElementPresent(By locator, String element) {

		log.debug("Waiting for web element to load on the page");
		try {

			// Waits for 60 seconds implicitly until expected condition is
			// matched
			Wait<WebDriver> wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);

			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			return true;
			// Log result
			// log.info("PASS: Web element '"+element+"' loaded on the
			// page");

		} catch (Exception waitForElementException) {
			// Log error
			log.error("FAIL: Error came while waiting for element to appear : " + element + " "
					+ waitForElementException.getMessage());

			return false;

		}

	}
	
	public static String  convertTheDateFormat(String date, String dateformat) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date dt = (Date) formatter.parse(date);
		SimpleDateFormat format1 = new SimpleDateFormat(dateformat);
		return format1.format(dt);
	}}
