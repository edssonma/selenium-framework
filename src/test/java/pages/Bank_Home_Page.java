package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import base.PageBase;
import utilities.ExplicitWaitFunctions;
import utilities.JavaScriptFunctions;
import utilities.SeleniumActions;
import utilities.UsefulMethods;
import utilities.WebSiteFunctions;
import static utilities.Constants.*;

public class Bank_Home_Page extends PageBase {

	private static final Logger log = LogManager.getLogger(Bank_Home_Page.class.getName());

	public Bank_Home_Page(WebDriver driver) {
		super(driver, Bank_Home_Page.class.getName());
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//button[@class='btn home']")
	private WebElement homeBtn;

	@FindBy(xpath = "//button[text()='Customer Login']")
	private WebElement customerLoginBtn;

	@FindBy(xpath = "//button[text()='Bank Manager Login']")
	private WebElement bankManagerLoginBtn;

	// ------------------------------------------------------------------------------------------

	/*
	 * This method clicks the home button
	 */
	public void clickHomeButton() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(homeBtn, MID_WAIT));
		jsf.shadeElem(homeBtn);
		log.info("Home button is displayed");
		selact.clickOnElement(homeBtn);
		log.info("Home button has been clicked");
	}

	/*
	 * This method clicks the customer login button
	 */
	public void clickCustomerLoginButton() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(customerLoginBtn, MID_WAIT));
		jsf.shadeElem(customerLoginBtn);
		log.info("Customer Login button is displayed");
		selact.clickOnElement(customerLoginBtn);
		log.info("Customer Login button has been clicked");
	}

	/*
	 * This method clicks the customer login button
	 */
	public void clickBankManagerLoginBtn() {
		Assert.assertTrue(waits.waitForVisibilityOfElement(bankManagerLoginBtn, MID_WAIT));
		jsf.shadeElem(bankManagerLoginBtn);
		log.info("Bank Manager Login button is displayed");
		selact.clickOnElement(bankManagerLoginBtn);
		log.info("Bank Manager Login button has been clicked");
	}

}
