package modelo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import modelo.usuario.ModUsuario;

public class Log
{
	private static Log logUnico = new Log();
	
	public static final String ARCHIVO = "movimientos.log";
	private ModUsuario usuario;
	
	private Log() {}
	
	public static Log getInstance()
	{
		return logUnico;
	}
	
	public synchronized void escribir(String mensaje)
	{
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	    Date date = new Date();  
		String linea = String.format(
				"[%s][%s][%s]", 
				formatter.format(date), usuario.getUsuario(), mensaje
				);
		
		try(FileWriter fw = new FileWriter(ARCHIVO, true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);)
		{
			salida.println(linea);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void log(String mensaje)
	{
		Log log = Log.getInstance();
		log.escribir(mensaje);
	}

	public ModUsuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(ModUsuario usuario)
	{
		this.usuario = usuario;
	}
}