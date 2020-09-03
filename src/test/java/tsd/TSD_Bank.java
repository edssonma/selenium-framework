package tsd;

import static utilities.Constants.MID_WAIT;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.MediaEntityModelProvider;
import com.aventstack.extentreports.Status;

import base.TestBase;
import pages.Bank_Home_Page;
import pages.Bank_Manager_Page;

public class TSD_Bank extends TestBase {

	private Bank_Home_Page bankHomePage;
	private Bank_Manager_Page bankManagerPage;

	@BeforeMethod
	public void setUpTSDClass() {
		bankHomePage = new Bank_Home_Page(driver);
		bankManagerPage = new Bank_Manager_Page(driver);
	}

	@Test
	public void addCustomer() throws InterruptedException, MalformedURLException {

		// Step 1 - Click the Bank Manager Login button
		test.log(Status.INFO, "Step 1 - Click the Bank Manager Login button");
		bankHomePage.clickBankManagerLoginBtn();

		// Step 2 - Click the Add Customer button
		test.log(Status.INFO, "Step 2 - Click the Add Customer button");
		bankManagerPage.clickAddCustomerButton();

		// Step 3 - Add a new customer by filling out the add customer form
		test.log(Status.INFO, "Step 3 - Add a new customer by filling out the add customer form");
		bankManagerPage.addNewCustomer(config.getFirstName(), config.getLastName(), config.getPostCode(),
				config.getAlertText());

		log.info("##################### PASSED ######################");
	}
	
	@Test
	public void deleteCustomer() throws InterruptedException, MalformedURLException {

		// Step 1 - Click the Bank Manager Login button
		test.log(Status.INFO, "Step 1 - Click the Bank Manager Login button");
		bankHomePage.clickBankManagerLoginBtn();

		// Step 2 - Click the Add Customer button
		test.log(Status.INFO, "Step 2 - Click the Add Customer button");
		bankManagerPage.clickAddCustomerButton();

		// Step 3 - Add a new customer by filling out the add customer form
		test.log(Status.INFO, "Step 3 - Add a new customer by filling out the add customer form");
		bankManagerPage.addNewCustomer(config.getFirstName(), config.getLastName(), config.getPostCode(),
				config.getAlertText());

		log.info("##################### PASSED ######################");
	}
}
