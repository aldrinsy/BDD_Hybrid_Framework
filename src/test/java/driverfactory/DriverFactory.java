package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
	public enum Browser {
		CHROME, EDGE
	}

	private static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();

	public static WebDriver initializeBrowser(String browserName) {
		Browser browser = Browser.valueOf(browserName.toUpperCase());

		switch (browser) {
		case CHROME:
			initChromeDriver();
			break;
		case EDGE:
			initEdgeDriver();
			break;
		default:
			System.out.println("****** BROWSER IS NOT DEFINED ******");
		}
		return getDriver();
	}

	private static void initChromeDriver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless", "--window-size=1920,1080");
		tdriver.set(new ChromeDriver(options));
	}

	private static void initEdgeDriver() {
		tdriver.set(new EdgeDriver());
	}

	public static WebDriver getDriver() {
		return tdriver.get();
	}
}