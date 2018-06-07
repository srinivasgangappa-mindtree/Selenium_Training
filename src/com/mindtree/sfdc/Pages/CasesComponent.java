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

public class CasesComponent extends BasePage{

	@FindBy(id="cas7")
	private WebElement status;

	@FindBy(xpath="//*[@id='cas11']")
	private WebElement origin;

	@FindBy(id="cas14")
	private WebElement subject;
	
	@FindBy(id="cas4")
	private WebElement AccountName;

	@FindBy(xpath="//a[@id='cke_18']/span[1]")
	private WebElement attachmentIcon;

	@FindBy(xpath="//input[@name='file']")
	private WebElement browseattachmentBtn;
	
	@FindBy(id="cke_83_label")
	private WebElement insert_btn;
	
	public CasesComponent(WebDriver driver, ExtentTest testReport) 
	{
		super(driver, testReport);
		PageFactory.initElements(driver, this);
	}

	//Method to create Case using UIs
	public void createCases(ArrayList<Map<String, String>> data, String p) throws Exception
	{
		for(int i=0; i<data.size(); i++)
		{
			String caseurlExt = "500/o";
			AccessTab(caseurlExt);
			waitTill_NewPage_LoadsTitle(driver, "Cases");
			click("New Button", newButton);
			verifyElementPresent("Edit Case", createRecordPage);
			enter("Account Name ",AccountName, data.get(i).get("AccountName"));
			enter("Status ",status, data.get(i).get("Status"));
			enter("Origin ",origin, data.get(i).get("Origin"));
			enter("Subject",subject, data.get(i).get("Subject"));
			//click("Attachment field", attachmentIcon);
			//enter("Browser Button ", browseattachmentBtn,p);
			//click("insert ", insert_btn);
			testReport.log(LogStatus.PASS, "Added Case Details");
			click("Save Button", saveButton);
			//verifyElementPresent("Case Details", recordDetailsPage);
			testReport.log(LogStatus.PASS,"Defect Created Successfully");
			Excel.setOutputResults("Defect",new ArrayList<String>(data.get(i).values()), i, getID(),"Pass");
			
		}
	}
}
