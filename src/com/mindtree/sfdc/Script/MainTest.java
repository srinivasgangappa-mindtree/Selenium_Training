package com.mindtree.sfdc.Script;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class MainTest extends BaseTest
{
	@Test(dataProvider="getScenarios")
	public void testScenarios(String ScenarioName,String ExecutionStatus) throws Exception	
	{
		testReport = eReport.startTest(ScenarioName);
		
		if(ExecutionStatus.equalsIgnoreCase("yes"))
		{
			ExecuteScenarios es = new ExecuteScenarios();
			es.executetest(ScenarioName, testReport);
			
		}
		else
		{
			testReport.log(LogStatus.SKIP,"Execution Status is 'NO'");
			throw new SkipException("Skipping Scenario as execution status is 'NO'");
		}
	}

}
