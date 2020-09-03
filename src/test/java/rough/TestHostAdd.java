package rough;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import utilities.MonitoringMail;
import utilities.TestConfig;

public class TestHostAdd {

	public static void main(String[] args) throws UnknownHostException, AddressException, MessagingException {

		MonitoringMail mail = new MonitoringMail();

		String messageBody =

				"username: reporter | password: reporter" + "\n" +

						"http://" + InetAddress.getLocalHost().getHostAddress()
						+ ":8080/job/DataDrivenSeleniumFramework/Execution_20Report/";

		System.out.println(messageBody);

		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, messageBody);
	}
}
