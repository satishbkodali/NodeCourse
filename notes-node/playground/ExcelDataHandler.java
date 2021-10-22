package utilities;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.LogManager;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelDataHandler {

	// private static Logger logger = LogManager.getLogger();
	private HSSFWorkbook xlsWorkbook;
	private XSSFWorkbook xlsxWorkbook;
	private LinkedHashMap<String, List<List<String>>> RuntimeData = new LinkedHashMap<String, List<List<String>>>();

	public ExcelDataHandler(String excelFilePath) throws Exception {
		FileInputStream inputstream;
		try {
			inputstream = new FileInputStream(new File(excelFilePath));
			if (excelFilePath.endsWith(".xls")) {
				this.xlsWorkbook = new HSSFWorkbook(inputstream);
			} else if (excelFilePath.endsWith(".xlsx")) {
				this.xlsxWorkbook = new XSSFWorkbook(inputstream);
			} else {
				throw new Exception("Incorrect File Type");
			}
		} catch (FileNotFoundException e) {
			throw new Exception("File path is incorrect");
		} catch (IOException e) {
			throw new Exception("Error loading file");
		} catch (Exception e) {
			throw new Exception("Unable to read excel");
		}

	}

	public ExcelDataHandler(String excelFilePath, String sheetName) throws Exception {
		FileInputStream inputstream;
		try {
			inputstream = new FileInputStream(new File(excelFilePath));
			if (excelFilePath.endsWith(".xls")) {
				this.xlsWorkbook = new HSSFWorkbook(inputstream);
				this.RuntimeData = LoadExcelDataSheet(xlsWorkbook.getSheet(sheetName));
			} else if (excelFilePath.endsWith(".xlsx")) {
				this.xlsxWorkbook = new XSSFWorkbook(inputstream);
				this.RuntimeData = LoadExcelDataSheet(xlsxWorkbook.getSheet(sheetName));
			} else {
				throw new Exception("Incorrect File Type");
			}
		} catch (FileNotFoundException e) {
			throw new Exception("File path is incorrect");
		} catch (IOException e) {
			throw new Exception("Error loading file");
		} catch (Exception e) {
			throw new Exception("Unable to read excel");
		}

	}

	public ExcelDataHandler(String excelFilePath, int sheetIndex) throws Exception {
		FileInputStream inputstream;
		try {
			inputstream = new FileInputStream(new File(excelFilePath));
			if (excelFilePath.endsWith(".xls")) {
				this.xlsWorkbook = new HSSFWorkbook(inputstream);
				this.RuntimeData = LoadExcelDataSheet(xlsWorkbook.getSheetAt(sheetIndex));
			} else if (excelFilePath.endsWith(".xlsx")) {
				this.xlsxWorkbook = new XSSFWorkbook(inputstream);
				this.RuntimeData = LoadExcelDataSheet(xlsxWorkbook.getSheetAt(sheetIndex));
			} else {
				throw new Exception("Incorrect File Type");
			}
		} catch (FileNotFoundException e) {
			throw new Exception("File path is incorrect");
		} catch (IOException e) {
			throw new Exception("Error loading file");
		} catch (Exception e) {
			throw new Exception("Unable to read excel");
		}

	}

	public LinkedHashMap<String, List<List<String>>> LoadExcelDataSheet(Sheet dataSheet) {
		String sheetName = dataSheet.getSheetName();
		List<List<String>> data = new ArrayList<List<String>>();
		int rowCount = dataSheet.getLastRowNum();
		for (int i = 0; i <= rowCount; i++) {
			List<String> row = new ArrayList<String>();
			int cellCount = dataSheet.getRow(0).getLastCellNum();
			for (int j = 0; j < cellCount; j++) {
				Cell cell = dataSheet.getRow(i).getCell(j);
				if (cell != null) {
					row.add(cell.toString().trim());

				} else {
					row.add("");
				}

			}
			data.add(row);
		}
		RuntimeData.put(sheetName, data);
		return RuntimeData;
	}

	public int getColumnNumber(Sheet sheet, String columnName) {
		int j = 0;
		do {
			if (columnName.equals(sheet.getRow(0).getCell(j).toString().trim())) {
				break;
			}
			j++;
		} while (sheet.getRow(0).getCell(j) != null);
		return j;

	}

	public int getRowNumber(Sheet sheet, String label) {
		int j = 0;
		for (; j <= sheet.getLastRowNum(); j++) {
			if (label.equals(sheet.getRow(j).getCell(0).toString().trim())) {
				return j;
			}
		}
		return j;
	}

	public List<String> getSheetNames(HSSFWorkbook wBook) {
		List<String> sheetNames = new ArrayList<String>();
		for (int i = 0; i < wBook.getNumberOfSheets(); i++) {
			sheetNames.add(wBook.getSheetName(i));
		}
		return sheetNames;
	}

	public List<String> getSheetNames(XSSFWorkbook wBook) {
		List<String> sheetNames = new ArrayList<String>();
		for (int i = 0; i < wBook.getNumberOfSheets(); i++) {
			sheetNames.add(wBook.getSheetName(i));
		}
		return sheetNames;
	}

	public LinkedHashMap<String, String> getRowColumnMapDefaultHeader(List<List<String>> dataRows, int rowNum) {
		return getRowColumnMapWithHeaderRowNumber(dataRows, 0, rowNum);
	}

	public LinkedHashMap<String, String> getRowColumnMapWithHeaderRowNumber(List<List<String>> dataRows, int headerRow,
			int rowNum) {
		// map row data to column name
		List<String> columns = dataRows.get(headerRow);
		LinkedHashMap<String, String> rowColumnData = new LinkedHashMap<String, String>();
		for (int j = 0; j < columns.size(); j++) {
			try {
				rowColumnData.put(columns.get(j).trim(), dataRows.get(headerRow + rowNum).get(j).trim());
			} catch (Exception e) {
				rowColumnData.put(columns.get(j).trim(), null);
			}
		}
		return rowColumnData;
	}

	public static ArrayList<LinkedHashMap<String, String>> getAllRowsColumnMapWithHeaderRowNumber(
			List<List<String>> dataRows, int headerRow) {
		// map row data to column name
		List<String> columns = dataRows.get(headerRow);
		LinkedHashMap<String, String> rowColumnData = null;
		ArrayList<LinkedHashMap<String, String>> allRowsColumnData = new ArrayList<LinkedHashMap<String, String>>();
		for (int i = 1; i < dataRows.size(); i++) {
			rowColumnData = new LinkedHashMap<String, String>();
			if (headerRow + i == dataRows.size()) {
				break;
			}
			for (int j = 0; j < columns.size(); j++) {
				rowColumnData.put(columns.get(j).trim(), dataRows.get(headerRow + i).get(j).trim());
			}
			allRowsColumnData.add(rowColumnData);
		}
		return allRowsColumnData;
	}

	public int getFirstEligibleRow(List<List<String>> ds) {
		int rowcounter = 0;
		for (List<String> rowList : ds) {
			int counter = 0;
			for (String str : rowList) {
				if (!str.isEmpty()) {
					counter++;
				}
			}
			if (counter == rowList.size()) {
				if (counter > 2) {
					break;
				}
			}
			rowcounter++;
		}
		return rowcounter;
	}

	public int getFirstEligibleRowForXlsx(List<List<String>> ds) {
		int rowcounter = 0;
		for (List<String> rowList : ds) {
			int counter = 0;
			for (String str : rowList) {
				if (!str.isEmpty()) {
					counter++;
				}
			}
			if (counter == rowList.size()) {
				if (counter > 2) {
					break;
				}
			}
			rowcounter++;
		}
		return rowcounter;
	}

	public static void updateExcelCell(String path, String sheetName, int row, int column, String value)
			throws Exception {
		FileInputStream file = new FileInputStream(new File(path));
		Cell cell = null;
		if (path.endsWith(".xls")) {
			HSSFWorkbook workBook = new HSSFWorkbook(file);
			HSSFSheet sheet = workBook.getSheet(sheetName);
			cell = sheet.getRow(row).getCell(column, HSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellValue(value);
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(path));
			workBook.write(outFile);
			outFile.close();

		} else if (path.endsWith(".xlsx")) {
			XSSFWorkbook workBook = new XSSFWorkbook(file);
			XSSFSheet sheet = workBook.getSheet(sheetName);
			cell = sheet.getRow(row).getCell(column, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			cell.setCellValue(value);
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(path));
			workBook.write(outFile);
			outFile.close();
		}

	}

	public LinkedHashMap<String, List<List<String>>> getRuntimeData() {
		return RuntimeData;
	}

	public HSSFWorkbook getXlsWorkbook() {
		return xlsWorkbook;
	}

	public void setXlsWorkbook(HSSFWorkbook xlsWorkbook) {
		this.xlsWorkbook = xlsWorkbook;
	}

	public XSSFWorkbook getXlsxWorkbook() {
		return xlsxWorkbook;
	}

	public void setXlsxWorkbook(XSSFWorkbook xlsxWorkbook) {
		this.xlsxWorkbook = xlsxWorkbook;
	}
	
	public static ArrayList<LinkedHashMap<String, String>> getExcelDataMap(int columnNameRow, int startRow, int endRow, String sheetName, String path) throws Exception{
	ExcelDataHandler eDH=new ExcelDataHandler(path);
	List<List<String>> data=new ArrayList<List<String>>();
	List<String> headerData=new ArrayList<String>();
	if(path.endsWith(".xls")) {
		HSSFWorkbook workBook=eDH.getXlsWorkbook();
		HSSFSheet dataSheet=workBook.getSheet(sheetName);
		int cellCount = dataSheet.getRow(0).getLastCellNum();
		 for(int j=0;j<cellCount;j++) {
			 Cell cell=dataSheet.getRow(0).getCell(j);
			  if(cell !=null) {
				 if( cell.getCellTypeEnum().name().equals("STRING")) {
					 headerData.add(cell.getStringCellValue());
				 }else if( cell.getCellTypeEnum().name().equals("NUMERIC")) {
					 headerData.add(String.valueOf(cell.getNumericCellValue()));
				 }else if( cell.getCellTypeEnum().name().equals("FORMULA")) {
					 if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 1){
						 headerData.add(cell.getStringCellValue());
					 }else if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 0) {
						 headerData.add(String.valueOf(cell.getNumericCellValue()));
					 }else {
						 headerData.add(" ");
					 }
				 }else {
					 headerData.add(cell.toString().trim());
				 }
			  }else {
				  headerData.add("");
			  }
		 }
		 data.add(headerData);
		 for(int i= startRow; i <= endRow; i++) {
			 List<String> row= new ArrayList<String>();
			 for(int j=0; j<cellCount;j++) {
				 Cell cell = dataSheet.getRow(i).getCell(j);
				 if(cell !=null) {
					 
					 if( cell.getCellTypeEnum().name().equals("STRING")) {
						 row.add(cell.getStringCellValue());
					 }else if( cell.getCellTypeEnum().name().equals("NUMERIC")) {
						 row.add(String.valueOf(cell.getNumericCellValue()));
					 }else if( cell.getCellTypeEnum().name().equals("FORMULA")) {
						 if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 1){
							 row.add(cell.getStringCellValue());
						 }else if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 0) {
							 row.add(String.valueOf(cell.getNumericCellValue()));
						 }else {
							 row.add(" ");
						 }
					 }else {
						 row.add(cell.toString().trim());
					 }
				  }else {
					  row.add("");
				  }
					 
				 }
			 data.add(row);
			 }
		 } else if(path.endsWith(".xlsx")) {
			 
			 XSSFWorkbook workBook=eDH.getXlsxWorkbook();
				XSSFSheet dataSheet=workBook.getSheet(sheetName);
				int cellCount = dataSheet.getRow(0).getLastCellNum();
				 for(int j=0;j<cellCount;j++) {
					 Cell cell=dataSheet.getRow(0).getCell(j);
					  if(cell !=null) {
						 if( cell.getCellTypeEnum().name().equals("STRING")) {
							 headerData.add(cell.getStringCellValue());
						 }else if( cell.getCellTypeEnum().name().equals("NUMERIC")) {
							 headerData.add(String.valueOf(cell.getNumericCellValue()));
						 }else if( cell.getCellTypeEnum().name().equals("FORMULA")) {
							 if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 1){
								 headerData.add(cell.getStringCellValue());
							 }else if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 0) {
								 headerData.add(String.valueOf(cell.getNumericCellValue()));
							 }else {
								 headerData.add(" ");
							 }
						 }else {
							 headerData.add(cell.toString().trim());
						 }
					  }else {
						  headerData.add("");
					  }
				 }
				 data.add(headerData);
				 for(int i= startRow; i <= endRow; i++) {
					 List<String> row= new ArrayList<String>();
					 for(int j=0; j<cellCount;j++) {
						 Cell cell = dataSheet.getRow(i).getCell(j);
						 if(cell !=null) {
							 
							 if( cell.getCellTypeEnum().name().equals("STRING")) {
								 row.add(cell.getStringCellValue());
							 }else if( cell.getCellTypeEnum().name().equals("NUMERIC")) {
								 row.add(String.valueOf(cell.getNumericCellValue()));
							 }else if( cell.getCellTypeEnum().name().equals("FORMULA")) {
								 if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 1){
									 row.add(cell.getStringCellValue());
								 }else if(workBook.getCreationHelper().createFormulaEvaluator().evaluateFormulaCell(cell) == 0) {
									 row.add(String.valueOf(cell.getNumericCellValue()));
								 }else {
									 row.add(" ");
								 }
							 }else {
								 row.add(cell.toString().trim());
							 }
						  }else {
							  row.add("");
						  }
							 
						 }
					 data.add(row);
					 }
			 
		 }
	return getAllRowsColumnMapWithHeaderRowNumber(data, 0);	
	}
	
}