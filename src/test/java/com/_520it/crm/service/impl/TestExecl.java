package com._520it.crm.service.impl;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.junit.Test;

import java.io.File;
import java.util.Date;

public class TestExecl {

    @Test
    public void testOut() throws Exception {
        // 1创建execl工作的对象
        WritableWorkbook wb = Workbook.createWorkbook(new File("out.xls"));
        // 2:创建sheet页
        WritableSheet sheet = wb.createSheet("第一个sheet页", 0);
        // 定义宽和高
        sheet.setColumnView(7, 50); // 定义列宽，表示第8列单元格宽度
        sheet.setRowView(6, 500); // 定义行高，索引从0开始，表示第7行单元格

        // 合并单元格
        sheet.mergeCells(0, 0, 4, 4); // 表示对角线单元格

        // 单元格的格式
        WritableCellFormat st = new WritableCellFormat();
        // 设置水平居中
        st.setAlignment(Alignment.CENTRE);
        // 设置垂直居中
        st.setVerticalAlignment(VerticalAlignment.CENTRE);


        // 3:创建单元格,label(列号,行号,内容)
        Label cell = new Label(0, 0, "第一个单元格", st);
        // 4:往sheet页中添加单元格信息
        sheet.addCell(cell);
        // 5:将内容写出去
        wb.write();
        // 6:关闭资源
        wb.close()
        ;
    }

    @Test
    public void testDate() throws Exception {
        // 1:建立execl工作对象
        WritableWorkbook wb = Workbook.createWorkbook(new File("dateOut.xls"));
        // 2:创建sheet页
        WritableSheet sheet = wb.createSheet("第一个sheet页", 0);
        // 3:创建时间格式的单元格
        DateFormat df = new DateFormat("yyyy-MM-dd hh:mm:ss");
        WritableCellFormat st = new WritableCellFormat(df);
        DateTime cell = new DateTime(0, 0, new Date(), st);
        // 4:往sheet页中添加单元格信息
        sheet.addCell(cell);
        // 5:将内容写出去
        wb.write();
        // 6:关闭资源
        wb.close();
    }

    @Test
    public void testRead() throws Exception {
        // 1:获取工作本的对象
        Workbook wb = Workbook.getWorkbook(new File("C:/Users/Administrator/Desktop/test.xls"));
        // 2:获取sheet页的信息
        Sheet sheet = wb.getSheet(0);
        // 3:获取行列信息
        int rows = sheet.getRows();
        int columns = sheet.getColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = sheet.getCell(j, i);
                System.out.print(cell.getContents() + "\t");
            }
            System.out.println();
        }
        // 4:关闭资源
        wb.close();

    }

}
