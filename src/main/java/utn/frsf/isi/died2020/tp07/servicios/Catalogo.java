package utn.frsf.isi.died2020.tp07.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import utn.frsf.isi.died2020.tp07.modelo.Autor;
import utn.frsf.isi.died2020.tp07.modelo.Curso;
import utn.frsf.isi.died2020.tp07.modelo.Libro;
import utn.frsf.isi.died2020.tp07.modelo.Material;
import utn.frsf.isi.died2020.tp07.modelo.Tema;
import utn.frsf.isi.died2020.tp07.modelo.Video;

public class Catalogo {
	
	public enum CriterioOrdenamiento { TITULO,FECHA_PUBLICACION,CALIFICACION,COSTO,CALIFICACION_COSTO};
	
	private List<Material> catalogo;
	private Set<Autor> autores;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public Catalogo() {
		this.catalogo = new ArrayList<Material>();
		this.autores = new LinkedHashSet<Autor>();
	}

	public Stream<Material> getCatalogo() {
		return catalogo.stream();
	}
	
	public Stream<Autor> getAutores() {
		return autores.stream();
	}
	
	private Optional<Autor> buscarAutor(Predicate<Autor> p) {
		return this.autores.stream().filter( p).findFirst();
		// si encuentra uno con el predicado V --> retorna un Optional con ese autor
		// si no encuentra retorna un optional vacio
	}
	
	private Optional<Material> buscarMaterial(Predicate<Material> p) {
		return this.catalogo.stream().filter(p).findFirst();
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p) {
		return this.catalogo.stream()
				.filter(p)
				.sorted()
				.collect(Collectors.toList());
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p,Integer n) {
		return this.catalogo
					.stream()
					.filter(p)
					.sorted()
					.limit(n)
					.collect(Collectors.toList());
	}

	private List<Material> buscarListaMaterial(Predicate<Material> p,Comparator<Material> orden) {
		return this.catalogo
					.stream()
					.filter(p)
					.sorted(orden)
					.collect(Collectors.toList());
	}
	
	private List<Material> buscarListaMaterial(Predicate<Material> p,Integer n,Comparator<Material> orden) {
		return this.catalogo
					.stream()
					.filter(p)
					.limit(n)
					.sorted(orden)
					.collect(Collectors.toList());
	}
	

	private Autor obtenerAutor(String nombre) {
		Optional<Autor> autorOpt = this.buscarAutor(a -> a.getNombre().equalsIgnoreCase(nombre));
		Autor autor = null;
		if(autorOpt.isEmpty()) {
			// si no existia lo crea y lo agrega a la lista
			autor = new Autor(nombre);
			this.autores.add(autor);
		} else {
			// si existia lo retorna
			autor = autorOpt.get();
		}
        return autor;
	}
	
	public void agregarLibro(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,String isbn, Integer paginas) {
		List<Tema> temasLibro = Arrays.asList(temas);
        Libro l = new Libro( titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, isbn, paginas);
        this.catalogo.add(l);
	}

	public void agregarVideo(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,Integer duracion) {
		List<Tema> temasLibro = Arrays.asList(temas);
		Video v = new Video( titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, duracion);
        this.catalogo.add(v);
	}

	public void agregarCurso(String titulo, String nombreAutor, Integer calificacion,String fechaPublicacion,Tema[] temas,Double precio,Boolean certificado,Curso.Nivel nivel,Integer clases) {
		List<Tema> temasLibro = Arrays.asList(temas);
		Curso c = new Curso(titulo, LocalDate.parse(fechaPublicacion, formatter).atTime(LocalTime.now()), calificacion, this.obtenerAutor(nombreAutor), temasLibro, precio, certificado, nivel, clases);
        this.catalogo.add(c);
	}
	
	public List<Material> buscarPorTemas(Tema tema) {
		return this.buscarListaMaterial(p-> p.getTemas().indexOf(tema)>=0);
	}
	
	public void imprimirCatalogo() {
		this.catalogo.stream().sorted().forEach(System.out::println);
	}

	public void imprimirAutores() {
		this.autores.stream().forEach(System.out::println);
	}

	
	private Comparator<Material> getCriterio(CriterioOrdenamiento criterio) {
		switch(criterio) {
			case CALIFICACION:
				return (m1,m2) -> m2.getCalificacion().compareTo(m1.getCalificacion());
			case TITULO:
				return (m1,m2) -> m1.getTitulo().compareTo(m2.getTitulo());
			case FECHA_PUBLICACION:
				return (m1,m2) -> m2.getFechaPublicacion().compareTo(m1.getFechaPublicacion());
//			case COSTO:
//				return (m1,m2) -> ((int)m1.costo(user)).compareTo((int)m2.costo(user));

		}
		return null;
	}
	
	public List<Material> busquedaRangoCalificacionOrdCalif(Integer minimo,Integer max){
		return this.buscarListaMaterial( m -> m.getCalificacion()>= minimo && m.getCalificacion()<=max, this.getCriterio(CriterioOrdenamiento.CALIFICACION));
	}
	
	public List<Material> busquedaRangoCalificacionOrdCalif(Integer minimo,Integer max, Integer n){
		return this.buscarListaMaterial( m -> m.getCalificacion()>= minimo && m.getCalificacion()<=max, n, this.getCriterio(CriterioOrdenamiento.CALIFICACION));
	}
	
	public List<Material> busquedaTitulo(String titulo){
		return this.buscarListaMaterial(m -> m.getTitulo().equalsIgnoreCase(titulo), this.getCriterio(CriterioOrdenamiento.TITULO));
	}
	public List<Material> busquedaTitulo(String titulo, Integer n){
		return this.buscarListaMaterial(m -> m.getTitulo().equalsIgnoreCase(titulo), n, this.getCriterio(CriterioOrdenamiento.TITULO));
	}
	
	public List<Material> busquedaRangoFechaOrdFecha(String primero, String ultimo){
		LocalDateTime fechaDesde = LocalDate.parse(primero, formatter).atStartOfDay();
		LocalDateTime fechaHasta = LocalDate.parse(ultimo, formatter).atTime(LocalTime.now());
		return this.buscarListaMaterial(m -> m.getFechaPublicacion().isAfter(fechaDesde) && m.getFechaPublicacion().isBefore(fechaHasta), this.getCriterio(CriterioOrdenamiento.FECHA_PUBLICACION));
	}
	
	public List<Material> busquedaRangoFechaOrdFecha(String primero, String ultimo, Integer n){
		LocalDateTime fechaDesde = LocalDate.parse(primero, formatter).atStartOfDay();
		LocalDateTime fechaHasta = LocalDate.parse(ultimo, formatter).atTime(LocalTime.now());
		return this.buscarListaMaterial(m -> m.getFechaPublicacion().isAfter(fechaDesde) && m.getFechaPublicacion().isBefore(fechaHasta), n, this.getCriterio(CriterioOrdenamiento.FECHA_PUBLICACION));
	}
	
	public List<Material> busquedaTipoYAutorOrdAutor(String autor, String tipo){
		return this.buscarListaMaterial(m -> m.getAutor().getNombre().equalsIgnoreCase(autor) && m.getClass().getSimpleName().equalsIgnoreCase(tipo));
	}
	
	
//	public static void main(String[] args) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		System.out.println(LocalDate.parse("20-02-2010", formatter).atTime(LocalTime.now()));
//		System.out.println(LocalDate.parse("20-02-2010", formatter).atStartOfDay());
//		
//	}

}
