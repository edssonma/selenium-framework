package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class WebSiteFunctions {

	WebDriver driver;
	static ExplicitWaitFunctions ewaits = new ExplicitWaitFunctions();
	private final Logger log = LogManager.getLogger(this.getClass().getName());

	public WebSiteFunctions(WebDriver driver) {
		this.driver = driver;
	}

	/***
	 * Move to outer/parent frame .
	 */
	public void frameOut() {
		try {
			driver.switchTo().defaultContent();
			log.debug("switched to defaultContent");
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 
	 * This method Switch to Frame based on WebElement and With a wait
	 * 
	 * @param webElement
	 * @param timeout
	 */
	public void switchToFrame(WebElement webElement, int timeout) {
		Assert.assertTrue(ewaits.waitForFrameAndSwitchToIt(webElement, timeout));
		log.debug("Switched to the Frame: " + webElement.toString());
	}

	/**
	 * 
	 * This method Switch to Frame based on WebElement
	 * 
	 * @param webElement
	 * @throws InterruptedException 
	 */
	public void switchToFrame(WebElement webElement) throws InterruptedException {
		driver.switchTo().frame(webElement);
		log.debug("Switched to the Frame: "+ webElement.toString() );
	}
	
	/**
	 * 
	 * This method Switch to Frame based on WebElement with thread sleep
	 * 
	 * @param webElement
	 * @param timeout (example: 3000 = 3 seconds)
	 * @throws InterruptedException 
	 */
	public void switchToFrameWithSleep(WebElement webElement, int timeout) throws InterruptedException {
		Thread.sleep(timeout);
		driver.switchTo().frame(webElement);
		log.debug("Switched to the Frame: "+ webElement.toString() );
	}

	/**
	 * 
	 * This method Switch to Frame based on WebElement
	 * 
	 * @param frameName
	 */
	public void switchToFrame(String frameName) {
		driver.switchTo().frame(frameName);
		log.debug("Switched to the Frame: " + frameName);
	}

	/***
	 * Reload web page and move to the inner frame
	 */
	public void reloadPage() {
		try {
			Thread.sleep(1000);
			driver.navigate().refresh();
			log.debug(" - Site reloaded");

		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}

	}

	/**
	 * Navigates to the previous page (Click "Back" function)
	 * 
	 */
	public void goBackHomePage() {
		try {
			driver.navigate().back();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Does the action of Mouse Hover over a WebElement.
	 * 
	 * @param webEl
	 */
	public void mousehover(WebElement webEl) {
		Actions hover = new Actions(driver);
		hover.moveToElement(webEl);
		hover.build();
		hover.perform();

	}
}
