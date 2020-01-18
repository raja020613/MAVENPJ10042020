package orangeHRM.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import orangeHRM.factories.DataProviderFactory;
import orangeHRM.pages.BaseClass;
import orangeHRM.pages.LoginPage;
import orangeHRM.pages.LogoutPage;

public class LoginScenarios extends BaseClass {

	LoginPage login;
	LogoutPage logout;
	
	
	@Test(priority=0)
	public void verifyPage()
	{		
		
		System.out.println("Report 1 >"+report);
		
		login=PageFactory.initElements(driver, LoginPage.class);
		
		logout=PageFactory.initElements(driver, LogoutPage.class);
		
		logger=report.createTest("URL validation");
		
		login.verifyUrlBeforeLogin();	
		
		logger.info("Validating url");
	}
	
	@Test(priority=1,dependsOnMethods="verifyPage")
	public void loginToApplication()
	{
		
		
		logger=report.createTest("Login as admin");
		
		login.loginToApplication(DataProviderFactory.getExcel().getCellData("OrangeHRM", 1, 0),
				
				DataProviderFactory.getExcel().getCellData("OrangeHRM", 1, 1));
		
		login.verifyUrlAfterLogin();
		
		logger.info("Logged in");
	}
	
	@Test(priority=2,dependsOnMethods="loginToApplication")
	public void logoutFromApplication()
	{
		logger=report.createTest("Logout");
		
		logout.logOutFromApplicationWithAdminRole();
		
		logger.info("Logout done");
		
	}

}
