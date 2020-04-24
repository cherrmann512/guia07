package utn.frsf.isi.died2020.tp07.modelo;

import java.util.List;

public class Autor {

	private static Integer _IDS =1 ;
	private Integer id;
	private String nombre;
	private List<Material> publicado;
	private Double ganancias;
	private Double comision;
	
	public Autor() {
		this.id = _IDS++;
		this.ganancias = 0.0;
		this.comision = 0.05;
	}
	
	public Autor(String nombre) {
		this();
		this.nombre = nombre;
	}



	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<Material> getPublicado() {
		return publicado;
	}
	public void setPublicado(List<Material> publicado) {
		this.publicado = publicado;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public Double getGanancias() {
		return ganancias;
	}

	public void pagar(Double p) {
		this.ganancias +=p;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
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
		Autor other = (Autor) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[id=" + id + "; nombre=" + nombre + "]";
	}

	
	
}
