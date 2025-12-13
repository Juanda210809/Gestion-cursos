package com.unbosque.gestiocursos.gestion_nombre_cursos.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lowagie.text.DocumentException;
import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;
import com.unbosque.gestiocursos.gestion_nombre_cursos.reports.CursoExporterExcel;
import com.unbosque.gestiocursos.gestion_nombre_cursos.reports.CursoExporterPDF;
import com.unbosque.gestiocursos.gestion_nombre_cursos.repository.CursoRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public String home() {
		return "redirect:/cursos";
	}

	@GetMapping("/cursos")
	public String listarCursos(Model model, @Param("keyword") String keyword,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size) {
		
		/*
		 * Son Parametros para que la busqueda de cursos esten paginadas en un tamaño de  por pagina 
		 *  @Param("keyword") String keyword,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "4") int size
		 */
		
		try {
			List<Curso> cursos = new ArrayList<Curso>();
			Pageable paging = PageRequest.of(page - 1, size);

			Page<Curso> pageCursos = null;

			
			/*
			 *Si en la busqueda no se escribe nada se paginaran todos los cursos que encuentre
			 *En caso de que no sea asi hara la busqueda con la paginacion pero con la palabra clave (keyword) 
			 */
			if (keyword == null) {
				pageCursos = cursoRepository.findAll(paging);
			} else {

				pageCursos = cursoRepository.findByTituloContainingIgnoreCase(keyword, paging);
				model.addAttribute("keyword", keyword);
			}
			cursos = pageCursos.getContent();
			model.addAttribute("cursos", cursos);
			model.addAttribute("currentPage", pageCursos.getNumber()+1);

			model.addAttribute("totalItems", pageCursos.getTotalElements());
			model.addAttribute("totalPages", pageCursos.getTotalPages());
			model.addAttribute("pageSize", size);

		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
		}

		return "cursos";
	}

	@GetMapping("/cursos/nuevo")
	public String agregarCurso(Model model) {
		Curso curso = new Curso();
		curso.setPublicado(true);
		model.addAttribute("curso", curso);
		model.addAttribute("pageTitle", "Nuevo curso");
		return "agregar_curso_form";
	}

	@PostMapping("/cursos/save")
	public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes) {

		try {
			cursoRepository.save(curso);
			redirectAttributes.addFlashAttribute("message", "Curso guardado con exito");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/cursos";
	}

	@GetMapping("/cursos/{id}")
	public String editarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {

		try {

			Curso curso = cursoRepository.findById(id).get();
			model.addAttribute("pageTitle", "Actuaizar curso: " + id);
			model.addAttribute("curso", curso);

			redirectAttributes.addFlashAttribute("message", "Curso actualizado con exito");

			return "agregar_curso_form";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/cursos";
	}

	@GetMapping("/cursos/delete/{id}")
	public String eliminarCurso(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {

		try {
			cursoRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("message", "Curso Eliminado");

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/cursos";
	}

	@GetMapping("/export/pdf")
	public void generarReportePDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=cursos_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);

		List<Curso> cursos = cursoRepository.findAll();

		CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);
		exporterPDF.export(response);
	}

	@GetMapping("/export/excel")
	public void generarReporteExcel(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=cursos_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, headerValue);

		List<Curso> cursos = cursoRepository.findAll();

		CursoExporterExcel exporterExcel = new CursoExporterExcel(cursos);
		exporterExcel.export(response);
	}

}
