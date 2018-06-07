package com.mindtree.sfdc.generic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mindtree.sfdc.Script.AutomationConstants;

public class Excel 
{
	//Used to create output file
		static XSSFWorkbook opwb = new XSSFWorkbook();
	//Get number of Rows present in Excel
	public static int getRowCount(String path,String sheet)
	{
		int r=0;
		try{
			r=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getLastRowNum();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//System.err.println("Exception in counting Excel row");
		}
		return r;
	}
	
	//get last row number
	public static int getlastRowNumber(String path,String sheet)
	{
		int i=0;
		try{

			i=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getLastRowNum();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}

		
		return i;
	
	}
	
	//Get number of Columns present in Excel
	public static int getColumnCount(String path,String sheet)
	{
		int c=0;
		try{

			c=WorkbookFactory.create(new FileInputStream(path)).getSheet(sheet).getRow(0).getLastCellNum();
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		return c;
	}

	//Get Cell Value
	public static String getCellValue(String path,String sheet,int r,int c)
	{
		String v="";
		try{


			Workbook wb = WorkbookFactory.create(new FileInputStream(path));
			Cell cell = wb.getSheet(sheet).getRow(r).getCell(c);
			if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
				FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
				CellValue cv = evaluator.evaluate(cell);
				v=cv.getStringValue();
				System.out.println("Cell has formula; Returning:"+v);
			}
			else{
				v=cell.toString();
				//				System.out.println("Cell has No formula; Returning:"+v);
			}
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			//System.err.println("Exception in reading cell");
		}
		return v;
	}

	//Get First Row Data from Data Sheet
	public static ArrayList<Map<String, String>> getFirstRowData(String filepath, String sheet)
	{
		ArrayList<Map<String,String>> records=new ArrayList<Map<String,String>>();
		int columnCount= Excel.getColumnCount(filepath,sheet);
		ArrayList<String> headers=new ArrayList<String>();
		for(int i=0;i<=columnCount;i++)
		{
			headers.add(Excel.getCellValue(filepath, sheet, 0, i));
		}
		Map<String,String> datas=new HashMap<String,String>();
		for(int j=0;j<columnCount;j++)
		{
			datas.put(headers.get(j).toString(), Excel.getCellValue(filepath,sheet,1,j));
		}
		records.add(datas);

		return records;
	}

	//Get header Names
	public static ArrayList<String> getSheetHeaders(String filepath, String sheet)
	{
		int columnCount= Excel.getColumnCount(filepath,sheet);
		ArrayList<String> headers=new ArrayList<String>();
		for(int i=0;i<=columnCount;i++)
		{
			headers.add(Excel.getCellValue(filepath, sheet, 0, i));
		}
		return headers;
	}

	//Get All data from Data sheet
	public static ArrayList<Map<String, String>> getSheetData(String filepath, String sheet)
	{
		ArrayList<Map<String,String>> records=new ArrayList<Map<String,String>>();
		int rowCount=Excel.getRowCount(filepath,sheet);
		int columnCount= Excel.getColumnCount(filepath,sheet);
		ArrayList<String> headers = Excel.getSheetHeaders(filepath, sheet);

		for(int i=1;i<=rowCount;i++)
		{
			Map<String,String> datas=new HashMap<String,String>();
			for(int j=0;j<columnCount;j++)
			{
				datas.put(headers.get(j).toString(), Excel.getCellValue(filepath,sheet,i,j));
			}
			records.add(datas);
		}

		return records;
	}

	/**
	 * Method to read the excel data
	 * 
	 * @param File and Sheetname
	 * @return List of SFDC ids in the excel
	 */

	public static void writeResult(String filePath, Map<String,String> resultMap, int row, String sheet) throws Exception
	{        
		try {    

			//incrementing row value to match the sheet row
			row++;

			Workbook wb = WorkbookFactory.create(new FileInputStream(filePath));
			Row tempRow = wb.getSheet(sheet).getRow(row);
			Cell cell = null;

			//Creating the cell data to write
			cell=tempRow.createCell(0);
			cell.setCellValue(resultMap.get("ID"));
			cell=tempRow.createCell(1);
			cell.setCellValue(resultMap.get("Test Case Purpose"));
			cell=tempRow.createCell(2);
			cell.setCellValue(resultMap.get("Result"));
			cell=tempRow.createCell(3);
			//Trimming the reason for failure if the size is greater than 32767
			if(resultMap.get("Reason for Failure").length()>32767)
			{
				cell.setCellValue(resultMap.get("Reason for Failure").substring(0, 32767-3)+"...");
			}
			else
			{
				cell.setCellValue(resultMap.get("Reason for Failure"));
			}

			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(filePath);
			wb.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to read the excel data
	 * 
	 * @param File and Sheetname
	 * @return List of SFDC ids in the excel
	 */

	public static void setExcelHeaders(String sheetName) throws Exception
	{        
		try {
			
			Sheet tempSheet = opwb.createSheet(sheetName);
			Row tempRow = tempSheet.createRow(0);
			//Row tempRow = wb.getSheet(sheetName).createRow(0);
			Cell cell = null;
			ArrayList<String> HeaderName = Excel.getSheetHeaders(AutomationConstants.DataFilePath, sheetName);
			int tD = HeaderName.size();
			for(int i=0; i<tD; i++)
			{
				cell=tempRow.createCell(i);
				cell.setCellValue(HeaderName.get(i));
			}
			tempRow.createCell(tD).setCellValue("Result");
			tempRow.createCell(tD-1).setCellValue("Status");


			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(AutomationConstants.outputFilePath);
			opwb.write(out);
			out.close();
		

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getSheetCount(String filepath) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException
	{
		Workbook wb = WorkbookFactory.create(new FileInputStream(filepath));
		int sc = wb.getNumberOfSheets();
		ArrayList<String> sheetNames = new ArrayList<String>();
		for(int i=0; i<sc; i++)
		{
			sheetNames.add(wb.getSheetName(i));
		}
		return sheetNames;
	}

	/*public static void updateExcel(String outputfilepath, String sheetName, List<String> testData, int rowNum, String ID) throws Exception
	{
		Workbook wb = WorkbookFactory.create(new FileInputStream(outputfilepath));
		for(int i=0; i<wb.getNumberOfSheets(); i++)
		{
			if(wb.getSheetName(i).equalsIgnoreCase(sheetName))
			{
				setOutputResults(outputfilepath, sheetName, testData, rowNum, ID, status);
				break;
			}
			else
			{
				setExcelHeaders(outputfilepath, sheetName);
				setOutputResults(outputfilepath, sheetName, testData, rowNum, ID);
				
			}
		}
	}*/
	
	public static void setOutputResults(String sheetName, List<String> testData, int rowNum, String result,String status) throws Exception
	{
		if(rowNum==0)
		{
			setExcelHeaders(sheetName);
		}
		rowNum++;
		try {
			Row tempRow = opwb.getSheet(sheetName).createRow((short)rowNum);
			Cell cell = null;			
			int tD = testData.size();
			for(int i=0; i<testData.size(); i++)
			{
				cell=tempRow.createCell(i);
				cell.setCellValue(testData.get(i));
			}
			tempRow.createCell(tD+1).setCellValue(result);
			tempRow.createCell(tD).setCellValue(status);


			//Write the workbook in file system
			FileOutputStream out = new FileOutputStream(AutomationConstants.outputFilePath);
			opwb.write(out);
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
