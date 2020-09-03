package utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

public class UsefulMethods {
	WebDriver driver;
	private static final Logger log = LogManager.getLogger(UsefulMethods.class.getName());
	private String parentHandle;
	private Set<String> handles;

	public String getScreenshotBase64(WebDriver driver, String filename) {
		String encodedBase64 = null;
		TakesScreenshot tsc = (TakesScreenshot) driver;
		File src = tsc.getScreenshotAs(OutputType.FILE);

		String directory;

		// IF we are running as a Remote Execution:
		if (driver.toString().contains("RemoteWebDriver")) {
			((RemoteWebDriver) driver).setFileDetector(new LocalFileDetector());
			log.debug("RemoteWebDriver detected..");
			directory = "./ScreenShot/";

		} else {
			log.debug("Local WebDriver detected..");
			directory = "\\ScreenShot\\";
		}
		String path = directory + filename + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(src, destination);
			FileInputStream fileInputStream = new FileInputStream(destination);
			byte[] bytes = new byte[(int) destination.length()];
			fileInputStream.read(bytes);
			encodedBase64 = new String(Base64.encodeBase64(bytes));

			fileInputStream.close();
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return encodedBase64;
	}

	public String getScreenshot(WebDriver driver, String filename) {
		TakesScreenshot tsc = (TakesScreenshot) driver;
		File src = tsc.getScreenshotAs(OutputType.FILE);
		String directory = "./ScreenShot/";
		String path = directory + filename + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		String absolutePath = destination.getAbsolutePath();
		try {
			FileUtils.copyFile(src, destination);
		} catch (IOException e) {
			System.out.println("Capture Failed " + e.getMessage());
		}
		return absolutePath;
	}

	public UsefulMethods(WebDriver driver) {
		this.driver = driver;
	}

	public void getParentHandle() {
		parentHandle = driver.getWindowHandle();
		log.info("The parent handle is: " + parentHandle);
	}

	public void getAllHadles() {
		handles = driver.getWindowHandles();
		for (String handle : handles) {
			log.info("Handle found :" + handle);
		}
	}

