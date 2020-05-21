package principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class ficheroLog {

	public static void metodo(String usuario, String mensaje)
	{
		Calendar hoy = Calendar.getInstance();
		try {
			// Destino de los datos 
			FileWriter fw = new FileWriter("movimientos.log");
			// Buffer de escritura 
			BufferedWriter bw = new BufferedWriter(fw);
			// Objeto para la escritura 
			PrintWriter salida = new PrintWriter(bw); 
			//Guardamos la primera línea 
			salida.println("["+ hoy.get(Calendar.DATE)+"]["+ hoy.get(Calendar.MONTH)+"]["+ hoy.get(Calendar.YEAR)+"]["+ hoy.get(Calendar.HOUR_OF_DAY)+"]["+ hoy.get(Calendar.MINUTE)+"]["+ hoy.get(Calendar.SECOND)+"]["+usuario+"]["+mensaje+"]");
			//Cerrar objetos
			salida.close(); 
			bw.close(); 
			fw.close();
		} 
		catch(IOException i) {

			System.out.println("Se produjo un error de Archivo");
		}
	}

	public static void main(String[] args)
	{
		 metodo("usuario1", "mensaje");

	}
}


