package utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This class reads the values from the file configuration.properties
 * 
 * @return Value from configuration.properties
 */
public class ConfigReader {

	private static final Logger log = LogManager.getLogger(ConfigReader.class.getName());
	Properties pro;

	public ConfigReader(String env) {

		try {
			if (env.equalsIgnoreCase("Prod")) {
				File src = new File(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				log.info("The environment is: " + env);
				FileInputStream fis = new FileInputStream(src);

				pro = new Properties();
				pro.load(fis);

			} else if (env.equalsIgnoreCase("Test")) {
				File src = new File(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
				log.info("The environment is: " + env);
				FileInputStream fis = new FileInputStream(src);

				pro = new Properties();
				pro.load(fis);
			}

		} catch (Exception e) {
			log.debug("Exception is == " + e.getMessage());
		}
	}

	// Config properties
	public List<String> getBrowser() {
		String property = pro.getProperty("browser");
		List<String> propertyList = new ArrayList<String>(Arrays.asList(property.split(",")));
		return propertyList;
	}

	public void setBrowser(String browser) {
		System.setProperty("browser", browser);
	}

	public String getWebSiteUrl() {
		return pro.getProperty("webSiteUrl");
	}
	// BrowserStack Keys -----------
	
	public String getAutomateUsername() {
		return pro.getProperty("AUTOMATE_USERNAME");
	}
	
	public String getAutomateAccessKey() {
		return pro.getProperty("AUTOMATE_ACCESS_KEY");
	}
	
	//-----------------------

	public String getFirstName() {
		return pro.getProperty("firstName");
	}

	public String getLastName() {
		return pro.getProperty("lastName");
	}

	public String getPostCode() {
		return pro.getProperty("postCode");
	}

	public String getAlertText() {
		return pro.getProperty("alertText");
	}

}
