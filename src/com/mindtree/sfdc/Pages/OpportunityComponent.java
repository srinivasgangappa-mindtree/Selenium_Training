package com.mindtree.sfdc.Pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.mindtree.sfdc.Script.AutomationConstants;
import com.mindtree.sfdc.generic.Excel;
import com.mindtree.sfdc.generic.Utility;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class OpportunityComponent extends BasePage
{

	@FindBy(id="opp3")
	private WebElement opportunityName;

	@FindBy(xpath="//*[@id='opp4']")
	private WebElement accountName;

	@FindBy(xpath="//*[@id='opp9']")
	private WebElement closeDate;
	
	@FindBy(xpath="//a[@id='opp4_lkwgt']/img")
	private WebElement lp_accountName;
	

	@FindBy(id="opp11")
	private WebElement stage;

	public OpportunityComponent(WebDriver driver, ExtentTest testReport) 
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}

	//Method to create Opportunity using UI
	public void createOppurtunity(ArrayList<Map<String, String>> OpportunityData) throws Exception
	{
		//String outputFile = Excel.setExcelHeaders(AutomationConstants.outputFilePath,"opportunity");
		for(int i=0; i<OpportunityData.size(); i++)
		{
			String oppturlExt = "006/o";
			AccessTab(oppturlExt);
			waitTill_NewPage_LoadsTitle(driver, "Opportunities");
			click("New Button", newButton);
			verifyElementPresent("Edit Opportunity ", createRecordPage);
			enter("Opportunity", opportunityName, OpportunityData.get(i).get("OppurtunityName") + Utility.getFormatedDateTime());
			//click("AccountName Look up", lp_accountName);
			//selectLookupValue("Account Name", lp_accountName, OpportunityData.get(i).get("AccountName"));
			
			enter("Account Name", accountName, OpportunityData.get(i).get("AccountName"));
			enter("Opportunity Stage", stage, OpportunityData.get(i).get("Stage"));
			enter("Close Date",closeDate, OpportunityData.get(i).get("CloseDate"));
			testReport.log(LogStatus.PASS, "Added Opportunity Details");
			click("Save Button", saveButton);
			if(verifyElementPresent("Opportunity Detail ", recordDetailsPage))
			{
				testReport.log(LogStatus.PASS,"Opportunity Created Successfully");
				
				Excel.setOutputResults("opportunity",new ArrayList<String>(OpportunityData.get(i).values()), i, getID(), "PASS");
				//Excel.setOutputResults("opportunity",testData, i, getID(), "PASS");
			}
			else
			{
				verifyElementPresent("Error Appeared while creating Opportunity", ErrorInvalidData);
				Excel.setOutputResults("opportunity",new ArrayList<String>(OpportunityData.get(i).values()), i,"Error Appeared while creating Opportunity","FAIL");
				//Excel.setOutputResults("opportunity",testData, i, "Error Appeared while creating Opportunity","FAIL");
				Assert.fail();
			}

		}
	}

	}
