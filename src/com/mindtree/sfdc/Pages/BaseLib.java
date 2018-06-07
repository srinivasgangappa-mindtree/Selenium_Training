package com.mindtree.sfdc.Pages;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.mindtree.sfdc.generic.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public abstract class BaseLib
{
	public WebDriver driver;
	public ExtentTest testReport;

	Logger log = Logger.getLogger("BaseLib");

	public BaseLib(WebDriver driver, ExtentTest testReport)
	{
		this.driver=driver;
		this.testReport=testReport;
	}

	//Method to deselect all the Checkboxes
	public void deSelectAllCheckBoxes(List<WebElement> allCheckBox)
	{
		for(WebElement chkBox:allCheckBox)
		{
			if(chkBox.isSelected())
			{
				chkBox.click();
			}
			else
			{
				logReport("INFO","CheckBox is Already DeSelected");
			}
		}
		logReport("INFO","All the CheckBoxs are DeSelected");
	}

	//Method to select all the checkboxes
	public  void selectAllCheckBoxes(List<WebElement> allCheckBox)
	{
		for(WebElement chkBox:allCheckBox)
		{
			if(chkBox.isSelected())
			{
				logReport("INFO","CheckBox is Already Selected");
			}
			else
			{
				chkBox.click();

			}
		}

		logReport("INFO","All the CheckBoxs are Selected");
	}

	//Method to select CheckBox
	public  void selectCheckBoxe(WebElement CheckBox)
	{
		if(CheckBox.isSelected())
		{
			logReport("INFO","CheckBox is Already Selected");
		}
		else
		{
			CheckBox.click();

		}
	}

	//Method to DeSelect CheckBox
	public  void deSelectCheckBoxe(WebElement CheckBox)
	{
		if(CheckBox.isSelected())
		{
			CheckBox.click();
		}
		else
		{
			logReport("INFO","CheckBox is Already DeSelected");
		}
	}

	//Verify that Element is disabled
	public  void verifyElementDisabled(WebElement element)
	{
		if(element.isEnabled())
		{
			logReport("FAIL","Specified Element is Not Disabled");
		}
		else
		{
			logReport("PASS","Specified Element is Disabled");
		}
	}

	//Verify that element is Enabled
	public  void verifyElementEnabled(WebElement element)
	{
		if(element.isEnabled())
		{
			logReport("PASS","Specified Element is Enabled");

		}
		else
		{
			logReport("FAIL","Specified Element is Not Enabled");
		}
	}

	//Verify element is present in listbox
	public  void VerifyListBoxContent(WebElement listBox,String content)
	{
		String[] allOptions = content.split(",");
		for(String option:allOptions)
		{	
			boolean present = Utility.isOptionPresentinListBox(listBox,option);
			if(present)
			{
				logReport("PASS","Specified Option is Present:"+option);
			}
			else
			{
				logReport("FAIL","Specified Option is Not Present:"+option);
			}
		}
	}

	//Verify the Selected Option
	public  void verifySelectedOption(WebElement listBox,String option)
	{
		String actualSelected=new Select(listBox).getFirstSelectedOption().getText();
		logReport("INFO","Selected option is:"+actualSelected);
		logReport("INFO","Expected option to be Selected is:"+option);
		if(actualSelected.equals(option))
		{
			logReport("PASS","Specified Option is Selected");
		}
		else
		{
			logReport("FAIL","Specified Option is Not Selected");
		}
	}

	//Verify the sorting order of Listbox
	public  void verifyListBoxIsSorted(WebElement listBox)
	{

		if(Utility.isListBoxSorted(listBox))
		{
			logReport("PASS","ListBox is Sorted");
		}
		else
		{
			logReport("FAIL","ListBox is Not Sorted");
		}
	}

	//Press Enter key using Robort Class
	public  void pressEnter()
	{
		logReport("INFO", "Pressing ENTER");
		try{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		}
		catch(Exception e)
		{
			logReport("WARNING", "Could not Press ENTER");
			e.printStackTrace();
		}
	}

	//Save the file using Robort Class
	//Handeling download popup using Robort Class
	public  void selectSaveOnPopUp()
	{
		logReport("INFO","Pressing ALT+S");
		try{
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_ALT);
			r.keyPress(KeyEvent.VK_S);
			r.keyRelease(KeyEvent.VK_ALT);
		}
		catch(Exception e)
		{
			testReport.log(LogStatus.WARNING, "Could not Select Save");
			e.printStackTrace();
		}
	}

	//Wait untill Duration in seconds
	public  void waitTill(String durationInSecond)
	{
		System.out.println("waiting");
		try{
			long n=(long)Double.parseDouble(durationInSecond);
			System.out.println(n);
			Thread.sleep(n*1000);
			logReport("INFO","Waiting for "+durationInSecond+" seconds");
		}
		catch(Exception e)
		{
			testReport.log(LogStatus.WARNING, "Could not Wait");
			e.printStackTrace();
		}
	}

	//Mouse over on the element
	public  void hover(WebElement element)
	{
		logReport("INFO","Moving mouse on the element");
		Actions actions=new Actions(driver);
		actions.moveToElement(element).perform();
	}

	//Verify the Text Contains
	public  void verifyTextContains(WebElement element,String eText)
	{
		String text_getText=element.getText();
		String text_innerHTML=element.getAttribute("innerHTML");
		String text_textContent=element.getAttribute("textContent");
		logReport("INFO","Expected text is:"+eText);
		logReport("INFO", "getText:"+text_getText);
		logReport("INFO","innerHTML:"+text_innerHTML);
		logReport("INFO", "textContent:"+text_textContent);
		if(text_getText.contains(eText))
		{	
			logReport("PASS","Verified using getText; Actual text contains Expected text");
		}
		else if(text_innerHTML.contains(eText))
		{	
			logReport("PASS","Verified using innerHTML; Actual text contains Expected text");
		}
		else if(text_textContent.contains(eText))
		{	
			logReport("PASS","Verified using textContent; Actual text contains Expected text");
		}
		else
		{
			logReport("FAIL","Actual text DO NOT contains Expected text");
		}
	}

	//Verify the Element Colour
	public  void verifyElementColor(WebElement element,String eHexColor)
	{

		String strRGB=element.getCssValue("color");
		logReport("INFO","RGB is:"+strRGB);
		String hex=Utility.convertRGBtoHex(strRGB);

		String msg1="<span style='color:"+eHexColor+";'>Expected color</span>";
		testReport.log(LogStatus.INFO,"HTML",msg1);

		String msg2="<span style='color:"+hex+";'>Actual color</span>";
		testReport.log(LogStatus.INFO,"HTML",msg2);


		if(hex.equals(eHexColor))
		{
			logReport("PASS","Element color is matching");
		}
		else
		{
			logReport("FAIL","Element color is not matching");
		}
	}

	//Handeling file upload popup
	public  void selectFileToUpload(WebElement element,String filePath)
	{
		String absoluteFilePath=new File(filePath).getAbsolutePath();
		logReport("INFO","absoluteFilePath:"+absoluteFilePath);
		element.sendKeys(absoluteFilePath);
	}

	//Enter a value into Field
	public  void enter(String fieldLable, WebElement element,String input)
	{
		element.sendKeys(input);
		logReport("PASS", fieldLable+" entered");
	}
	
	public  void clear(String fieldLable, WebElement element)
	{
		element.clear();
		logReport("PASS", fieldLable+" cleared");
	}
	

	//Click on Element
	public  void click(String fieldLable, WebElement element){
		element.click();
		logReport("PASS", "Click on "+fieldLable);
	}
	
	//Select lookup Value
	public void selectLookupValue(String fieldName, WebElement element, String lookupValue) throws InterruptedException
	{
		String basePage = driver.getWindowHandle();
		element.click();
		Thread.sleep(2000);
		Set<String> handles = driver.getWindowHandles(); // get all window handles
	    for(String s : handles)
	    {
	    	String windowHandle = s;
	    	driver.switchTo().window(windowHandle);
	    	if(driver.getTitle().contains("Search "));
	    	break;
	    }
	    
	    WebElement searchFrameName = driver.findElement(By.id("searchFrame"));
    	WebElement resultsFrameName = driver.findElement(By.id("resultsFrame"));
    	driver.switchTo().frame(searchFrameName);
    	driver.findElement(By.xpath("//input[@id='lksrch']")).sendKeys(lookupValue);
    	driver.findElement(By.xpath("//input[@type='submit']")).click();
    	driver.switchTo().defaultContent();
    	driver.switchTo().frame(resultsFrameName);
    	String locater = "//a[contains(text(),'"+lookupValue+"')]";
    	WebElement searchElement = driver.findElement(By.xpath(locater));
    	if(searchElement.isDisplayed())
    	{
    		searchElement.click();
    		logReport("PASS", " Selected Lookup Value "+lookupValue);
    		driver.switchTo().defaultContent();
    	}
    	else
    	{
    		logReport("FAIL", " No Matching Lookup Value "+lookupValue);
    	}
		
		
		/*Iterator<String> iterator = handles.iterator();
	    while (iterator.hasNext())
	    {
	    	driver.switchTo().window(iterator.next());
	        if(driver.getTitle().contains("Search "))
	        {
	        	driver.switchTo().window(iterator.next());
	        }
	        	WebElement searchFrameName = driver.findElement(By.id("searchFrame"));
	        	WebElement resultsFrameName = driver.findElement(By.id("resultsFrame"));
	        	driver.switchTo().frame(searchFrameName);
	        	driver.findElement(By.xpath("//input[@id='lksrch']")).sendKeys(lookupValue);
	        	driver.findElement(By.xpath("//input[@type='submit']")).click();
	        	driver.switchTo().defaultContent();
	        	driver.switchTo().frame(resultsFrameName);
	        	String locater = "//a[contains(text(),'"+lookupValue+"')]";
	        	WebElement searchElement = driver.findElement(By.xpath(locater));
	        	if(searchElement.isDisplayed())
	        	{
	        		searchElement.click();
	        		logReport("PASS", " Selected Lookup Value "+lookupValue);
	        		driver.switchTo().defaultContent();
	        	}
	        	else
	        	{
	        		logReport("FAIL", " No Matching Lookup Value "+lookupValue);
	        	}
	        	break;
	        }
*/	    driver.switchTo().window(basePage);
	}

	//Verify Element Present
	public boolean verifyElementPresent(String fieldLable, WebElement element){
		boolean elementPresent=false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		try{
			elementPresent=element.isDisplayed();
			if(elementPresent)
			{
				Utility.highlightElement(driver,element);
				logReport("PASS", fieldLable+" is present and Displayed");
			}
			else
			{
				logReport("ERROR", fieldLable+" is Not Present or Not Displayed");
			}
		}
		catch(Exception e)
		{
			logReport("ERROR","Element is Not Present or Not Displayed");
			//Assert.fail();
		}
		return elementPresent;

	}
	
	public String getID()
	{
		String url=driver.getCurrentUrl();
		String id=url.substring(27);
		return id;
	}

	//Verify Element Not Present
	public  void verifyElementNotPresent(WebElement element){
		boolean elementPresent=false;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		try{
			elementPresent=element.isDisplayed();
			if(elementPresent)
			{
				Utility.highlightElement(driver,element);
				logReport("FAIL","Element is Present and Displayed");
				Assert.fail();
			}
			else
			{
				logReport("PASS","Element is Not Present or Not Displayed");
			}
		}
		catch(Exception e)
		{
			logReport("PASS","Element is Not Present or Not Displayed");
		}
	}

	//Wait till new page loads after navigation
	public void waitTill_NewPage_LoadsTitle(WebDriver driver, String expectedPage)
	{
		WebDriverWait wait = new WebDriverWait(driver,90);
		if(expectedPage.equalsIgnoreCase("Home"))
		{
			wait.until(ExpectedConditions.titleContains("Salesforce -"));
			logReport("PASS","Accessed Home Page");
		}

		if(expectedPage.equalsIgnoreCase("Campaigns"))
		{
			wait.until(ExpectedConditions.titleContains("Campaigns: Home ~ Salesforce -"));
			logReport("PASS","Accessed Campaigns Module");
		}
		if(expectedPage.equalsIgnoreCase("Leads"))
		{
			wait.until(ExpectedConditions.titleContains("Leads: Home ~ Salesforce -"));
			logReport("PASS","Accessed Leads Module");
		}
		if(expectedPage.equalsIgnoreCase("Accounts"))
		{
			wait.until(ExpectedConditions.titleContains("Accounts: Home ~ Salesforce -"));
			logReport("PASS","Accessed Accounts Module");
		}

		if(expectedPage.equalsIgnoreCase("Contacts"))
		{	
			wait.until(ExpectedConditions.titleContains("Contacts: Home ~ Salesforce -"));
			logReport("PASS","Accessed Contacts Module");
		}

		if(expectedPage.equalsIgnoreCase("Cases"))
		{	
			wait.until(ExpectedConditions.titleContains("Defect Management: Home ~ Salesforce -"));
			logReport("PASS","Accessed Defect Management Module");
		}

		if(expectedPage.equalsIgnoreCase("Opportunities"))
		{
			wait.until(ExpectedConditions.titleContains("Opportunities: Home ~ Salesforce -"));
			logReport("PASS","Accessed Opportunities Module");
		}

		if(expectedPage.equalsIgnoreCase("Dashboard"))
		{
			wait.until(ExpectedConditions.titleContains("Dashboard: Marketing Dashboard ~ Salesforce -"));
			logReport("PASS","Accessed Dashboard Module");
		}
	}

	public void logReport(String status, String message)
	{
		if(status.equalsIgnoreCase("PASS"))
		{
			testReport.log(LogStatus.PASS, message);
			log.info(message);
			
		}

		else if(status.equalsIgnoreCase("FAIL"))
		{
			//Workshop
			testReport.log(LogStatus.FAIL, message);
			log.error(message);
			Assert.fail();
		}

		else if(status.equalsIgnoreCase("INFO"))
		{
			testReport.log(LogStatus.INFO, message);
			log.info(message);
		}
		else if(status.equalsIgnoreCase("WARNING"))
		{
			testReport.log(LogStatus.WARNING, message);
			log.warn(message);
		}
		
		else if(status.equalsIgnoreCase("ERROR"))
		{
			testReport.log(LogStatus.ERROR, message);
			log.error(message);
		}
		
		
	}
}
