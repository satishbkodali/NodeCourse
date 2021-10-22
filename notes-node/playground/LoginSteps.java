package steps;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.Scenario;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageobjects.LoginpagePO;
import pageobjects.VerificationPO;
import utilities.ExcelDataHandler;
import utilities.GetDataFromExcel;
import utilities.ThreadManager;

public class LoginSteps {
	
	private final WebDriver driver = ThreadManager.getDriver();
	GetDataFromExcel data=new GetDataFromExcel();
	Hooks context=new Hooks();
	

	
	@Given("I  Launch login page of {string}")
	public void i_Launch_login_page_of(String string) {
	    new LoginpagePO(driver).getURL();
	}
	
	@Given("I Input valid Laptop {string}")
	public void i_Input_valid_Laptop(String string) {
		String value = null;
		try {
			value=data.get(context.scenarioname, string);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		new LoginpagePO(driver).enterTextPassword(value);
	}

	@Given("I Input valid mobile number {string}")
	public void i_Input_valid_mobile_number(String HP) {
	    new LoginpagePO(driver).enterTextPassword(HP);
	}

	@When("I click on Continue")
	public void i_click_on_Continue() throws InterruptedException {
		 new LoginpagePO(driver).clickSearchIcon();
	}
	@Then("I load workbook {string}")
	public void i_load_workbook(String path) {
		
		try {
			data.get("1", "Laptops");
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		/*
		 * try { ExcelDataHandler eDH= new ExcelDataHandler(path);
		 * if(path.endsWith(".xls")) { HSSFWorkbook workBook=eDH.getXlsWorkbook();
		 * List<String> sheets=eDH.getSheetNames(workBook); for(String sheetName :
		 * sheets) { eDH.LoadExcelDataSheet(workBook.getSheet(sheetName)); } }
		 * }catch(Exception e) {
		 * 
		 * }
		 */
	}



}
