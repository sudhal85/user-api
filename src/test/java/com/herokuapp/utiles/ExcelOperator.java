package com.herokuapp.utiles;
//

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ExcelOperator {

	/*
	 * private Workbook workbook; private Sheet sheet; private String filePath;
	 * 
	 * private static ExcelOperator excelOperator;
	 * 
	 * private ExcelOperator() {
	 * 
	 * }
	 * 
	 * public static ExcelOperator getExcelOperator(String filePath, String
	 * sheetName) { if (excelOperator == null) { excelOperator = new
	 * ExcelOperator(filePath, sheetName); } return excelOperator; }
	 * 
	 * 
	 * private ExcelOperator(String filePath, String sheetName) { this.filePath =
	 * filePath; try { FileInputStream excelFile = new FileInputStream(filePath);
	 * workbook = new XSSFWorkbook(excelFile); sheet = workbook.getSheet(sheetName);
	 * } catch (IOException e) { e.printStackTrace(); } }
	 * 
	 * public Map<String, Object> getRowData(int rowNumber) { Map<String, Object>
	 * data = new HashMap<>(); Row row = sheet.getRow(rowNumber); if (row != null) {
	 * for (Cell cell : row) {
	 * data.put((String)getCellValue(sheet.getRow(0).getCell(cell.getColumnIndex()))
	 * , getCellValue(cell)); } } return data; }
	 * 
	 * private Object getCellValue(Cell cell) { if (cell == null) { return ""; }
	 * switch (cell.getCellType()) { case STRING: return cell.getStringCellValue();
	 * case NUMERIC: if (DateUtil.isCellDateFormatted(cell)) { return
	 * cell.getDateCellValue().toString(); } else { return
	 * cell.getNumericCellValue(); } case BOOLEAN: return
	 * cell.getBooleanCellValue(); case FORMULA: return cell.getCellFormula(); case
	 * BLANK: return ""; default: return ""; } }
	 * 
	 * public void writeData(int rowNumber, String columnName, Object value) { int
	 * columnIndex = -1; Row headerRow = sheet.getRow(0);
	 * 
	 * for (Cell cell : headerRow) { if
	 * (cell.getStringCellValue().equalsIgnoreCase(columnName)) { columnIndex =
	 * cell.getColumnIndex(); break; } }
	 * 
	 * if (columnIndex == -1) { throw new IllegalArgumentException("Column " +
	 * columnName + " does not exist in the sheet."); }
	 * 
	 * Row row = sheet.getRow(rowNumber); if (row == null) { row =
	 * sheet.createRow(rowNumber); }
	 * 
	 * Cell cell = row.getCell(columnIndex); if (cell == null) { cell =
	 * row.createCell(columnIndex); }
	 * 
	 * if (value instanceof String) { cell.setCellValue((String) value); } else if
	 * (value instanceof Double) { cell.setCellValue((Double) value); } else if
	 * (value instanceof Boolean) { cell.setCellValue((Boolean) value); } else if
	 * (value instanceof Integer) { cell.setCellValue((Integer) value); } else if
	 * (value instanceof Long) { cell.setCellValue((Long) value); } else {
	 * cell.setCellValue(value.toString()); } saveAndClose(filePath); }
	 * 
	 * public void saveAndClose(String filePath) { try (FileOutputStream
	 * outputStream = new FileOutputStream(filePath)) {
	 * workbook.write(outputStream); } catch (IOException e) { e.printStackTrace();
	 * } finally { closeWorkbook(); } }
	 * 
	 * public void closeWorkbook() { try { workbook.close(); } catch (IOException e)
	 * { e.printStackTrace(); } }
	 */ private static ExcelOperator excelOperator;

	    private ExcelOperator() {
	        // Private constructor to prevent instantiation
	    }

	    public static ExcelOperator getInstance() {
	        if (excelOperator == null) {
	            synchronized (ExcelOperator.class) {
	                if (excelOperator == null) {
	                    excelOperator = new ExcelOperator();
	                }
	            }
	        }
	        return excelOperator;
	    }

	    private void verifyFilePath(String filePath) {
	        File file = new File(filePath);
	        if (!file.exists()) {
	            throw new IllegalArgumentException("File does not exist: " + filePath);
	        }
	        if (!file.canRead()) {
	            throw new IllegalArgumentException("File cannot be read: " + filePath);
	        }
	        if (!file.canWrite()) {
	            throw new IllegalArgumentException("File cannot be written: " + filePath);
	        }
	    }
	    private boolean isFileLocked(File file) {
	        try (FileInputStream in = new FileInputStream(file)) {
	            return false;
	        } catch (IOException e) {
	            return true;
	        }
	    }
	    public Map<String, Object> getRowData(String filePath, String sheetName, int rowNumber) {
	        verifyFilePath(filePath);
	        try (FileInputStream excelFile = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(excelFile)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the file.");
	            }
	            Map<String, Object> data = new HashMap<>();
	            Row row = sheet.getRow(rowNumber);
	            if (row != null) {
	                for (Cell cell : row) {
	                    data.put((String) getCellValue(sheet.getRow(0).getCell(cell.getColumnIndex())), getCellValue(cell));
	                }
	            }
	            return data;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new HashMap<>();
	        }
	    }

	    private Object getCellValue(Cell cell) {
	        if (cell == null) {
	            return "";
	        }
	        switch (cell.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue();
	            case NUMERIC:
	                if (DateUtil.isCellDateFormatted(cell)) {
	                    return cell.getDateCellValue().toString();
	                } else {
	                    return cell.getNumericCellValue();
	                }
	            case BOOLEAN:
	                return cell.getBooleanCellValue();
	            case FORMULA:
	                return cell.getCellFormula();
	            case BLANK:
	                return "";
	            default:
	                return "";
	        }
	    }

	    public void writeData(String filePath, String sheetName, int rowNumber, String columnName, Object value) {
	        File file = new File(filePath);
	        verifyFilePath(filePath);

	        if (isFileLocked(file)) {
	            System.out.println("The file is currently open in another program and cannot be written to.");
	            return;
	        }

	        try (FileInputStream excelFile = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(excelFile)) {

	            Sheet sheet = workbook.getSheet(sheetName);
	            if (sheet == null) {
	                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the file.");
	            }
	            int columnIndex = -1;
	            Row headerRow = sheet.getRow(0);

	            for (Cell cell : headerRow) {
	                if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
	                    columnIndex = cell.getColumnIndex();
	                    break;
	                }
	            }

	            if (columnIndex == -1) {
	                throw new IllegalArgumentException("Column " + columnName + " does not exist in the sheet.");
	            }

	            Row row = sheet.getRow(rowNumber);
	            if (row == null) {
	                row = sheet.createRow(rowNumber);
	            }

	            Cell cell = row.getCell(columnIndex);
	            if (cell == null) {
	                cell = row.createCell(columnIndex);
	            }

	            if (value instanceof String) {
	                cell.setCellValue((String) value);
	            } else if (value instanceof Double) {
	                cell.setCellValue((Double) value);
	            } else if (value instanceof Boolean) {
	                cell.setCellValue((Boolean) value);
	            } else if (value instanceof Integer) {
	                cell.setCellValue((Integer) value);
	            } else if (value instanceof Long) {
	                cell.setCellValue((Long) value);
	            } else {
	                cell.setCellValue(value.toString());
	            }

	            // Save changes
	            saveWorkbook(workbook, filePath);

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private void saveWorkbook(Workbook workbook, String filePath) {
	        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
	            workbook.write(outputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            closeWorkbook(workbook);
	        }
	    }

	    private void closeWorkbook(Workbook workbook) {
	        try {
	            workbook.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
}
