package com.unbosque.gestiocursos.gestion_nombre_cursos.reports;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.store.Cur;

import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;

public class CursoExporterExcel {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<Curso>cursos;
	
	public CursoExporterExcel(List<Curso> cursos) {
		super();
		this.cursos = cursos;
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Cursos");
		
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		
		createCell(row, 0, "ID", style);
		createCell(row, 1, "Titulo", style);
		createCell(row, 2, "Descripcion", style);
		createCell(row, 3, "Nivel", style);
		createCell(row, 4, "Estado de publicacion", style);
		
	}
	
	private void createCell(Row row, int columnCount, Object value,CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer)value);
		}else if (value instanceof Boolean) {
			cell.setCellValue((Integer)value);
		}else {
			cell.setCellValue((String)value);
		}
		
		cell.setCellStyle(style);
	}
	
	
	private void writeDataLines() {
		int rowCount =1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontHeight(14);
		style.setFont(font);
		
		for (Curso curso : cursos) {
			Row row = sheet.createRow(rowCount++);
		}
		
	}
}
