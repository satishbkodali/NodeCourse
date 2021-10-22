package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class GetDataFromExcel {
	
	private static String s = "Sheet1";
	
	private static String s1 = "C:\\Users\\Vinod\\OneDrive\\Desktop\\testdata1.xls";
	

	

	DataFormatter objDefaultFormat = new DataFormatter();
	
	
	private static Map<String, String> map = new HashMap<>();

	public String get(String testcasename, String columnname) throws IOException {


		if (s1.endsWith(".xls")) {
			FileInputStream fis;
			int k = 0;
			try {
				fis = new FileInputStream(s1);
				HSSFWorkbook wb = new HSSFWorkbook(fis);
				FormulaEvaluator objFormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
				HSSFSheet ws = wb.getSheet(s);
				

				int rows = ws.getPhysicalNumberOfRows();
				for (int i = 0; i < rows; i++) {
					int cols = ws.getRow(0).getPhysicalNumberOfCells();
					for (int j = 0; j < cols; j++) {
						if (ws.getRow(i).getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", "")
								.equalsIgnoreCase(columnname)) {
							k = j;
						}
					    Cell cellValue = ws.getRow(i).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK);
						 objFormulaEvaluator.evaluate(cellValue); // This will evaluate the cell, And any type of cell will return string value
						    String cellValueStr = objDefaultFormat.formatCellValue(cellValue,objFormulaEvaluator);
						    System.out.println("Test case name"+cellValueStr);
						    Cell cellValue1 = ws.getRow(i).getCell(k, MissingCellPolicy.CREATE_NULL_AS_BLANK);
							 objFormulaEvaluator.evaluate(cellValue1); // This will evaluate the cell, And any type of cell will return string value
							    String cellValueStr1 = objDefaultFormat.formatCellValue(cellValue1,objFormulaEvaluator);
							    System.out.println("Test case name"+cellValueStr1);   
						    
						    map.put(cellValueStr, cellValueStr1);
							/*
							 * map.put(ws.getRow(i).getCell(0,
							 * MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", ""),
							 * ws.getRow(i).getCell(k,
							 * MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().replace(".0",
							 * ""));
							 */
						System.out.println("Test case name"+map);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (s1.endsWith(".xlsx")) {
			FileInputStream fis;
			int k = 0;
			try {
				fis = new FileInputStream(s1);
				XSSFWorkbook wb = new XSSFWorkbook(fis);
				XSSFSheet ws = wb.getSheet(s);
				int rows = ws.getPhysicalNumberOfRows();
				for (int i = 0; i < rows; i++) {
					int cols = ws.getRow(0).getPhysicalNumberOfCells();
					for (int j = 0; j < cols; j++) {
						if (ws.getRow(i).getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", "")
								.equalsIgnoreCase(columnname)) {
							k = j;
						}
						map.put(ws.getRow(i).getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0",
								""),
								ws.getRow(i).getCell(k, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0",
										""));

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Test case name"+map.get(testcasename));
		return map.get(testcasename);
	}
}