package com.mindtree.sfdc.Script;

public interface AutomationConstants
{
	//File path
	String projectPath = System.getProperty("user.dir");
	public static  final String reportFolderPath=projectPath+"/report/";
	public static  final String reportFilePath=projectPath+"/report/result.html";
	public static  final String configPptPath=projectPath+"/config/config.properties";
	public static  final String DataFilePath=projectPath+"/TestData/InputData/Data.xlsx";
	public static  final String controllerPath=projectPath+"/TestData/InputData/Controller.xlsx";
	public static  final String suiteSheet="Suite";
	public static  final String chromeDriverPath= projectPath+"/driver/chromedriver.exe";
	public static  final String firefoxDriverPath= projectPath+"/driver/geckodriver.exe";
	public static  final String IEDriverPath= projectPath+"/driver/IEDriverServer.exe";
	public static final String MailTrigger = projectPath+"/driver/Email.vbs";
	
	public static  final String  ActualImage=projectPath+"/ActualImage";
	public static  final String CsvFolderPath = projectPath+"/csv";
	public static  final String screenShotsFolderPath="./report/screenShots";
	public static  final String outputFilePath=projectPath+"/TestData/OutputResults/TestResults.xls";
	//public static  final String outputFilePath=projectPath+"/OutputResults/"+"TestResults_"+Utility.getFormatedDateTime().replace(":", "_")+".xls";
}