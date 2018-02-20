package vista;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import modelo.Usuario;
import modelo.UsuarioModelo;

public class UsuarioVista {

	private static final int LISTAR = 1;
	private static final int INSERTAR = 2;
	private static final int SALIR = 0;

	public void menuDeUsuario(){
	
		Scanner scan = new Scanner(System.in);
		int opcion;
		
		do{
			System.out.println("## MENU ##");
			System.out.println(LISTAR +" - Para listar los usuarios");
			System.out.println(INSERTAR + " - Para insertar un nuevo usuario");
			System.out.println(SALIR + " - Para salir");
			opcion = Integer.parseInt(scan.nextLine());
		
			switch(opcion){
		
				case LISTAR:
					UsuarioModelo usuarioModelo = new UsuarioModelo();
					ArrayList<Usuario> usuarios = usuarioModelo.selectAll();
					mostrarUsuarios(usuarios);
					break;
				case INSERTAR:
					UsuarioModelo usuarioModelo2 = new UsuarioModelo();
					Usuario usuario = insertarUsuario();
					usuarioModelo2.insert(usuario);
					break;
				default:
					break;
			
			}
		
		}while (opcion !=SALIR);
	
	
	
	}

	private Usuario insertarUsuario() {

		Usuario usuario = new Usuario();
		int dia;
		int mes;
		int ano;
		Scanner insertador = new Scanner(System.in);
		System.out.println("Escribe un nombre");
		usuario.setNombre(insertador.nextLine());
		System.out.println("Escribe un apellido");
		usuario.setApellido(insertador.nextLine());
		System.out.println("Cual es la edad?");
		usuario.setEdad(insertador.nextInt());
		System.out.println("Cual es tu dni?");
		usuario.setDni(insertador.nextLine());
		System.out.println("Cual es tu fecha de nacimiento?");
		System.out.println("Insertar el año:");
		ano = insertador.nextInt();
		System.out.println("Insertar el mes:");
		mes = insertador.nextInt();
		System.out.println("Insertar el dia:");
		dia = insertador.nextInt();
		Date fechaNac = new Date(ano,mes,dia);
		usuario.setFechaNacimiento(fechaNac);
		return usuario;
	}

	private void mostrarUsuarios(ArrayList<Usuario> usuarios) {

		Iterator<Usuario> i = usuarios.iterator();
		while(i.hasNext()){
			Usuario usuario = i.next();
			this.mostrarUsuario(usuario);
		}
	}

	private void mostrarUsuario(Usuario usuario) {

		System.out.println(usuario.getId()+ " # " + usuario.getNombre() + " " + usuario.getApellido() + " " + usuario.getEdad());
	}
}