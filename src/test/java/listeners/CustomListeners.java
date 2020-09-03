package listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import base.TestBase;
import utilities.MonitoringMail;
import utilities.TestConfig;
import utilities.TestUtil;
import utilities.UsefulMethods;

public class CustomListeners extends TestBase implements ITestListener, ISuiteListener {

	static Date d = new Date();
	static String fileName = "Extent_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
	static String messageBody;
	public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<ExtentTest>();
	public UsefulMethods um = new UsefulMethods(driver);

	@BeforeMethod
	public void onTestStart(ITestResult result) {

		// initialize the report
		test = report
				.createTest(result.getTestClass().getName() + "     @TestCase : " + 
		result.getMethod().getMethodName());
		
		testReport.set(test);

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test script: " + methodName + " has started..." + "</b>";
		test.log(Status.INFO, logText);
	}

	public void onTestSuccess(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test script: " + methodName + " has passed..." + "</b>";
		test.log(Status.PASS, logText);
	}

	public void onTestFailure(ITestResult result) {

		try {

			test.log(Status.FAIL, "Test script failed: " + result.getName());
			test.addScreenCaptureFromPath(um.getScreenshot(driver, result.getName()));
			test.log(Status.FAIL, result.getName() + " FAIL with error " + result.getThrowable());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		report.flush();

	}

	public void onTestSkipped(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test script:- " + methodName + " Skipped" + "</b>";
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
		testReport.get().skip(m);
		driver.quit();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {

	}

	public void onFinish(ISuite arg0) {

		MonitoringMail mail = new MonitoringMail();

		try {
			messageBody =

					"username: reporter | password: reporter" + "\n" +

							"http://" + InetAddress.getLocalHost().getHostAddress()
							+ ":8080/job/DataDrivenSeleniumFramework/Execution_20Report/";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub

	}

	@AfterMethod
	public void onFinish(ITestContext context) {

		if (report != null) {
			report.flush();
		}

	}

}
