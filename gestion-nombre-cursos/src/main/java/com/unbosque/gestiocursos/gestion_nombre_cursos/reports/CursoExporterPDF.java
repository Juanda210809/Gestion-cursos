package com.unbosque.gestiocursos.gestion_nombre_cursos.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;

import jakarta.servlet.http.HttpServletResponse;

public class CursoExporterPDF {

	private List<Curso> listaCursos;

	public CursoExporterPDF(List<Curso> listaCursos) {
		super();
		this.listaCursos = listaCursos;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Titulo", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Descripcion", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Nivel", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Publicado", font));
		table.addCell(cell);

	}

	private void writeTableData(PdfPTable table) {
		for (Curso curso : listaCursos) {
			table.addCell(String.valueOf(curso.getId()));
			table.addCell(String.valueOf(curso.getTitulo()));
			table.addCell(String.valueOf(curso.getDescripcion()));
			table.addCell(String.valueOf(curso.getNivel()));
			table.addCell(String.valueOf(curso.isPublicado()));
		}

	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);

		Paragraph p = new Paragraph("Lista de cursos", font);
		p.setAlignment(Paragraph.ALIGN_CENTER);

		document.add(p);
		
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 0.8f, 2.5f, 4f, 1f, 1.2f });
		table.setSpacingBefore(10);


		writeTableHeader(table);
		writeTableData(table);

		document.add(table);
		document.close();
	}

}
