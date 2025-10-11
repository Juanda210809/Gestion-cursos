package com.unbosque.gestiocursos.gestion_nombre_cursos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unbosque.gestiocursos.gestion_nombre_cursos.entity.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Integer>{

}
