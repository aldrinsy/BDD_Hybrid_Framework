package hooks;

import java.time.Duration;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import driverfactory.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.CommonUtils;
import utils.ConfigReader;

public class AppHooks {

	private WebDriver driver;

	@Before
	public void launchBrowser() {
		driver = DriverFactory.initializeBrowser(ConfigReader.getBrowser());
		driver.get(ConfigReader.getUrl());
		configureDriver();
	}

	@After
	public void closeBrowser(Scenario sceanrio) {
		String scenarioName = sceanrio.getName();
		if (sceanrio.isFailed()) {
			TakesScreenshot takescreenshot = (TakesScreenshot) driver;
			byte[] screnshot = takescreenshot.getScreenshotAs(OutputType.BYTES);
			sceanrio.attach(screnshot, "image/png", scenarioName);
		}
		driver.quit();
	}

	private void configureDriver() {
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(CommonUtils.PAGE_LOAD_TIME));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(CommonUtils.IMPLICIT_WAIT_TIME));
	}

}
