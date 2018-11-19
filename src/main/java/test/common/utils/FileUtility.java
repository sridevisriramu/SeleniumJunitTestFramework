package test.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtility {

	public static void removeRowsFromInExcelSheet(String path) throws Exception {
		XSSFWorkbook workbook = null;
		try {
			File file = new File(path).getAbsoluteFile();
			FileInputStream inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int count = sheet.getPhysicalNumberOfRows() - 1;
			for (int i = 1; i <= count; i++) {
				Row row = sheet.getRow(i);
				sheet.removeRow(row);
			}

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
		} catch (Exception e) {
			log.error("Error came while reading from excel sheet, Failed");
			throw new Exception(e);
		} finally {
			workbook.close();
		}
	}

	public static void addRowInExcelSheet(String path, String value) throws Exception {
		XSSFWorkbook workbook = null;
		try {
			File file = new File(path).getAbsoluteFile();
			FileInputStream inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);

			int columnNum = sheet.getRow(0).getLastCellNum();

			Row row = sheet.createRow(1);

			for (int i = 0; i < columnNum; i++) {
				Cell cell = row.createCell(i, 1);
				cell.setCellValue(value);
			}

			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
		} catch (Exception e) {
			log.error("Error came while reading from excel sheet, Failed");
			throw new Exception(e);
		} finally {
			workbook.close();
		}
	}

	public static String retrieveDownloadedFilePath(String fileName) {
		String downloadPath = System.getProperty("user.home");
		return downloadPath + "\\Downloads\\" + fileName;
	}

	/**
	 * Count the rows from csv file
	 * 
	 * @param fileName
	 * @return Count of records in file
	 * @throws IOException
	 */
	public static int getRowCountFromCSVFile(String fileName) throws IOException {
		int rowCount = 0;
		try (Reader reader = Files.newBufferedReader(Paths.get(retrieveDownloadedFilePath(fileName)));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			// Row Count of CSV file records
			List<CSVRecord> records = csvParser.getRecords();
			rowCount = records.size() - 1;
		}

		return rowCount;
	}

	/**
	 * get all records value from csv file
	 * 
	 * @param fileName
	 * @return Return arraylist with all records value
	 * @throws IOException
	 */
	public static ArrayList<HashMap<String, String>> getRecordsFromCSVFile(String fileName) throws IOException {
		HashMap<String, String> recordMap = null;
		ArrayList<HashMap<String, String>> recordList = new ArrayList<>();
		try (Reader reader = Files.newBufferedReader(Paths.get(retrieveDownloadedFilePath(fileName)));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			// Row Count of CSV file records
			List<CSVRecord> records = csvParser.getRecords();
			for (int i = 0; i < records.size(); i++) {
				if (i != 0) {
					recordMap = new HashMap<>();
					for (int j = 0; j < records.get(i).size(); j++) {
						recordMap.put(records.get(0).get(j), records.get(i).get(j));
					}
					recordList.add(recordMap);
				}
			}
		}

		return recordList;
	}

	/**
	 * Get header record value from csv file
	 * 
	 * @param fileName
	 * @return return the header record value
	 * @throws IOException
	 */
	public static ArrayList<String> getRecordsHeadFromCSVFile(String fileName) throws IOException {
		ArrayList<String> recordList = new ArrayList<>();
		try (Reader reader = Files.newBufferedReader(Paths.get(retrieveDownloadedFilePath(fileName)));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			// Row Count of CSV file records
			List<CSVRecord> records = csvParser.getRecords();
			for (int i = 0; i < records.get(0).size(); i++) {
				recordList.add(records.get(0).get(i));
			}

		}

		return recordList;
	}

}
