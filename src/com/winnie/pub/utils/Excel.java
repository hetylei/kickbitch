package com.winnie.pub.utils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: chenlei
 * Date: 12-3-18
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
public class Excel {
    /**
     * 获取excel 可用列
     * @param f
     * @return
     */
    public static List<String> getColFromExcel(File f) {

        Workbook workbook;
        try {
            workbook = Workbook.getWorkbook(f);

            if (workbook.getSheets().length > 0)
            {
                Sheet sheet = workbook.getSheet(0);
                int rowNum = sheet.getRows();

                if (rowNum > 0 ) {
                    List<String> r = new ArrayList<String>();
                    for (int i=0;i<sheet.getColumns();i++) {
                        r.add("第" + (i+1) + "列:(" + sheet.getCell(i, 0).getContents() + ")");
                    }
                    workbook.close();
                    return r;
                }
                workbook.close();
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }

        return null;
    }
}
