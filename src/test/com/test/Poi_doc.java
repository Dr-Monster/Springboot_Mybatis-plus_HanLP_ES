package com.test;

import com.entity.KeyWords;
import com.hankcs.hanlp.HanLP;
import com.mapper.KeyWordsMapper;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class Poi_doc {

    @Test
    public void test()  {
        String filePath = "C:\\Users\\Administrator\\Desktop\\故障\\液压设备故障诊断分析.docx";
        String content = readWord(filePath);
    }

    public String readWord(String path) {
        String buffer = "";
        try {
            if (path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(path));
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (path.endsWith("docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            } else {
                System.out.println("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer;
    }



    @Test
    public void test2()  {
        String filePath = "C:\\Users\\Administrator\\Desktop\\故障\\薄板厂2018-2020年实际设备事故明细(最终版樊永安)20210406.xlsx";
        Map<String , String[][]> stringMap = getArrayFromXLSX(filePath);
        for (Map.Entry<String , String[][]> item : stringMap.entrySet()) {
            String[][] content = item.getValue();
            for (String[] row : content) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
            }
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    /**
     * 获取xlsx文件内容某个sheet（从0开始）的内容，以二维数组形式返回
     *
     * @param fileAbsolutePath xlsx文件的绝对路径
     * @return xlsx文件的文本内容
     */
    public static Map<String , String[][]> getArrayFromXLSX(String fileAbsolutePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(fileAbsolutePath));
            XSSFWorkbook book = new XSSFWorkbook(inputStream);


            Map<String , String[][]> stringMap = new HashMap<>(book.getNumberOfSheets() + 1);
            for(int sheetIndex=0;sheetIndex<book.getNumberOfSheets();sheetIndex++){
                XSSFSheet sheet = book.getSheetAt(sheetIndex);
                int rowNum = sheet.getLastRowNum() + 1;
                int coloumNum = sheet.getRow(0).getPhysicalNumberOfCells();
                String[][] contents = new String[rowNum][coloumNum];
                for (int j = 0; j < rowNum; j++) {
                    XSSFRow row = sheet.getRow(j);
                    if (row != null) {
                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            contents[j][k] = getXCellFormatValue(row.getCell(k));
                        }
                    }
                }
                stringMap.put("sheet_" + sheetIndex , contents);
            }
            return stringMap;
        } catch (FileNotFoundException fe) {
        } catch (IOException ie) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    private static String getXCellFormatValue(XSSFCell cell) {
        String cellValue = "";
        if (null != cell) {
            switch (String.valueOf(cell.getCellType().getCode())) {
                case "1":
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                case "0":
                    cellValue = (new Double(cell.getNumericCellValue())).intValue() + "";
                    break;
                default:
                    cellValue = " ";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }
}
