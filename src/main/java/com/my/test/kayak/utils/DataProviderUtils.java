package com.my.test.kayak.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * All the test data will be kept in excel sheet (testdata.xlsx). 
 * By using ‘testdata.xlsx’, we pass test data and handle data driven testing. 
 * We use Apache POI to handle excel sheets.
 * 
 */

public class DataProviderUtils {

    private static boolean doFilePathMapping=true;

    @DataProvider
    public  Object[][] multipleExecutionData(Method method,ITestContext context) {
        String sWorkBooName=context.getCurrentXmlTest().getParameter("workBookName");
        String sheetNameP=context.getCurrentXmlTest().getParameter("sheetName");
        return multipleExecutionDataWithFixedSheet(method,sWorkBooName,sheetNameP,null);
    }


    public  Object[][] multipleExecutionDataWithFixedSheet(Method method,String sWorkBooName, String sheetNameP, String testCase){
        String testCaseId;
        if(testCase==null){ testCaseId = method.getName();}
        else{
            testCaseId = testCase;
        }
        //System.out.println("test case Name is :"+testCaseId);for debugging
        ReadFromExcel readTestData=new ReadFromExcel();
        // initializing excel connection
        XSSFSheet sheet=readTestData.initiateExcelConnection(sheetNameP, sWorkBooName, doFilePathMapping);

        ArrayList<Integer> ObjArrayOfTestCaseRow=readTestData.getRowNumbers(sheet,testCaseId);
        ArrayList<String> ObjArrayOf_Headers=readTestData.getHeaders(sheet);
        int executionCount=ObjArrayOfTestCaseRow.size();
        int totalData=ObjArrayOf_Headers.size();

        Object[][] obj=new Object[executionCount][1];

        for(int row=0;row<executionCount;row++)

        {
            HashMap<String, String> ObjHmap=new HashMap<String, String>();
            ObjHmap.put("testCaseId", testCaseId.trim());

            for(int icell=0;icell<totalData;icell++)
            {
                DataFormatter dataFormatter=new DataFormatter();

                Cell cell=sheet.getRow(ObjArrayOfTestCaseRow.get(row)).getCell(icell);
                String tempValue=dataFormatter.formatCellValue(cell);

                ObjHmap.put(ObjArrayOf_Headers.get(icell), tempValue);
            }

            obj[row][0]=ObjHmap;
        }
        return obj;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface DataPass
    {
        String workbookname();
        String sheetName();
    }

}