	public void switchToHandle() {
		try {
			for (String handle : handles) {
				log.info(handle);
				if (!handle.equals(parentHandle)) {
					driver.switchTo().window(handle);
					log.info("Move to handle: " + handle);
					break;
				}
			}
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void closeHandle() {
		driver.close();
		log.info("The tab was closed");
	}

	public boolean elementExist(String xpath) {
		return driver.findElements(By.xpath(xpath)).size() > 0;
	}

	public void returnToParentHandle() {
		try {
			driver.switchTo().window(parentHandle);
			log.info("Return to the parent handle: " + parentHandle);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public File lastestDownloadedFile(String downloadPath) {
		File dir = new File(downloadPath);
		File[] dirList = dir.listFiles();
		if (dirList == null || dirList.length == 0) {
			log.info("No files downloaded");
			return null;
		}

		File lmFile = dirList[0];
		for (int i = 1; i < dirList.length; i++) {
			if (lmFile.lastModified() < dirList[i].lastModified()) {
				lmFile = dirList[i];
			}
		}
		log.info("The latest downloaded file is: " + lmFile.getName());
		return lmFile;
	}

	public void verifyLastestDownload(String downloadPath, String filedwnname) {
		try {
			Thread.sleep(10000);
			String home = System.getProperty("user.home");
			File glf = lastestDownloadedFile(home + downloadPath);
			String fileName = glf.getName();
			log.info("The file downloaded was: " + fileName);
			Boolean fileNameC = fileName.contains(filedwnname);
			Assert.assertTrue(fileNameC);
			log.info("The File successfully downloaded");
			glf.delete();
			log.info("The File: " + fileNameC + " was deleted successfully");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Double roundValues(Double value) {
		DecimalFormat df = new DecimalFormat("#####.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String nfv = df.format(value);
		Double dNewFormat = Double.parseDouble(nfv);
		log.info(df.format(value));
		return dNewFormat;
	}

	public int randValue(int size) {
		Random random = new Random();
		int rand = random.nextInt(size);
		return rand;
	}

	// ------------------------------------------------------------------------

	/**
	 * Pause the execution for the given time duration in Milliseconds.
	 * 
	 * @param milliseconds
	 */
	public void waitThreadTime(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * Returns the yesterday's date & noon time (12:00 PM).
	 * 
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayNoonDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.add(Calendar.DATE, -1);
		Date yesterdayNoon = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		log.debug("Yesterday's date at noon with format: " + dateFormat.format(yesterdayNoon));

		return dateFormat.format(yesterdayNoon);
	}

	/**
	 * Returns the yesterday's date & time from current system.
	 * 
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getYesterdayDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date yesterday = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Yesterday's date with format: " + dateFormat.format(yesterday));

		return dateFormat.format(yesterday);
	}

	/**
	 * Returns the current date & time from system.
	 * 
	 * @return Date in the following format yyyy-MM-dd
	 */
	public String getTodayDateString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date yesterday = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		log.debug("Today's date with format: " + dateFormat.format(yesterday));

		return dateFormat.format(yesterday);
	}

	/**
	 * Returns the current date & time from system.
	 * 
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateTimeString() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		Date yesterday = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(yesterday));

		return dateFormat.format(yesterday);
	}

	/**
	 * Returns the current date & time from system with the additional amount of
	 * hours given.
	 * 
	 * @param hour
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getTodayDateStringPlusHour(int hour) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, hour);
		Date yesterday = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.debug("Today's date with format: " + dateFormat.format(yesterday));

		return dateFormat.format(yesterday);
	}

	/**
	 * Returns the current date & time from system adjusted to set the date to the
	 * next week of day number.
	 * 
	 * @param weekOfDay
	 *            - standard, from 1 (Monday) to 7 (Sunday).
	 * @return Date in the following format yyyy-MM-dd hh:mm:ss
	 */
	public String getNextDayString(DayOfWeek weekOfDay) {
		LocalDateTime nextDay = LocalDateTime.now().with(TemporalAdjusters.next(weekOfDay));
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		System.out.println("Today's date with format: " + dateFormat.format(nextDay));

		return dateFormat.format(nextDay);
	}

	/**
	 * Send the ExtentReport.html file via email to the StkYaleAutomation Team
	 * 
	 * @param reportPath
	 * @param reportName
	 */
	public void sendEmailWithReport(String reportPath, String reportName) {
		// Create object of Property file
		Properties props = new Properties();

		// this will set host of server- you can change based on your requirement
		props.put("mail.smtp.host", "smtp.office365.com");

		// set the port of socket factory
		props.put("mail.smtp.socketFactory.port", "587");
		props.put("mail.smtp.socketFactory.fallback", "false");

		// set socket factory
		// props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");

		// set the authentication to true
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);

		// set the port of SMTP server
		props.put("mail.smtp.port", "587");

		// This will handle the complete authentication
		Session session = Session.getDefaultInstance(props,

				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("jorge.morales@yale.edu", "Droanougmk3}");
					}
				});

		try {

			// Create object of MimeMessage class
			Message message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress("jorge.morales@yale.edu"));

			// Set the recipient address
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("jorge.morales@yale.edu,moises.valdes@yale.edu,eduardo.alvarez@yale.edu"));

			// Add the subject link
			message.setSubject("ServiceNow Execution report : " + this.getTodayDateString());

			// Create object to add multimedia type content
			BodyPart messageBodyPart1 = new MimeBodyPart();

			// Set the body of email
			messageBodyPart1.setText("NetX Execution report");

			// Create another object to add another content
			MimeBodyPart messageBodyPart2 = new MimeBodyPart();

			// Mention the file which you want to send
			// Create data source and pass the filename
			DataSource source = new FileDataSource(reportPath);

			// set the handler
			messageBodyPart2.setDataHandler(new DataHandler(source));

			// set the file
			messageBodyPart2.setFileName(reportName);

			// Create object of MimeMultipart class
			Multipart multipart = new MimeMultipart();

			// add body part 1
			multipart.addBodyPart(messageBodyPart2);

			// add body part 2
			multipart.addBodyPart(messageBodyPart1);

			// set the content
			message.setContent(multipart);

			// finally send the email
			Transport.send(message);

			System.out.println("=====Email Sent=====");

		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}

	/**
	 * This method convert seconds to time 24h
	 * 
	 * @param time
	 *            (example: 12:34 AM or 05:18 PM)
	 * @return Input: "12:34 AM" Output: "00:34"
	 * @throws ParseException
	 * @author Edson Molina
	 */
	public String convert12To24HourType(String time) throws ParseException {
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

		Date formated = parseFormat.parse(time);
		return displayFormat.format(formated);
	}

	/**
	 * This method convert seconds to time 24h
	 * 
	 * @param time
	 *            (example: 00:34 AM or 17:18)
	 * @return Input: "00:34" Output: "12:34 AM"
	 * @throws ParseException
	 * @author Edson Molina
	 */
	public String convert24To12HourType(String time) throws ParseException {
		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

		Date formated = displayFormat.parse(time);
		return parseFormat.format(formated);
	}

	public WebElement findElement(String xpathExpression) {
		return driver.findElement(By.xpath(xpathExpression));
	}

}
