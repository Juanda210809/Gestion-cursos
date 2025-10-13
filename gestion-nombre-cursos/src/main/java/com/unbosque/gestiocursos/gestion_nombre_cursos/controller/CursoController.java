package com.unbosque.gestiocursos.gestion_nombre_cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;
import com.unbosque.gestiocursos.gestion_nombre_cursos.repository.CursoRepository;

import ch.qos.logback.core.model.processor.PhaseIndicator;

@Controller
public class CursoController {

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	public String home() {
		return "redirect:/cursos";
	}

	@GetMapping("/cursos")
	public String listarCursos(Model model) {
		List<Curso> cursos = cursoRepository.findAll();
		model.addAttribute("cursos", cursos);
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
			model.addAttribute("pageTitle", "Actuaizar curso: "+id);
			model.addAttribute("curso",curso);

			redirectAttributes.addFlashAttribute("message", "Curso actualizado con exito");
			
			return "agregar_curso_form";

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}

		return "redirect:/cursos";
	}
	
	@GetMapping("/cursos/delete/{id}")
	public String eliminarCurso(@PathVariable Integer id,Model model, RedirectAttributes redirectAttributes) {
		
		try {
			cursoRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("message","Curso Eliminado");
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/cursos";
	}

	
	
	
}
