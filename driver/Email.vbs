'########################################
'Function Name: fnSendEmailFromOutlook
'Description: Sends mail from outlook
'Inputs: To,Cc,Bcc mail id's
'Output: N/A
'Author: Srinivas Gangappa
'Date: 28-june-2017
'########################################
Function fnSendEmailFromOutlook 
	'Create an object of type Outlook
	Set objOutlook = CreateObject("Outlook.Application")
	Set myMail = objOutlook.CreateItem(0)
	With myMail
		.Display
		.To = "srinivas.gangappa@mindtree.com"
		.CC = "srinivas.gangappa@mindtree.com"
		'.BCC = "srinivas.gangappa@mindtree.com"
		.Subject = "Automation Script execution Report"
		.Attachments.Add("C:\Users\m1040467\git\SalesForce\report\result.html")
		.Body= "Auto generated mail for the Test Results run using Selenium"
		.Send
	End With
	'Clear object reference
	Set myMail = Nothing
	Set objOutlook = Nothing
End Function

call fnSendEmailFromOutlook