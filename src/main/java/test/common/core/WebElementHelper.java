package test.common.core;


import static test.common.config.Constants.WAIT_SHORT_SECONDS;
import static org.junit.Assert.fail;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebElementHelper {
	
    public static boolean isElementDisplayed(WebElement webElement) throws Exception {
        return isElementDisplayed(webElement, WAIT_SHORT_SECONDS);
    }

    public static boolean isElementDisplayed(WebElement webElement, int timeOut) throws Exception {
        try {
            WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), timeOut);
            wait.until(ExpectedConditions.visibilityOf(webElement));
            return webElement.isDisplayed();
        } catch (NoSuchElementException | TimeoutException ne) {
            return false;
        } catch (StaleElementReferenceException ex) {
            return isElementDisplayed(webElement, timeOut);
        }
    }

    public static void waitForVisibility(WebElement element) throws Exception {
        WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);
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

    public static void sendKeys(WebElement webElement, CharSequence... keysToSend) throws Exception {
        WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);
        wait.until(ExpectedConditions.visibilityOf(webElement));
        webElement.clear();
        webElement.sendKeys(keysToSend);
    }

    public static void click(WebElement webElement) throws Exception {
        isElementDisplayed(webElement);
        waitForElementToBeClickable(webElement);
        webElement.click();
    }

    public static void click(By webElement) throws Exception {
        waitForElementToBeClickable(webElement).click();
    }

    public static void selectByVisiableText(WebElement webElement, String value) {
        Select select = new Select(webElement);
        select.selectByVisibleText(value);
    }

    public static void waitForElementToBeClickable(WebElement webElement) throws Exception {
        WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(webElement));

    }

    public static WebElement waitForElementToBeClickable(By webElement) throws Exception {
        WebDriverWait wait = new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS);
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static String getText(WebElement webElement) throws Exception {
        waitForVisibility(webElement);
        return webElement.getText();
    }

    public static String getValue(WebElement webElement) throws Exception {
        waitForVisibility(webElement);
        return webElement.getAttribute("value");
    }

    public static void navigateToPage(String url) throws Exception {
    	log.info("Navigating to: " + url);
        DriverBase.getDriver().get(url);
    }

    public static void scrollToCenterOfScreen(WebElement webElement) throws Exception {
        String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                + "var elementTop = arguments[0].getBoundingClientRect().top;"
                + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

        ((JavascriptExecutor) DriverBase.getDriver()).executeScript(scrollElementIntoMiddle, webElement);
    }

    public static void setColorForDebug(WebElement element) throws Exception {

        JavascriptExecutor js = (JavascriptExecutor) DriverBase.getDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background-color:coral')", element);
    }

    public static void clickWithOffset(WebElement element) throws Exception {
        Actions builder = new Actions(DriverBase.getDriver());
        Action action = builder.moveToElement(element, 2, 2).click().build();
        action.perform();
    }

    public static void waitForCookie(String cookieName) throws Exception {
        new WebDriverWait(DriverBase.getDriver(), WAIT_SHORT_SECONDS).until(d -> d.manage().getCookieNamed(cookieName) != null);
    }

    public static boolean isElementPresentByLocator(By locator) throws Exception {
        return DriverBase.getDriver().findElements(locator).size() != 0;
    }

    public static void waitUntilElementToDisappear(By by, int timeout) throws Exception {
        WebDriverWait webDriverWait = new WebDriverWait(DriverBase.getDriver(), timeout);
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static String getElementColor(WebElement element) throws Exception {
        waitForVisibility(element);
        return element.getCssValue("color");
    }

}
