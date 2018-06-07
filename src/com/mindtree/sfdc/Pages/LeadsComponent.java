package com.mindtree.sfdc.Pages;

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

public class LeadsComponent extends BasePage {
	@FindBy(id="lea13")
	private WebElement leadStatus;

	@FindBy(xpath="//*[@id='name_lastlea2']")
	private WebElement leadName;

	@FindBy(xpath="//*[@id='lea3']")
	private WebElement company;

	@FindBy(xpath="//*[@id='Lead_Tab']/a")
	private WebElement leadtab;

	public LeadsComponent(WebDriver driver, ExtentTest testReport) 
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}
	
	//Method to create Lead using UI
	public void createLeads(ArrayList<Map<String, String>> data) throws Exception
	{

		for(int i=0; i<data.size(); i++)
		{
			String leadurlExt = "00Q/o";
			AccessTab(leadurlExt);
			waitTill_NewPage_LoadsTitle(driver, "Leads");
			click("Popup closing", lignthing);
			click("New Button", newButton);
			verifyElementPresent("Edit Lead", createRecordPage);
			enter("Lead Name ", leadName, data.get(i).get("LeadName") + Utility.getFormatedDateTime());
			enter("Lead Status", leadStatus, data.get(i).get("LeadStatus"));
			enter("Company Name", company, data.get(i).get("Company") + Utility.getFormatedDateTime());
			testReport.log(LogStatus.PASS, "Added Lead Details");
			click("Save Button", saveButton);
			verifyElementPresent("Lead Details", recordDetailsPage);
			testReport.log(LogStatus.PASS,"Case Created Successfully");
			Excel.setOutputResults("Leads",new ArrayList<String>(data.get(i).values()),i, getID(),"Pass");
		}
	}
}
