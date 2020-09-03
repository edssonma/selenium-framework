package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;


public class SeleniumActions {
	WebDriver driver;
	Actions actions;
	JavaScriptFunctions jsf;
	private static final Logger log = LogManager.getLogger(SeleniumActions.class.getName());

	public SeleniumActions(WebDriver driver) {
		this.driver = driver;
		jsf = new JavaScriptFunctions(driver);
	    actions = new Actions(driver);
	}

	
	/**
	 * 
	 * This Method Clicks Ctrl+S and then Enter
	 *
	 * @throws Throwable  
	 * 
	 */
	public void saveFileRobot() throws  Throwable {
		Thread.sleep(10000);
		Robot robot = new Robot();
		Thread.sleep(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		log.info("The Click Ctrl+S ");
		//Thread.sleep(5000);
		Thread.sleep(2000);
		robot.keyPress(KeyEvent.VK_ENTER);
		log.info("The Click Enter ");
		Thread.sleep(2000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_S);
		log.info("The release Ctrl+S ");
		robot.keyRelease(KeyEvent.VK_ENTER);
		log.info("The release Enter");
		Thread.sleep(11000);
		log.info("The Click Ctrl+S and Enter ");
	}
	
	/**
	 * 
	 * This Method double clicks on a selected WebElement
	 * 
	 * @param webE
	 */
	public void doubleClickSelectedElement(WebElement webE) {
		actions.keyDown(Keys.LEFT_CONTROL)
	    .click(webE)
	    .keyUp(Keys.LEFT_CONTROL)
	    .doubleClick(webE)
	    .build()
	    .perform();
		log.info("The Double click on the WebElement: "+ webE);
		try {
			Thread.sleep(7000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This Method returns the trimmed text from a WebElement
	 * 
	 * @param webElement
	 * @return string
	 */
	public String getElementText(WebElement webElement) {
		String text = webElement.getText().trim();
		log.info("The text of the WebElement: "+ webElement +" is : "+text);
		return text;
	}
	
	/**
	 * This Method returns the trimmed text from a WebElement with no logs
	 * 
	 * @param webElement
	 * @return string
	 */
	public String getElementTextNoLogs(WebElement webElement) {
		String text = webElement.getText().trim();
		return text;
	}
	
	/**
	 * This Method returns the trimmed text from a textbox WebElement
	 * 
	 * @param webElement
	 * @return string
	 */
	public String getInnerText(WebElement webElement) {
		String text = webElement.getAttribute("value");
		log.info("The text of the WebElement: " + webElement + " is : " + text);
		return text;
	}
	
	/**
	 * 
	 * This Method clicks on a WebElement
	 * 
	 * @param webElement
	 */
	public void clickOnElement(WebElement webElement) {
		webElement.click();
	}

	/**
	 * 
	 * This Method verifies if a radio button WebElement is selected
	 * 
	 * @param webElement
	 * @return boolean
	 */
	public boolean radioButtonIsSelected(WebElement webElement) {
		Boolean isSelected = webElement.isSelected();
		log.info("The WebElement: "+ webElement + " is selected = "+ isSelected );
		return isSelected;		
	}
	
	/**
	 * 
	 * This Method Send a String to a WebElement by clicking first
	 * 
	 * @param webElement
	 * @param text
	 * @throws InterruptedException 
	 */
	public void sendTextToElementWithClick(WebElement webElement, String text) throws InterruptedException {
		webElement.click();
		Thread.sleep(1000);
		webElement.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webElement  );		
	}
	
	/**
	 * 
	 * This Method Send a String to a WebElement
	 * 
	 * @param webElement
	 * @param text
	 * @throws InterruptedException 
	 */
	public void sendTextToElement(WebElement webElement, String text) throws InterruptedException {
		webElement.clear();
		Thread.sleep(1000);
		webElement.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webElement  );		
	}
	
	/**
	 * 
	 * This Method Send a String to a WebElement
	 * 
	 * @param webElement
	 * @param text
	 */
	public void sendTextToElementNoClear(WebElement webElement, String text) {
		webElement.sendKeys(text);
		log.info("Send the text to the WebElement: "+ webElement  );		
	}
	
	
	/**
	 * 
	 * This method returns all the WebElements from a dropdown
	 * 
	 * @param webElement
	 * @return List<WebElement>
	 */
	public List<WebElement> returnOptionsFromDropDown(WebElement webElement) {
		Select dropdown = new Select(webElement);
		List<WebElement> options = dropdown.getOptions();
		return options;
	}
	
	/**
	 * 
	 * This method Selects an option based on the value
	 * 
	 * @param webElement
	 * @param option
	 */
	public  void selectOneOptionFromDropDown(WebElement webElement,String option) {
		Select dropdown = new Select(webElement);
		dropdown.selectByValue(option);
		log.info("The option: "+ option + " was selected from the dropdown: "+webElement);
	}
	
	/**
	 * 
	 * This method Selects an option based on the Visible Text
	 * 
	 * @param webElement
	 * @param option
	 */
	public  void selectOneVisibleTextDropDown(WebElement webElement,String option) {
		Select dropdown = new Select(webElement);
		dropdown.deselectByVisibleText(option);
		log.info("The option: "+ option + " was selected from the dropdown: "+webElement);
	}
	

	/**
	 * 
	 * This method returns all the Options converted to Strings from a dropdown
	 * 
	 * @param webElement
	 * @return List<String>
	 */
	public List<String> returnOptionsFromDropDownString(WebElement webElement) {
		Select dropdown = new Select(webElement);
		List<WebElement> options = dropdown.getOptions();
		List<String> optionsString = new ArrayList<String>();
		for (int i = 0; i <= options.size() - 1; i++) {
			String optString = options.get(i).getText();
			optionsString.add(i, optString);
		}
		return optionsString;
	}
	
	
	public String concaStringList(List<String> list, String delimiter) {
		String conStr = String.join(delimiter, list);
		log.info("The String is : " + conStr);
		return conStr;
	}
	
	public List<String> getTheTextFromAListWebElements(List<WebElement> list) {
		List<String> optionsString = new ArrayList<String>();
		for (int i = 0; i <= list.size() - 1; i++) {
			String str = getElementTextNoLogs(list.get(i));
			optionsString.add(i, str);
		}
		return optionsString;
	}
	
	public List<String> getTheTextFromASelectedWebElements(List<WebElement> list) {
		List<String> optionsString = new ArrayList<String>();
		int j=0;
		for (int i = 0; i <= list.size() - 1; i++) {
			if (list.get(i).isSelected()) {
				String str = getElementTextNoLogs(list.get(i));
				optionsString.add(j, str);
				j++;
			}
		}
		return optionsString;
	}

}
