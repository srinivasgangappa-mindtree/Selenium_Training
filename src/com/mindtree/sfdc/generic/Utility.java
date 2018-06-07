package com.mindtree.sfdc.generic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.mindtree.sfdc.Script.AutomationConstants;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;

import javax.imageio.ImageIO;

public class Utility implements AutomationConstants
{
	//Method used to decript the passwords
	public static String decode(String passwordToHash)
	{
		return StringUtils.newStringUtf8(org.apache.commons.codec.binary.Base64.decodeBase64(passwordToHash));
	}

	/**
     * Method to read the excel data
     * 
     * @param File and Sheetname
     * @return List of SFDC ids in the excel
     */
    
    public static String createFile(String folderlePath) throws Exception
    {
        String resultPath=null;
        java.util.Date date= new java.util.Date();
        System.out.println(folderlePath);
        resultPath=folderlePath+"Results_"+date.toString().replace(":", "_");        
        boolean file=new File(resultPath).mkdir();        
        return resultPath;
    }
	
	//Method used to lanuch the application
	public static WebDriver LaunchApplication(WebDriver driver)
	{
		if(Property.getPropertyValue(configPptPath,"Browser").equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", AutomationConstants.chromeDriverPath);
			driver = new ChromeDriver();
		}
		else if(Property.getPropertyValue(configPptPath,"Browser").equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", AutomationConstants.firefoxDriverPath);
			driver = new FirefoxDriver();
		}
		else if(Property.getPropertyValue(configPptPath,"Browser").equalsIgnoreCase("IE"))
		{
			System.setProperty("webdriver.ie.driver", AutomationConstants.IEDriverPath);
			driver = new InternetExplorerDriver();
		}
		driver.manage().deleteAllCookies();
		String appURL=Property.getPropertyValue(configPptPath,"URL");
		String timeOut=Property.getPropertyValue(configPptPath,"TimeOut");
		driver.get(appURL);
		driver.manage().timeouts().implicitlyWait(Long.parseLong(timeOut),TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("logo")).isDisplayed();
		
		return driver;
	}

	//Method used to login to the application
	public static void loginToApp(WebDriver driver)
	{
		if(driver.findElement(By.id("logo")).isDisplayed())
		{
			String un=Property.getPropertyValue(configPptPath,"userName");
			String encryptedPW=Property.getPropertyValue(configPptPath,"password");
			driver.findElement(By.id("username")).sendKeys(un);
			driver.findElement(By.id("password")).sendKeys(Utility.decode(encryptedPW));
			driver.findElement(By.id("Login")).click();
			if(driver.findElement(By.xpath("//span[@id='userNavLabel']")).isDisplayed())
			{
				System.out.println("login successfull");
			}
			else
			{
				System.out.println("login failed");
			}
		}
		else
		{
			System.out.println("Unable to reach Login page");
		}
	}


	//Method used to highlight the Element
	public static void highlightElement(WebDriver driver,WebElement element)
	{
		String presentColor=element.getCssValue("backgroundColor");
		String newCoclor="rgb(255,255,0)";

		for(int i=1;i<=3;i++)
		{
			((JavascriptExecutor)driver).executeScript("arguments[0].style.backgroundColor='"+newCoclor+"'",element);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.backgroundColor='"+presentColor+"'",element);
		}
	}

	//Verify that the value is present in dropdown list vf
	public static boolean isOptionPresentinListBox(WebElement listBox,String option)
	{
		List<WebElement> optionElement = listBox.findElements(By.xpath("./option[text()='"+option+"']"));
		if(optionElement.size()==0){
			return false;}
		else{
			return true;
		}
	}

	//Verify that the Listbox is sorted or not
	public static boolean isListBoxSorted(WebElement listBox)
	{
		List<WebElement> allOptions = new Select(listBox).getOptions();
		ArrayList<String> allText=new ArrayList<String>();
		for(WebElement option:allOptions)
		{
			allText.add(option.getText());
		}
		ArrayList<String> allTextSorted=new ArrayList<String>(allText);
		Collections.sort(allTextSorted);
		//System.out.println("BEFORE SORT<-->AFTER SORT\n--------------------------");
		for(int i=0;i<allText.size();i++)
		{
			if(allText.get(i).equals(allTextSorted.get(i))){
				System.out.println(allText.get(i)+"<-->"+allTextSorted.get(i));
			}
			else
			{
				System.err.println(allText.get(i)+"<-->"+allTextSorted.get(i));
			}
		}
		//System.out.println("--------------------------");
		return allText.equals(allTextSorted);
	}

	//Get Color to highlight a webElement.
	public static String convertRGBtoHex(String strRGB)
	{
		String hex="";
		List<Integer> rgb=new ArrayList<Integer>();
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(strRGB);
		while(m.find())
		{
			int v=Integer.parseInt(m.group());
			rgb.add(v);
		}

		int red=rgb.get(0);
		int green=rgb.get(1);
		int blue=rgb.get(2);
		hex = String.format("#%02x%02x%02x",red, green,blue);
		return hex; 
	}

	//Get Current Date and time
	public static String getFormatedDateTime()
	{
		SimpleDateFormat sd = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		return sd.format(new Date());
	}

	//Get Current Date in SFDC Date format
	public static String getSFDCDateFormated()
	{
		SimpleDateFormat sd = new SimpleDateFormat("MM/dd/yyyy");
		return sd.format(new Date());
	}

	//Get Screenshot
	public static String getPageScreenShot(WebDriver driver,String imageFolderPath)
	{
		String imagePath=imageFolderPath+"/"+getFormatedDateTime()+".png";
		EventFiringWebDriver edriver=new EventFiringWebDriver(driver);
		try{
			FileUtils.copyFile(edriver.getScreenshotAs(OutputType.FILE),new File(imagePath));
		}
		catch(Exception e)
		{}
		return imagePath;
	}

	//Get Croped Image
	public static String cropImage(String imagePath,int x,int y,int width,int height,String OutputImgFolder)
	{
		String newImagePath=OutputImgFolder+"/"+getFormatedDateTime()+".png";
		try{
			BufferedImage originalImgage = ImageIO.read(new File(imagePath));
			BufferedImage subImgage = originalImgage.getSubimage(x,y,width,height);
			ImageIO.write(subImgage,"png", new File(newImagePath));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return newImagePath;
	}

	//Compare two images
	public static boolean compareImage( String actualImage,String expectedImage) {        
		try {
			DataBuffer eImg = ImageIO.read(new File(expectedImage)).getData().getDataBuffer();
			int sizeA = eImg.getSize();         
			DataBuffer aImg = ImageIO.read(new File(actualImage)).getData().getDataBuffer();
			int sizeB = aImg.getSize();
			if(sizeA != sizeB) return false;
			for(int i=0; i<sizeA; i++) 
				if(eImg.getElem(i) != aImg.getElem(i)) return false;
		} 
		catch (Exception e){return  false;}
		return true;
	}
}
