package steps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.DriverManager;
import utilities.ThreadManager;

public class Hooks {
	DriverManager drivermanager = new DriverManager();
	Scenario scenario;
	public static String scenarioname;
	Scenario currentScenario;

	public Scenario getCurrentScenario() {
		return currentScenario;
	}

	public void setCurrentScenario(Scenario currentScenario) {
		this.currentScenario = currentScenario;
	}

	@Before
	public void setUp() {
		drivermanager.setUp(ThreadManager.getBrowser());
	}

	@Before
	public void before(Scenario scenario) {
		this.scenario = scenario;
		scenarioname=scenario.getName();
		System.out.println("scenario values is" + scenarioname);
	}

	@After
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			byte[] screenshotBytes = ((TakesScreenshot) ThreadManager.getDriver()).getScreenshotAs(OutputType.BYTES);
			// scenario.embed(screenshotBytes, "image/png",scenario.getName());
			scenario.attach(screenshotBytes, "image/png", scenario.getName());
		}

		drivermanager.tearDown();
	}
}
