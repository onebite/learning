package com.practice.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lxl
 * @Date 2018/6/19 15:54
 */
public class ExcelUtils {



    /**
     * 判断单元格在不在合并单元格范围内，如果是，获取其合并的行数
     * @param sheet
     * @param cellRow
     * @param cellCol
     * @return
     * @throws IOException
     */
    public static int getMergeCellRegionRow(HSSFSheet sheet,int cellRow,int cellCol) throws IOException{
        int retVal = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i=0;i < sheetMergeCount;i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            boolean inRegion = cellRow >= ca.getFirstRow()
                    && cellRow <= ca.getLastRow()
                    && cellCol >= ca.getFirstColumn()
                    && cellCol <= ca.getLastColumn();
            if(inRegion){
                return ca.getLastRow() - ca.getFirstRow() + 1;
            }
        }

        return retVal;
    }

    /**
     * 判断单元格在不在合并单元格范围内，如果是，获取其合并的列数
     * @param sheet
     * @param cellRow
     * @param cellCol
     * @return
     * @throws IOException
     */
    public static int getMergeCellRegionCol(HSSFSheet sheet,int cellRow,int cellCol) throws IOException{
        int retVal = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i=0;i < sheetMergeCount;i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            boolean inRegion = cellRow >= ca.getFirstRow()
                    && cellRow <= ca.getLastRow()
                    && cellCol >= ca.getFirstColumn()
                    && cellCol <= ca.getLastColumn();
            if(inRegion){
                return ca.getLastColumn() - ca.getFirstColumn() + 1;
            }
        }

        return retVal;
    }

    /**
     * 获取表格值
     * @param cell
     * @return
     * @throws IOException
     */
    public static Object getCellValue(HSSFCell cell) throws IOException {
        Object value = "";
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            value = cell.getRichStringCellValue().toString();
        } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {
                Date date = cell.getDateCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                value = sdf.format(date);
            } else {
                double value_temp = (double) cell.getNumericCellValue();
                BigDecimal bd = new BigDecimal(value_temp);
                BigDecimal bd1 = bd.setScale(3, 4);
                value = bd1.doubleValue();
            }
        }

        return value;
    }


    public static String getCellStrValue(HSSFCell cell) throws IOException {

        return String.valueOf(getCellValue(cell));
    }
}
