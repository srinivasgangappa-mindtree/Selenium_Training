package com.mindtree.sfdc.Pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mindtree.sfdc.Script.AutomationConstants;
import com.mindtree.sfdc.generic.Excel;
import com.mindtree.sfdc.generic.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CampaignsComponent extends BasePage
{
	
	@FindBy(id="cpn1")
	private WebElement campaignName;

	@FindBy(id="cpn16")
	private WebElement ActivationStatus;

	public CampaignsComponent(WebDriver driver, ExtentTest testReport) 
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}

	
	//Method to create Campaign using UI
	public void createCampaign(ArrayList<Map<String, String>> data) throws Exception
	{
		for(int i=0; i<data.size(); i++)
		{	
			String campaingurlExt = "701/o";
			AccessTab(campaingurlExt);
			waitTill_NewPage_LoadsTitle(driver, "Campaigns");
			click("New Button", newButton);
			verifyElementPresent("Edit Campaign", createRecordPage);
			enter("Campaign Name", campaignName, data.get(i).get("CampaignName") + Utility.getFormatedDateTime());
			selectCheckBoxe(ActivationStatus);
			testReport.log(LogStatus.PASS, "Added Accounts Details");
			click("Save Button", saveButton);
			verifyElementPresent("Campaign Details", recordDetailsPage);
			Excel.setOutputResults("Campaign",new ArrayList<String>(data.get(i).values()),i, getID(),"Pass");
		}
	}

}
