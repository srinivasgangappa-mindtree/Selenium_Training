package com.mindtree.sfdc.Script;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class WIP implements AutomationConstants
{
	   public static void main(String[] args) 
	   {
		  String reportPath = AutomationConstants.MailTrigger;
		  
			   Runtime rt = Runtime.getRuntime();
			   try {
			      Runtime.getRuntime().exec("wscript D:/Automation/SalesForce/SalesForce/driver/Email.vbs");
			   }
			   catch( IOException e ) {
			      e.printStackTrace();
			   }
			
    	
    }

}