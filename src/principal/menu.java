package principal;

import java.awt.FlowLayout;


import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;



public class menu extends Frame implements WindowListener, ActionListener

{

	private static final long serialVersionUID = 1L;
	
	//Declarar la Barra de Menú
	MenuBar mnbMenu = new MenuBar();
	
	//Declarar las opciones de la Barra de Menú
	Menu mn1 = new Menu("Profesor");
	Menu mn2 = new Menu("Asignatura");
	Menu mn3 = new Menu("Curso");
	Menu mn4 = new Menu("Alumnos");
	Menu mn5 = new Menu("Asign Alumno");
	Menu mn6 = new Menu("Usuario");
	
	//Declarar las opciones
	MenuItem mnl1 = new MenuItem("Alta");
	MenuItem mnl2 = new MenuItem("Baja");
	MenuItem mnl3 = new MenuItem("Consultar");
	MenuItem mnl4 = new MenuItem("Modificar");
	
	MenuItem mnl5 = new MenuItem("Alta");

	
	public menu()
	{
		  //Definimos la Ventana Principal
		   setLayout(new FlowLayout());
		   setTitle("Menu");
		   setResizable(false);
		   //Establecer la Barra de Menú como tal
		   setMenuBar(mnbMenu);
		   setSize(300,250);
		   
		   mn1.add(mnl1);
		   mn1.addSeparator();
		   mn1.add(mnl2);
		   mn1.addSeparator();
		   mn1.add(mnl3);
		   mn1.addSeparator();
		   mn1.add(mnl4);
		   
		   mn2.add(mnl5);
		   
		 //Añadir los submenus a la barra de menú
		   mnbMenu.add(mn1);
		   mnbMenu.add(mn2);

		   
		   mnl1.addActionListener(this);
		   
		   mnl2.addActionListener(this);
		   mnl3.addActionListener(this);
		   mnl4.addActionListener(this);
		   
		   mnl5.addActionListener(this);
		   addWindowListener(this);
		   
		   setVisible(true);
	}
	
	//public static void main(String[] args)
	//{
		// new menu();

	//}
	
	public void actionPerformed(ActionEvent e)
	{
		if(mnl1.equals(e.getSource()))
		{
			 new AltaProfesor();
		}
		else if (mnl2.equals(e.getSource()))
		{
			
		}
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	   {
		if(mnl1.isActive())
		{
			mnl1.setVisible(false);
		}
		
		
			
			 System.exit(0);
		 
		} 
	public void windowDeactivated(WindowEvent we) {} 
	public void windowDeiconified(WindowEvent we) {} 
	public void windowIconified(WindowEvent we) {}
	
	public void windowOpened(WindowEvent we) {}

}
