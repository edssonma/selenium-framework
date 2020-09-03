package utilities;

/***
 * @author Eduardo Alvarez
 * @Editor Jorge Morales
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavaScriptFunctions {
	WebDriver driver;
	private static final Logger log = LogManager.getLogger(JavaScriptFunctions.class.getName());

	public JavaScriptFunctions(WebDriver driver) {
		this.driver = driver;
	}

	/***
	 * Highlight web element
	 */
	public  void shadeElem(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: #FDFF47; border: 2px solid #000000;');",
				element);
		log.info("Shade element " + element);
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);
	}

	/***
	 * Click a web element
	 */
	public void clickOnElement(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", element);
		log.info("Click on the element " + element);
	}

	/***
	 * Double click a web element
	 */
	public void doubleClickOnElement(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("var evt = document.createEvent('MouseEvents');"+ 
				"evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);"+ 
				"arguments[0].dispatchEvent(evt);", element);
		log.info("Click on the element " + element);
	}

	/***
	 * SendKeys to a web element
	 */
	public void sendKeysOnElementInnerHTML(WebElement element, String text) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		jse.executeScript("arguments[0].tabindex='0';");
		jse.executeScript("arguments[0].contenteditable=true;",element);
//		jse.executeScript("arguments[0].focus();");
		jse.executeScript("arguments[0].innerHTML='" + text + "'", element);
		log.info("Send Text " + text + " to " + element);
	}
	
	/***
	 * SendKeys to a web element
	 * using value
	 */
	public void sendKeysOnElement(WebElement element, String text) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].value='" + text + "';", element);
		log.info("Send Text " + text + " to " + element);
	}

	/***
	 * Scroll / move to a web element
	 */
	public  void scrollToElement(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
		log.info("Scrolling the element " + element + " into view");
	}

	/***
	 * Scroll / move to a web element with no logs
	 */
	public void scrollToElementNoLogs(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView()", element);
	}

	/***
	 * Scrolls to the right side of the page till the Element comes into view.
	 * @param WebElement
	 */
	public void scrollTillElementRight(WebElement element) {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		// Scroll to div's most right:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollRight = arguments[0].scrollWidth", element);

		log.info("Scrolled to element "+ element );

	}

	/***
	 * Scrolls to the left side of the page until the Element comes into view.
	 * @param WebElement
	 */
	public void scrollTillElementLeft(WebElement element) {
		//JavascriptExecutor jse = (JavascriptExecutor) driver;
		// Scroll to div's most right:
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth", element);

		log.info("Scrolled to element "+ element );

	}

	/***
	 * Scrolls to the right side of the page 7000 pixels
	 */
	public void scrollRight() {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(7000,0)");
	}

	/***
	 * Scrolls to the left side of the page 7000 pixels
	 */
	public void scrollLeft() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(-7000,0)");
	}
	
	/***
	 * Scroll up by 250
	 */
	public void scrollUp() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		log.info("Scrolling up");
	}

	/***
	 * Scroll down by 100
	 */
	public void scrollDown() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,100)", "");
		log.info("Scrolling up");
	}

	/***
	 * Scroll till end of the page
	 */
	public void scrollToBottom() {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		log.info("Scrolling till end of the page");
	}
	
	/***
	 * Selects a WebElement by modifying property selected
	 */
	public void selectElementByProperty(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('selected', 'true');",
				element);
		log.info("Set true to the attribute " +  " to " + element);
	}

	/***
	 * Selects a WebElement by modifying property selected
	 */
	public void deselectElementByProperty(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute('selected', 'false');",
				element);
		log.info("Set true to the attribute " +  " to " + element);
	}

	/***
	 * Set Onchange Property on true
	 */
	public void runAttributeScripts(WebElement element, String attribute) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0]."+attribute+";", element);
		log.info("Set true to the attribute " +  " to " + element);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Modify the attribute value of a given WebElement
	 * <br>
	 * E.g: readonly='true' ==> readonly='false'
	 * @param element
	 * @param attName
	 * @param attValue
	 */
	public void setAttribute(WebElement element, String attName, String attValue) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", 
				element, attName, attValue);
	}

	/**
	 * Delete the attribute (if exists) of a given WebElement
	 * @param element
	 * @param attribute
	 */
	public void deleteAttribute(WebElement element, String attribute) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].removeAttribute(arguments[1])", element, attribute);
	}
	
}
