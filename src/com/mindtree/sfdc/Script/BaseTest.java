package com.mindtree.sfdc.Script;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.mindtree.sfdc.Pages.CasesComponent;
import com.mindtree.sfdc.generic.Excel;
import com.mindtree.sfdc.generic.Property;
import com.mindtree.sfdc.generic.Utility;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

abstract public class BaseTest implements AutomationConstants
{
	public static WebDriver driver;
	public static ExtentReports eReport;
	public static ExtentTest testReport;
	
	@DataProvider
	public String[][] getScenarios()
	{
		int scenarioCount=Excel.getRowCount(controllerPath,suiteSheet);
		String[][] data=new String[scenarioCount][2];
		for(int i=1;i<=scenarioCount;i++)
		{
			String scenarioName=Excel.getCellValue(controllerPath,suiteSheet,i,0);
			String executionStatus=Excel.getCellValue(controllerPath,suiteSheet,i,1);
			data[i-1][0]=scenarioName;
			data[i-1][1]=executionStatus;
		}
		return data;
	}
	
	
	/*
	 * Launch the Browser
	 * Access the application with valid details
	 */
	@BeforeSuite
	public void initFrameWork()
	{	
		eReport=new ExtentReports(reportFilePath);
		driver = Utility.LaunchApplication(driver);
		Utility.loginToApp(driver);
		
		try 
		{
			FileUtils.deleteDirectory(new File("./test-output"));
		} 
		catch (IOException e) 
		{
			System.out.println(e);
		}
		
	}
	//Starting Extent report
	@BeforeMethod
	public void startReport(Method method) 
	{
		//testReport = eReport.startTest(method.getName());
	}

	//Righting Data to Extent report
	@AfterMethod
	public void endReport(ITestResult test) throws Exception
	{
		if(test.getStatus()==ITestResult.FAILURE)
		{
			String pImage=Utility.getPageScreenShot(driver,screenShotsFolderPath);
			String p = testReport.addScreenCapture("."+pImage);
			/*ArrayList<Map<String, String>> CaseData = Excel.getSheetData(AutomationConstants.DataFilePath,"Case");
			CasesComponent c = new CasesComponent(driver, testReport);
			c.createCases(CaseData,p);*/
			testReport.log(LogStatus.FAIL,"Page screen shot:"+p);
		}
		else if(test.getStatus()==ITestResult.SUCCESS)
		{
			String pImage=Utility.getPageScreenShot(driver,screenShotsFolderPath);
			String p = testReport.addScreenCapture("."+pImage);
			testReport.log(LogStatus.PASS,"Page screen shot:"+p);
		}
		eReport.endTest(testReport);
	}

	/*
	 * Generating the Extent Report
	 * Logging out of Application
	 * Closing the Driver
	 */
	
	@AfterSuite
	public void endFrameWork()
	{
		driver.findElement(By.id("userNavLabel")).click();
		driver.findElement(By.xpath("//a[@title='Logout']")).click();
		System.out.println("Loggedout successfully");
		driver.close();
		
		//Email trigger To Team
		/*try {
		      Runtime.getRuntime().exec("wscript C:/Users/m1040467/git/SalesForce/driver/Email.vbs");
		   }
		   catch( IOException e ) {
		      e.printStackTrace();
		   }
	*/
		eReport.flush();
			
	}
	
}