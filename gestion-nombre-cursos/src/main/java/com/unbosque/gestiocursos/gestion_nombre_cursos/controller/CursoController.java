package com.unbosque.gestiocursos.gestion_nombre_cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;
import com.unbosque.gestiocursos.gestion_nombre_cursos.repository.CursoRepository;

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
		cursos = cursoRepository.findAll();
		model.addAttribute("cursos", cursos);
		return "cursos";
	}
}
