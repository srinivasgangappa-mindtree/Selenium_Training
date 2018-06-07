package com.mindtree.sfdc.Pages;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class BasePage extends BaseLib
{
	@FindBy(xpath="//span[@id='userNavLabel']")
	protected WebElement loggedinUser;
	
	@FindBy(xpath="//input[@title='New']")
	protected WebElement newButton;

	@FindBy(xpath="//input[@title='Edit']")
	protected WebElement editButton;
	
	@FindBy(xpath="//input[@title='Save']")
	protected WebElement saveButton;
	
	@FindBy(id="tsidButton")
	protected WebElement Toprightmenu;
	
	@FindBy(xpath="//div[@class='topLeft']//a")
	protected WebElement lignthing;
	
	@FindBy(xpath="//div[@id='tsid-menuItems']/a[text()='Marketing']")
	protected WebElement marketing;
	
	@FindBy(id="Campaign_Tab")
	protected WebElement campaightab;
	
	@FindBy(id="userNavButton")
	protected WebElement userMenu;
	
	@FindBy(xpath="//a[text()='Logout']")
	protected WebElement LogoutLink;
	
	@FindBy(xpath="//*[@id='AllTab_Tab']/a/img")
	protected WebElement plus;
	
	@FindBy(xpath="//*[@id='Account_Tab']/a")
	protected WebElement accounttab;
	
	@FindBy(xpath="//*[@id='bodyCell']/div[3]/div[2]/table/tbody/tr[6]/td[1]/a")
	protected WebElement Cases;
	
	@FindBy(xpath="//*[@id='Lead_Tab']/a")
	protected WebElement leadtab;
	
	@FindBy(xpath="//*[@id='bodyCell']/div[3]/div[2]/table/tbody/tr[5]/td[2]/a")
	protected WebElement oppurtunitytab;
	
	@FindBy(xpath="//h2[contains(text(),' Detail')]")
	protected WebElement recordDetailsPage;
	
	@FindBy(xpath="//h2[contains(text(),' Edit')]")
	protected WebElement createRecordPage;
	
	@FindBy(xpath="//div[@id='errorDiv_ep']")
	protected WebElement ErrorInvalidData;
	
	public BasePage(WebDriver driver, ExtentTest testReport)
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}
	
	public void ClickUserMenu()
	{
		//unilever Workshop
		userMenu.click();
	}
	
	public void ClickLogoutLink()
	{
		LogoutLink.click();
	}
	
	public void VerifyDashboardTab()
	{
		verifyElementPresent("User Name", loggedinUser);
	}
	
	public void AccessTab(String ext)
	{
		String url="https://ap5.salesforce.com/";
		driver.get(url+ext);
	}
}
