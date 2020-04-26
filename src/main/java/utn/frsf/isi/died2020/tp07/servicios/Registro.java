package utn.frsf.isi.died2020.tp07.servicios;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

import utn.frsf.isi.died2020.tp07.modelo.Curso;
import utn.frsf.isi.died2020.tp07.modelo.Libro;
import utn.frsf.isi.died2020.tp07.modelo.Material;
import utn.frsf.isi.died2020.tp07.modelo.Usuario;
import utn.frsf.isi.died2020.tp07.modelo.Video;
import utn.frsf.isi.died2020.tpo7.excepciones.AdquisicionException;

public class Registro {
	
	public enum Plan { GRATIS,BASE,PREMIUM};
	private Set<Usuario> usuarios = new LinkedHashSet<Usuario>();
	
	public void registrarGratuito(String nombre) {
		Usuario aux  = new Usuario();
		aux.setCorreoElectronico(nombre);
		
		aux.setPuedeAdquirirCurso(c -> false);
		aux.setPuedeAdquirirLibro(l -> l.librosAdquiridos()<1);
		aux.setPuedeAdquirirVideo(v -> v.minutosAdquiridos()<30);
		
		aux.setCostoCurso((u,c) -> 0.0);
		aux.setCostoLibro((u,c) -> 0.0);
		aux.setCostoVideo((u,c) -> 0.0);
		
		this.usuarios.add(aux);
	}
	
	public void registrarBasicos(String nombre, String tarjeta) {
		Usuario aux  = new Usuario();
		aux.setCorreoElectronico(nombre);
		aux.setTarjetaCredito(tarjeta);
		
		aux.setPuedeAdquirirCurso(c -> true);
		aux.setPuedeAdquirirLibro(l -> true);
		aux.setPuedeAdquirirVideo(v -> true);
		
		aux.setCostoCurso((u,c) -> (u.CursosAdquiridos()<2) ? 0.0 : c.precio());
		aux.setCostoLibro((u,c) -> (u.librosAdquiridos()<3) ? 0.0 : c.getPaginas()/10 *0.25);
		aux.setCostoVideo((u,c) -> (u.minutosAdquiridos()<500) ? 0.0 : c.getDuracion()/10 *0.1);
		
		this.usuarios.add(aux);
	}
	
	public void registrarBasicos(Usuario aux, String tarjeta) {
		aux.setTarjetaCredito(tarjeta);
		
		aux.setPuedeAdquirirCurso(c -> true);
		aux.setPuedeAdquirirLibro(l -> true);
		aux.setPuedeAdquirirVideo(v -> true);
		
		aux.setCostoCurso((u,c) -> (u.CursosAdquiridos()<2) ? 0.0 : c.precio());
		aux.setCostoLibro((u,c) -> (u.librosAdquiridos()<3) ? 0.0 : c.getPaginas()/10 *0.25);
		aux.setCostoVideo((u,c) -> (u.minutosAdquiridos()<500) ? 0.0 : c.getDuracion()/10 *0.1);
	}
	
	public void registrarPremium(String nombre, String tarjeta) {
		Usuario aux  = new Usuario();
		aux.setCorreoElectronico(nombre);
		aux.setTarjetaCredito(tarjeta);
		
		aux.setPuedeAdquirirCurso(c -> true);
		aux.setPuedeAdquirirLibro(l -> true);
		aux.setPuedeAdquirirVideo(v -> true);
		
		aux.setCostoCurso((u,c) -> (u.CursosAdquiridos()<4) ? 0.0 : c.precio()*0.8);
		aux.setCostoLibro((u,c) -> (u.librosAdquiridos()<25) ? 0.0 : c.getPaginas()/50 *0.25);
		aux.setCostoVideo((u,c) -> (u.minutosAdquiridos()<2500) ? 0.0 : c.getDuracion()/60 *0.1);
	}
	
	public void registarPremium(Usuario aux, String tarjeta) {
		aux.setTarjetaCredito(tarjeta);
		
		aux.setPuedeAdquirirCurso(c -> true);
		aux.setPuedeAdquirirLibro(l -> true);
		aux.setPuedeAdquirirVideo(v -> true);
		
		aux.setCostoCurso((u,c) -> (u.CursosAdquiridos()<4) ? 0.0 : c.precio()*0.8);
		aux.setCostoLibro((u,c) -> (u.librosAdquiridos()<25) ? 0.0 : c.getPaginas()/50 *0.25);
		aux.setCostoVideo((u,c) -> (u.minutosAdquiridos()<2500) ? 0.0 : c.getDuracion()/60 *0.1);
	}
	
	
	public void adquirir(String usuario, Material material) {
		Optional<Usuario> user = this.usuarios.stream().filter(u -> u.getCorreoElectronico().equalsIgnoreCase(usuario)).findFirst();
		if(user.isPresent()) {
			try {
				user.get().adquirir(material);
			} catch (AdquisicionException e) {
				System.out.println("No se pudo adquirir, revise los datos. Detalle: "+e.getMessage());
			}
		}	
	}
	
}
