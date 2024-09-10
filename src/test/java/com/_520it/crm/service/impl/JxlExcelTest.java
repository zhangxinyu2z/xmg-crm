package com._520it.crm.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.Test;

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
import jxl.write.WriteException;

/**
 * @author xinyu
 * @date 2021/06/23
 */
public class JxlExcelTest {
	
	@Test
	public void fileImport() throws Exception{
		// 获取读取的文件
		Workbook workbook = Workbook.getWorkbook(new File("test.xls"));
		// 获取工作表
		Sheet sheet = workbook.getSheet(1);
		// 多少行，多少列
		int rows = sheet.getRows();
		int columns = sheet.getColumns();
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				Cell cell = sheet.getCell(j,i);
				System.out.print(cell.getContents() + "\t");
			}
			System.out.println();
		}
		
		workbook.close();
		
	}
	
	@Test
	public void export() throws IOException, WriteException {
		// 1、创建excel工作簿
		WritableWorkbook createWorkbook = Workbook.createWorkbook(new File("test.xls"));
		// 2、创建工作表，插入顺序
		WritableSheet sheet = createWorkbook.createSheet("sheet1", 1);
		WritableSheet sheet2 = createWorkbook.createSheet("sheet2", 0);
		
		// 3、设置第一列列宽
		sheet.setColumnView(0, 30);
		// 设置第一行行高
		sheet.setRowView(0, 500);
		// 合并单元格，只需要知道左上角和右下角的单元格位置即可
		sheet.mergeCells(2, 0, 5, 2);
		
		// 4、创建单元格 合并后的单元格显示的内容为左上角的单元格
		Label label = new Label(2, 0, "第3列第一行");
		Label label2 = new Label(2,1,"第3列第2行");
		sheet.addCell(label);
		sheet.addCell(label2);
		
		// 格式化
		WritableCellFormat format = new WritableCellFormat();
		// 水平居中
		format.setAlignment(Alignment.CENTRE);
		// 垂直居中
		format.setVerticalAlignment(VerticalAlignment.CENTRE);
		Label label3 = new Label(0,1,"第1列第2行",format);
		sheet.addCell(label3);
		
		// 日期
		// 对日期格式化
		DateFormat dateFormat = new DateFormat("yyyy-MM-dd HH:mm:ss");
		WritableCellFormat format2 = new WritableCellFormat(dateFormat);
		DateTime dateTime = new DateTime(0, 4, new Date(),format2);
		sheet.addCell(dateTime);
	
		// 把内容写到excel中
		createWorkbook.write();
		// 关闭资源
		createWorkbook.close();
	}
}
