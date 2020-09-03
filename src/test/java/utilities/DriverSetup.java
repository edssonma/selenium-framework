package utilities;

import static utilities.Constants.MID_WAIT;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.core.DockerClientBuilder;

public class DriverSetup {

	private static WebDriver driver;
	private static ConfigReader config = new ConfigReader("Test");
	// Keys for running on https://www.browserstack.com/
	public static final String URL = "https://" + config.getAutomateUsername() + ":" + config.getAutomateAccessKey()
			+ "@hub-cloud.browserstack.com/wd/hub";
	// ------------------------------------------------------------------------------------------
	private static final Logger log = (Logger) LogManager.getLogger(DriverSetup.class.getName());

	/*
	 * This method initialize the driver locally or in selenium grid
	 */
	public static WebDriver setUpDriver(String browser, String env, String mode)
			throws IOException, InterruptedException {

		// path to drivers
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver",
				System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");

		// path to firefox exe
		File pathBinary = new File("C:\\Users\\edson.molina\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
		DesiredCapabilities dr = null;
		FirefoxOptions firefoxOptions = null;

		if (browser.equals("chrome")) {
			dr = DesiredCapabilities.chrome();
			dr.setBrowserName("chrome");
			dr.setPlatform(Platform.ANY);

		} else if (browser.equals("firefox")) {

			dr = DesiredCapabilities.firefox();
			// Setting up the local firefox exe when is running locally
			if (env.equals("local")) {
				FirefoxBinary firefoxBinary = new FirefoxBinary(pathBinary);
				firefoxOptions = new FirefoxOptions();
				dr.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions.setBinary(firefoxBinary));
			}
			dr.setBrowserName("firefox");
			dr.setPlatform(Platform.ANY);
		}

		if (env.equals("grid")) {
			// return the selenium grid remote driver
			driver = new RemoteWebDriver(new URL(mode.equals("browserstack") ? URL : "http://localhost:4444/wd/hub"),
					dr);
		} else {
			// return the local web driver
			if (browser.equals("chrome"))
				driver = new ChromeDriver();
			else if (browser.equals("firefox"))
				driver = new FirefoxDriver(firefoxOptions);
		}
		return driver;
	}

	public static void driverInit(WebDriver driver, String webSite) {
		driver.get(webSite);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(MID_WAIT, TimeUnit.SECONDS);
	}
}
