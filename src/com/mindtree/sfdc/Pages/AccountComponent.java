package com.mindtree.sfdc.Pages;

import java.beans.Expression;
import java.beans.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mindtree.sfdc.generic.Excel;
import com.mindtree.sfdc.generic.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class AccountComponent extends BasePage
{
	@FindBy(xpath="//*[@id='acc2']")
	private WebElement accountName;

	public AccountComponent(WebDriver driver, ExtentTest testReport) 
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}

	////Method to create Account using UI
	public String createAccount(ArrayList<Map<String, String>> data) throws Exception
	{
		for(int i=0; i<data.size(); i++)
		{

			String accurlExt = "001/o";
			AccessTab(accurlExt);
			waitTill_NewPage_LoadsTitle(driver, "Accounts");
			click("New Button", newButton);
			verifyElementPresent("Edit Account",createRecordPage);
			enter("Account Name", accountName, data.get(i).get("AccountName") + Utility.getFormatedDateTime());
			testReport.log(LogStatus.PASS, "Added Accounts Details");
			click("Save Button", saveButton);
			verifyElementPresent("Account Details ",recordDetailsPage);
			Excel.setOutputResults("Account",new ArrayList<String>(data.get(i).values()),i, getID(),"Pass");
		}
		return getID();
	}

	public void updateAccount(String id)
	{
		AccessTab(id);
		click("Edit Button", editButton);
		clear("Account Name", accountName);
		enter("Account Name", accountName, "Updated Account Name"+ Utility.getFormatedDateTime());
		testReport.log(LogStatus.PASS, "Updated Accounts Details");
		click("Save Button", saveButton);
		//verifyElementPresent("Account Details ",recordDetailsPage);
	}
}