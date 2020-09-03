package utilities;

import java.io.IOException;

public class Docker {

	private static Runtime runtime = Runtime.getRuntime();

	public void startSeleniumGridContainers() throws InterruptedException, IOException {

		runtime.exec("docker-compose up -d");
		Thread.sleep(4000);
		System.out.println("DOCKER: Selenium Grid containers has been started...");
	}

	public void stopSeleniumGridContainers() throws InterruptedException, IOException {
		runtime.exec("docker-compose kill");
		Thread.sleep(4000);
		System.out.println("DOCKER: Selenium Grid containers has been stopped...");
	}
}
