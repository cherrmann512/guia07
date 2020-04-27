package utn.frsf.isi.died2020.tp07.modelo;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Curso extends Material{
	
	public enum Nivel {BASE,AVANZADO,EXPERTO};
	
	private Double precioBase;
	private Boolean certificado;
	private Nivel nivel;
	private Integer clases;
	
	public Curso() {
		super();
	}

	public Curso(String titulo, LocalDateTime fechaPublicacion, Integer calificacion, Autor autor,
			List<Tema> temas,Double precioBase, Boolean certificado, Nivel nivel, Integer clases) {
		super(titulo, fechaPublicacion, calificacion, autor, temas);
		this.precioBase = precioBase;
		this.certificado = certificado;
		this.nivel = nivel;
		this.clases = clases;
	}

	public Double precio() {
		double precio = this.precioBase;
		if(this.clases>10) 
			precio = precio*1.1;
		if(this.certificado) 
			precio = precio*1.1;
		if(this.nivel == Nivel.AVANZADO)
			precio = precio*1.1;
		if(this.nivel == Nivel.EXPERTO)
			precio = precio*1.2;
		return precio;
	}

	public Double getPrecioBase() {
		return precioBase;
	}

	public void setPrecioBase(Double precioBase) {
		this.precioBase = precioBase;
	}

	public Boolean getCertificado() {
		return certificado;
	}

	public void setCertificado(Boolean certificado) {
		this.certificado = certificado;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	public Integer getClases() {
		return clases;
	}

	public void setClases(Integer clases) {
		this.clases = clases;
	}

	@Override
	public Double rating() {
		return 0.0;
	}
	
	@Override
	public Double costo(Usuario usuario) {
		return usuario.getCostoCurso().apply(usuario, this);
	}

	@Override
	public Boolean puedeAdquirir(Usuario usuario) {
		return usuario.getPuedeAdquirirCurso().test(usuario);
	}
}
