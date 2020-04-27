package utn.frsf.isi.died2020.tp07.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import utn.frsf.isi.died2020.tpo7.excepciones.AdquisicionException;
import utn.frsf.isi.died2020.tpo7.excepciones.LimiteMaximoGastoException;

public class Usuario {
	
	private Integer id;
	private String correoElectronico;
	private String tarjetaCredito;
	private Double gastos;
	private Double limite;
	
	private List<Adquisicion> adquisiciones = new ArrayList<Adquisicion>();
	
	private Predicate<Usuario> puedeAdquirirLibro;
	private Predicate<Usuario> puedeAdquirirVideo;
	private Predicate<Usuario> puedeAdquirirCurso;
	
	private BiFunction<Usuario, Libro, Double> costoLibro;
	private BiFunction<Usuario, Video, Double> costoVideo;
	private BiFunction<Usuario, Curso, Double> costoCurso;
	
	public void adquirir(Material material) throws AdquisicionException, LimiteMaximoGastoException {
		if(material.costo(this)>this.limite) {
			throw new LimiteMaximoGastoException("no puede adquirir el material "+material.getTitulo()+ " porque excede su limite de gasto sin facturarlo");
		}
		if(material.puedeAdquirir(this)) {
			this.adquisiciones.add(new Adquisicion(material, LocalDateTime.now(), material.costo(this)));
		}else throw new AdquisicionException("no se pudo adquirir el material" + material.getTitulo());

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		return true;
	}
	
	public long librosAdquiridos() {
		return this.adquisiciones.stream().filter(a -> a.getMaterial() instanceof Libro).count();
	}
	
	public long CursosAdquiridos() {
		return this.adquisiciones.stream().filter(a -> a.getMaterial() instanceof Curso).count();
	}
	
	public int minutosAdquiridos() {
		return this.adquisiciones.stream()
				.filter(a -> a.getMaterial() instanceof Video)
				.map( v -> (Video) v.getMaterial())
				.mapToInt(v -> v.getDuracion())
				.sum();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getTarjetaCredito() {
		return tarjetaCredito;
	}

	public void setTarjetaCredito(String tarjetaCredito) {
		this.tarjetaCredito = tarjetaCredito;
	}

	public Double getGastos() {
		return gastos;
	}

	public void setGastos(Double gastos) {
		this.gastos = gastos;
	}

	public List<Adquisicion> getAdquisiciones() {
		return adquisiciones;
	}

	public void setAdquisiciones(List<Adquisicion> adquisiciones) {
		this.adquisiciones = adquisiciones;
	}

	public Predicate<Usuario> getPuedeAdquirirLibro() {
		return puedeAdquirirLibro;
	}

	public void setPuedeAdquirirLibro(Predicate<Usuario> puedeAdquirirLibro) {
		this.puedeAdquirirLibro = puedeAdquirirLibro;
	}

	public Predicate<Usuario> getPuedeAdquirirVideo() {
		return puedeAdquirirVideo;
	}

	public void setPuedeAdquirirVideo(Predicate<Usuario> puedeAdquirirVideo) {
		this.puedeAdquirirVideo = puedeAdquirirVideo;
	}

	public Predicate<Usuario> getPuedeAdquirirCurso() {
		return puedeAdquirirCurso;
	}

	public void setPuedeAdquirirCurso(Predicate<Usuario> puedeAdquirirCurso) {
		this.puedeAdquirirCurso = puedeAdquirirCurso;
	}

	public BiFunction<Usuario, Libro, Double> getCostoLibro() {
		return costoLibro;
	}

	public void setCostoLibro(BiFunction<Usuario, Libro, Double> costoLibro) {
		this.costoLibro = costoLibro;
	}

	public BiFunction<Usuario, Video, Double> getCostoVideo() {
		return costoVideo;
	}

	public void setCostoVideo(BiFunction<Usuario, Video, Double> costoVideo) {
		this.costoVideo = costoVideo;
	}

	public BiFunction<Usuario, Curso, Double> getCostoCurso() {
		return costoCurso;
	}

	public void setCostoCurso(BiFunction<Usuario, Curso, Double> costoCurso) {
		this.costoCurso = costoCurso;
	}
	
	
	
}
