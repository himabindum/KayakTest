package com.my.test.kayak.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class ReadFromExcel {

    /**
     * initiateExcelConnection function to establish an initial connection with
     * a work sheet
     *
     * @param workSheet         (String)
     * @param doFilePathMapping (boolean)
     * @param workBookName      (String)
     * @return XSSFSheet (Work sheet)
     */
    public XSSFSheet initiateExcelConnection(String workSheet, String workBookName, boolean doFilePathMapping) {

        XSSFSheet sheet = null;

        try {

            String filePath = "";
            if (doFilePathMapping)
                filePath = ".\\src\\main\\resources\\testdata\\" + workBookName;
            else
                filePath = workBookName;

            filePath = filePath.replace("\\", File.separator);
            FileInputStream fis = new FileInputStream(filePath);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            sheet = wb.getSheet(workSheet);

        } catch (RuntimeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }

    /**
     * findRowColumnCount function to establish an initial connection with a
     * work sheet
     *
     * @param sheet          : Sheet Name
     * @param rowColumnCount (Hashtable)
     * @return Hashtable (returns row count and column count)
     */
    public Hashtable<String, Integer> findRowColumnCount(XSSFSheet sheet, Hashtable<String, Integer> rowColumnCount) {

        XSSFRow row = null;
        String temp = null;

        int rows = sheet.getPhysicalNumberOfRows();
        int cols = 0;
        int tmp = 0;
        int counter = 0;

        for (int i = 0; i < 10 || i < rows; i++) {

            row = sheet.getRow(i);

            if (row == null)
                continue;

            temp = convertXSSFCellToString(row.getCell(0));

            if (!temp.equals(""))
                counter++;

            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
            if (tmp > cols)
                cols = tmp;

        }

        rowColumnCount.put("RowCount", counter);
        rowColumnCount.put("ColumnCount", cols);
        return rowColumnCount;

    }

    /**
     * readExcelHeaders function to establish an initial connection with a work
     * sheet
     *
     * @param sheet          : Sheet Name
     * @param excelHeaders   : Excel Headers List
     * @param rowColumnCount : Row and Column Count
     * @return: Hashtable (Having Header column values)
     */
    public Hashtable<String, Integer> readExcelHeaders(XSSFSheet sheet, Hashtable<String, Integer> excelHeaders,
                                                       Hashtable<String, Integer> rowColumnCount) {

        XSSFRow row = null;
        XSSFCell cell = null;

        for (int r = 0; r < rowColumnCount.get("RowCount"); r++) {

            row = sheet.getRow(r);
            if (row == null)
                continue;

            for (int c = 0; c < rowColumnCount.get("ColumnCount"); c++) {

                cell = row.getCell(c);
                if (cell != null)
                    excelHeaders.put(cell.toString(), c);
            }

            break;
        }

        return excelHeaders;
    }

    /**
     * function will convert the XSSFCell type value to its equivalent string
     * value
     *
     * @param cell : XSSFCell value
     * @return String
     */
    public String convertXSSFCellToString(XSSFCell cell) {

        String cellValue = "";

        if (cell != null)
            cellValue = cell.toString().trim();

        return cellValue;

    }

    /**
     * @param sheet
     * @return: it will return all Name is a list
     */
    public ArrayList<String> getHeaders(XSSFSheet sheet) {
        ArrayList<String> listOfHeaderName = new ArrayList<String>();
        int columnCount = 0;

        columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < columnCount; i++) {
            if (!(sheet.getRow(0).getCell(i).getStringCellValue() == null)) {
                listOfHeaderName.add(sheet.getRow(0).getCell(i).getStringCellValue());
            }
        }

        return listOfHeaderName;

    }//getHeaders

    /**
     * @Description:
     * @param: XSSFSheet Obj
     * @param: test case id in String format
     * Return: it will return a list of row No. of all available with same test case id
     */

    public ArrayList<Integer> getRowNumbers(XSSFSheet sheet, String testCaseId) {
        ArrayList<Integer> listOfRowNumber = new ArrayList<Integer>();

        int rowCount = 0;
        rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("Row Count "+rowCount);
        for (int i = 1; i < rowCount; i++) {
            String cValue = sheet.getRow(i).getCell(0).getStringCellValue();
            //System.out.println("Test Case: "+cValue);//for debugging

            if (cValue.trim().equalsIgnoreCase(testCaseId.trim())) {
                listOfRowNumber.add(i);
            }
        }
        return listOfRowNumber;
    }

    public static String getData(XSSFSheet sheet, int row, int column) {

        String dataToBeReturned = "";

        dataToBeReturned = sheet.getRow(row).getCell(column).getStringCellValue();

        return dataToBeReturned;

    }

}

