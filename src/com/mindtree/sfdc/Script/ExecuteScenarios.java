package com.mindtree.sfdc.Script;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mindtree.sfdc.Pages.AccountComponent;
import com.mindtree.sfdc.Pages.CampaignsComponent;
import com.mindtree.sfdc.Pages.CasesComponent;
import com.mindtree.sfdc.Pages.LeadsComponent;
import com.mindtree.sfdc.Pages.OpportunityComponent;
import com.mindtree.sfdc.generic.Excel;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExecuteScenarios extends BaseTest
{
	//Method to execute the Test commit changes
	public void executetest(String Scenario, ExtentTest testReport) throws Exception
	{
		switch(Scenario)
		{  
		case "CreateCampaign": 
		{
			testReport.log(LogStatus.INFO,"Test Creating Campaign");
			ArrayList<Map<String, String>> campaignData = Excel.getSheetData(AutomationConstants.DataFilePath,"Campaign");
			CampaignsComponent cc = new CampaignsComponent(driver, testReport);
			cc.createCampaign(campaignData);
			break;
		}

		case "CreateAccount": 
		{
			testReport.log(LogStatus.INFO,"Creating Account");
			ArrayList<Map<String, String>> AccountData = Excel.getSheetData(AutomationConstants.DataFilePath,"Account");
			AccountComponent aa = new AccountComponent(driver, testReport);
			aa.createAccount(AccountData);
			break;
		}
		/*case "CreateCase": 
		{
			testReport.log(LogStatus.INFO,"Creating Case");
			ArrayList<Map<String, String>> CaseData = Excel.getSheetData(AutomationConstants.DataFilePath,"Case");
			CasesComponent c = new CasesComponent(driver, testReport);
			c.createCases(CaseData);
			break;

		}*/
		case "CreateLead": 
		{
			testReport.log(LogStatus.INFO,"Creating Lead");
			ArrayList<Map<String, String>> LeadData = Excel.getSheetData(AutomationConstants.DataFilePath,"Leads");
			LeadsComponent l = new LeadsComponent(driver, testReport);
			l.createLeads(LeadData);
			break;

		}
		case "CreateOpportunity":
		{
			testReport.log(LogStatus.INFO,"Creating Opportunity");
			ArrayList<Map<String, String>> OppurtunityData = Excel.getSheetData(AutomationConstants.DataFilePath,"Opportunity");
			OpportunityComponent o = new OpportunityComponent(driver, testReport);
			o.createOppurtunity(OppurtunityData);
			break;
		}
		
		case "UpdateAccount":
		{
			testReport.log(LogStatus.INFO,"Update Account");
			ArrayList<Map<String, String>> AccountData = Excel.getFirstRowData(AutomationConstants.DataFilePath,"Account");
			AccountComponent aa = new AccountComponent(driver, testReport);
			String id = aa.createAccount(AccountData);
			aa.updateAccount(id);
			break;
		}
		
		default:
		{
			testReport.log(LogStatus.INFO,"Test Not matching with any scenario");;
		}
		}
	}
}


