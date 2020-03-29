package orangeHRM.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import orangeHRM.factories.BrowserFactory;
import orangeHRM.factories.DataProviderFactory;
import orangeHRM.helper.Utility;

public class BaseClass {

	public WebDriver driver;
	public static ExtentReports report;
	public ExtentTest logger;
	
	@BeforeSuite
	public void setupReport()
	{
		
		System.out.println("Log:INFO- Report is getting setup");
		
		ExtentHtmlReporter htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Report/ExtentReport.html");
		
		report=new ExtentReports();
		
		report.attachReporter(htmlReporter);
		
		System.out.println("Log:INFO- Report value "+report);
		
		System.out.println("Log:INFO- Report setup ready");
	}
	
	@AfterMethod
	public void tearDownReport(ITestResult result)
	{
			
		if(result.getStatus()==ITestResult.SUCCESS) {
			
			System.out.println("Log:INFO- Test Executed- Test Status is Passed");
			
		}
		else if(result.getStatus()==ITestResult.FAILURE) {
			
			System.out.println("Log:INFO- Test Executed- Test Status is Failed");

			
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			System.out.println("Log:INFO- Test Executed- Test Status is Skipped");

		}
		
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			
			try {
				logger.pass("Test Passed", MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
			} catch (IOException e) {
				
			}
			
		}
		else if(result.getStatus()==ITestResult.FAILURE) {
			
			try {
				logger.fail("Test Failed "+result.getThrowable().getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(Utility.getScreenshot(driver)).build());
			} catch (IOException e) {
				System.out.println("Unable to attach screenshot in report "+e.getMessage());
			}
			
		}
		else if(result.getStatus()==ITestResult.SKIP)
		{
			logger.skip("Test Skipped");
		}

		report.flush();
	}
	
	
	@Parameters({"Browser","AppURL"})
	@BeforeClass
	public void setUp(String browser,String appurl)
	{
		System.out.println("Log:INFO- Setting up Browser and Application");
		
		
	//	driver=BrowserFactory.getApplication(DataProviderFactory.getConfig().getValue("Browser"),
		//		DataProviderFactory.getConfig().getValue("QAEnv"));
	
		driver=BrowserFactory.getApplication(browser,appurl);
		
		System.out.println("Driver value is "+driver);

		
		System.out.println("Log:INFO- Browser and application is ready");
	}
	
	@AfterClass
	public void tearDown()
	{
		System.out.println("Log:INFO- Terminating browser");
		
		driver.quit();
				
		System.out.println("Log:INFO- Browser terminated");
	}
	
}
