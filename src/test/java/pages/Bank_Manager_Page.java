package pages;

import static utilities.Constants.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import base.PageBase;
import utilities.ExplicitWaitFunctions;
import utilities.JavaScriptFunctions;
import utilities.SeleniumActions;
import utilities.UsefulMethods;
import utilities.WebSiteFunctions;

public class Bank_Manager_Page extends PageBase {

	public Bank_Manager_Page(WebDriver driver) {
		super(driver, Bank_Manager_Page.class.getName());
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[contains(text(),'Add Customer')]")
	private WebElement addCustomerBtn;

	@FindBy(xpath = "//button[contains(text(),'Open Account')]")
	private WebElement openAccountBtn;

	@FindBy(xpath = "//button[contains(text(),'Customers')]")
	private WebElement customersBtn;

	@FindBy(xpath = "//input[@placeholder='First Name']")
	private WebElement firstNameInput;

	@FindBy(xpath = "//input[@placeholder='Last Name']")
	private WebElement lastNameInput;

	@FindBy(xpath = "//input[@placeholder='Post Code']")
	private WebElement postCodeInput;

	@FindBy(xpath = "//button[contains(text(),'Add Customer')][@type='submit']")
	private WebElement addCustomerSubmitBtn;

	// -------------------------------------------------------------------------------------

	/*
	 * This method clicks the add customer button
	 */
	public void clickAddCustomerButton() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(addCustomerBtn, MID_WAIT));
		jsf.shadeElem(addCustomerBtn);
		log.info("Add Customer button is displayed");
		selact.clickOnElement(addCustomerBtn);
		log.info("Add Customer button has been clicked");
	}

	/*
	 * This method clicks the add open account button
	 */
	public void clickOpenAccountButton() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(openAccountBtn, MID_WAIT));
		jsf.shadeElem(openAccountBtn);
		log.info("Open Account button is displayed");
		selact.clickOnElement(openAccountBtn);
		log.info("Open Account button has been clicked");
	}

	/*
	 * This method clicks the add open account button
	 */
	public void clickCustomersButton() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(customersBtn, MID_WAIT));
		jsf.shadeElem(customersBtn);
		log.info("Customer button is displayed");
		selact.clickOnElement(customersBtn);
		log.info("Customer button has been clicked");
	}

	/*
	 * This method adds new customer
	 */
	public void addNewCustomer(String firstName, String lastName, String postalCode, String alertText)
			throws InterruptedException {
		Assert.assertTrue(waits.waitForVisibilityOfElement(firstNameInput, MID_WAIT));
		jsf.shadeElem(firstNameInput);
		log.info("First Name input is displayed");
		selact.sendTextToElement(firstNameInput, firstName);
		log.info("First Name input has been filled out with: " + firstName);

		Assert.assertTrue(waits.waitForVisibilityOfElement(lastNameInput, MID_WAIT));
		jsf.shadeElem(lastNameInput);
		log.info("Last Name input is displayed");
		selact.sendTextToElement(lastNameInput, lastName);
		log.info("Last Name input has been filled out with: " + lastName);

		Assert.assertTrue(waits.waitForVisibilityOfElement(postCodeInput, MID_WAIT));
		jsf.shadeElem(postCodeInput);
		log.info("Postal Code input is displayed");
		selact.sendTextToElement(postCodeInput, postalCode);
		log.info("Postal Code input has been filled out with: " + postalCode);

		Assert.assertTrue(waits.waitForVisibilityOfElement(addCustomerSubmitBtn, MID_WAIT));
		jsf.shadeElem(addCustomerSubmitBtn);
		log.info("Add Customer submit button is displayed");
		selact.clickOnElement(addCustomerSubmitBtn);
		log.info("Add Customer submit button has been clicked");

		waits.waitForAlertToBePresent(MID_WAIT);
		Alert alert = waits.getPresentAlert(MID_WAIT);
		Thread.sleep(2000);
		Assert.assertTrue(alert.getText().contains(alertText), "Alert text: " + alert.getText());
		alert.accept();

	}
}
