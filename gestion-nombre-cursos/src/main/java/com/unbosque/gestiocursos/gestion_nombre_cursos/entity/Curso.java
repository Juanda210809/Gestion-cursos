package com.unbosque.gestiocursos.gestion_nombre_cursos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cursos")
@Data
@AllArgsConstructor
@ToString 
@NoArgsConstructor
public class Curso {
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 128, nullable= false)
	private String Titulo;
	
	@Column(length = 256)
	private String descripcion;
	
	@Column(nullable = false)
	private int nivel;
	
	@Column(name = "estado_publicacion")
	private boolean isPublicado;



	
	
}
