package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.google.common.io.Files;

import tsd.TSD_Bank;
import utilities.ExcelReader;
import utilities.ConfigReader;
import utilities.Docker;

import static utilities.DriverSetup.*;
import static utilities.Constants.*;

public class TestBase {

	/*
	 * | WebDrivers | Properties | Logs | ExtentReports | DB | Excel | Mail |
	 * ReportNG | Jenkins
	 */

	// config
	public static WebDriver driver;
	public static FileInputStream fis;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static ConfigReader config = new ConfigReader("Test");
	public static Logger log;
	public static String browser;
	// report
	ExtentHtmlReporter htmlReporter;
	public static ExtentReports report;
	public static ExtentTest test;
	String reportName;
	String reportPath;
	// time
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm");
	DateTimeFormatter daily_dtf = DateTimeFormatter.ofPattern("yy.MM.dd");
	LocalDateTime now = LocalDateTime.now();

	/*
	 * This method is executed before the suite starts
	 */
	@Parameters({ "runType" })
	@BeforeSuite
	public void runTypeSetup(String runType) throws InterruptedException, IOException {
		if (runType.equals("docker")) {
			// up the selenium/hub, node-chorme and node-firefox containers
			(new Docker()).startSeleniumGridContainers();
		}
	}

	/*
	 * This method is executed after when the suite ends
	 */
	@Parameters({ "runType" })
	@AfterSuite
	public void endRunType(String runType) throws InterruptedException, IOException {
		if (runType.equals("docker")) {
			// up the selenium/hub, node-chorme and node-firefox containers
			(new Docker()).stopSeleniumGridContainers();
		}
	}

	/*
	 * This method is executed before of each Test Script
	 */
	@Parameters({ "browserType" })
	@BeforeMethod
	public void scriptSetup(Method m, String browserType) throws IOException, InterruptedException {

		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			// from Jenkins parameters
			browser = System.getenv("browser");
		} else if (browserType != null && !browserType.isEmpty() && !browserType.equals("none")) {
			// from suite parameters
			browser = browserType;
		} else {
			// from Local properties | default 'chrome'
			browser = config.getBrowser().get(0);
		}

		// Initializing web driver
		// Second and Third parameters are declared in utilities.Constants.java class
		driver = setUpDriver(browser, SELENIUM_GRID, DOCKER);
		driverInit(driver, config.getWebSiteUrl());

		// initializing logger
		log = (Logger) LogManager.getLogger(m.getName());
	}

	/*
	 * This method is executed after of each Test Script
	 */
	@AfterMethod
	public void endScript(ITestResult result) {
		report.flush();
		driver.quit();
	}

	/*
	 * Set up the extent reports
	 */
	@BeforeSuite(description = "Extent Report Init")
	public void reportSetUp() {

		// ExtentReports configuration:
		System.out.println("Report has been set up at: " + dtf.format(now).toString());

		try {
			File dailyDir = new File("./Reports/" + daily_dtf.format(now).toString());
			dailyDir.mkdir();

			this.reportPath = "./Reports/" + dailyDir.getName() + "/Report_" + dtf.format(now).toString() + "_"
					+ browser + "_Suite.html";
			this.reportName = dailyDir.getName() + "_" + browser + "_Suite.html";

			report = new ExtentReports();
			htmlReporter = new ExtentHtmlReporter(reportPath);
			htmlReporter.loadXMLConfig("extent-config.xml");

			report.attachReporter(htmlReporter);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Ending report
	 */
	@AfterSuite
	public void endReport() throws IOException {
		System.out.println("----------- Test Case Completed------------");
		report.flush();
		Files.copy(new File(this.reportPath), new File("./last-report/index.html"));
	}

}
