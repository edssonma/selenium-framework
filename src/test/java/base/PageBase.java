package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.asserts.SoftAssert;

import pages.Bank_Manager_Page;
import utilities.ExplicitWaitFunctions;
import utilities.JavaScriptFunctions;
import utilities.SeleniumActions;
import utilities.UsefulMethods;
import utilities.WebSiteFunctions;

public abstract class PageBase {

	public WebDriver driver;
	public SeleniumActions selact;
	public ExplicitWaitFunctions waits;
	public JavaScriptFunctions jsf;
	public WebSiteFunctions wbf;
	public UsefulMethods um;
	public static Logger log;
	public SoftAssert softAssert;

	public PageBase(WebDriver driver, String className) {
		this.driver = driver;
		waits = new ExplicitWaitFunctions(driver);
		selact = new SeleniumActions(driver);
		jsf = new JavaScriptFunctions(driver);
		wbf = new WebSiteFunctions(driver);
		um = new UsefulMethods(driver);
		log = (Logger) LogManager.getLogger(className);
		softAssert = new SoftAssert();
	}
}
