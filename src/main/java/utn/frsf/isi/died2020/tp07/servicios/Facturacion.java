package utn.frsf.isi.died2020.tp07.servicios;

import java.util.List;
import java.util.Optional;

import utn.frsf.isi.died2020.tp07.modelo.Adquisicion;
import utn.frsf.isi.died2020.tp07.modelo.Autor;
import utn.frsf.isi.died2020.tp07.modelo.Material;
import utn.frsf.isi.died2020.tp07.modelo.Usuario;

public class Facturacion {
	
	Catalogo catalogo;

	public void pagar(Autor autor) {
		Optional<Autor> autorEncontrado = this.catalogo.getAutores()
				.filter(a -> a.getNombre().equalsIgnoreCase(autor.getNombre()))
				.findAny();
		if(autorEncontrado.isPresent()) {
			List<Material> lista =autorEncontrado.get().getPublicado();
			for (Material m : lista) {
				for (Adquisicion a : m.getAdquisiciones()) {
					if(!a.getPagado()) {
						autorEncontrado.get().pagar(a.getPrecio()*autor.getComision());
						a.setPagado(true);
					}
				}
			}
		}
	}
	
	public void cobrar(Usuario usuario) {
		
	}
	
}
