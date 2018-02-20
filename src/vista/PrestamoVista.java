package vista;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;

import modelo.Libro;
import modelo.LibroModelo;
import modelo.Prestamo;
import modelo.PrestamoModelo;
import modelo.Usuario;
import modelo.UsuarioModelo;

public class PrestamoVista {

	static final int TOMAR_PRESTADO = 1;
	static final int ENTREGAR = 2;
	static final int LISTAR = 3;
	static final int SALIR = 0;
	
	public void menuPrestamo(){
		int opcion;
		Scanner scan = new Scanner(System.in);
		do{
			System.out.println(" ## MENU DE PRESTAMOS ##");
			System.out.println(TOMAR_PRESTADO + " tomar prestado un libro");
			System.out.println(ENTREGAR + " entregar un libro");
			System.out.println(LISTAR + " listar los prestamos");
			opcion = Integer.parseInt(scan.nextLine());
			
			switch (opcion){
			case TOMAR_PRESTADO:
				realizarPrestamo(scan);
				break;
			case ENTREGAR:
				terminarPrestamo(scan);
				break;
			case LISTAR:
				PrestamoModelo prestamoModelo = new PrestamoModelo();
				listarPrestamos(prestamoModelo.selectAll());
			default:
				break;
			}
		}while(opcion != SALIR);
	}
	
	private void listarPrestamos(ArrayList<Prestamo> prestamos){
		Iterator<Prestamo> i = prestamos.iterator();
		while(i.hasNext()){
			Prestamo prestamo = i.next();
			listarPrestamo(prestamo);
		}
	}
	
	private void listarPrestamo(Prestamo prestamo){
		System.out.println(prestamo.getLibro().getTitulo()+" : "
				+ prestamo.getLibro().getId()+ " : "
				+ prestamo.getUsuario().getNombre()+" "+prestamo.getUsuario().getApellido()+" : "
				+ prestamo.getUsuario().getId()+" : "
				+ prestamo.getFechaPrestamo()+" - "
				+ prestamo.getFechaLimite());
	}

	private void terminarPrestamo(Scanner scan) {

		//pedir el dni
		System.out.println("Introduce el DNI:");
		String dni = scan.nextLine();
		//conseguir el usuario
		UsuarioModelo usuarioModelo = new UsuarioModelo();
		Usuario usuario = usuarioModelo.selectPorDni(dni);
		//pedir el titulo
		System.out.println("Introduce el titulo del libro:");
		String titulo = scan.nextLine();
		
		//conseguir el libro
		LibroModelo libroModelo = new LibroModelo();
		Libro libro = libroModelo.selectPorTitulo(titulo);
		
		//conseguir el prestamo de la BBDD
		PrestamoModelo prestamoModelo = new PrestamoModelo();
		Prestamo prestamo = prestamoModelo.selectNoEntregado(usuario, libro);
		
		//cambiar el objeto prestamo
		prestamoModelo.update(prestamo);
		
		System.out.println("EL PRESTAMO HA SIDO TERMINADO");
	}

	private void realizarPrestamo(Scanner scan) {
		System.out.println("Introduce el titulo del libro");
		String titulo = scan.nextLine();
		LibroModelo libroModelo = new LibroModelo();
		Libro libro = libroModelo.selectPorTitulo(titulo);
		
		if(libro != null){ //el libro existe
			System.out.println("Introduce el DNI");
			String dni = scan.nextLine();
			UsuarioModelo usuarioModelo = new UsuarioModelo();
			Usuario usuario = usuarioModelo.selectPorDni(dni);
			
			//crear el objeto prestamo
			Prestamo prestamo = new Prestamo();
			prestamo.setLibro(libro);
			prestamo.setUsuario(usuario);
			Date date = new Date();
			prestamo.setFechaPrestamo(date);
			long ltime=date.getTime()+21*24*60*60*1000;
			Date dateEnd=new Date(ltime);
			prestamo.setFechaLimite(dateEnd);
			prestamo.setEntragado(false);
			//crear el objeto modelo prestamo
			PrestamoModelo prestamoModelo = new PrestamoModelo();
						
			//insertar prestamo utilizando modeloPrestamo
			prestamoModelo.insertar(prestamo);
			
			//SimpleDateFormat DateEs = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));

			//Date fechaPrueba = DateEs.
			System.out.println("Prestamo realizado, recuerde devolver el libro antes de:" + dateEnd);
			
		}else{//el libro no existe
			
		}
		
	}
}
