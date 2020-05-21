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



public class Usuarios extends Frame implements WindowListener, ActionListener

{

	private static final long serialVersionUID = 1L;
	
	//Declarar la Barra de Men�
	MenuBar mnbMenu = new MenuBar();
	
	//Declarar las opciones de la Barra de Men�
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
	
/////////////////////////////////Asignatura///////////////////////////////////
MenuItem mnl5 = new MenuItem("Alta");
MenuItem mnl6 = new MenuItem("Baja");
MenuItem mnl7 = new MenuItem("Consultar");
MenuItem mnl8 = new MenuItem("Modificar");

////////////////////////////////////Alumnos////////////////////////////////////////////////////////
MenuItem mnl9 = new MenuItem("Alta");
MenuItem mnl10 = new MenuItem("Baja");
MenuItem mnl11= new MenuItem("Consultar");
MenuItem mnl12= new MenuItem("Modificar");

///////////////////////////////////////////Curso///////////////////////////////////////////////////
MenuItem mnl13 = new MenuItem("Alta");
MenuItem mnl14= new MenuItem("Baja");
MenuItem mnl15= new MenuItem("Consultar");
MenuItem mnl16= new MenuItem("Modificar");

//////////////////////////////////////////AsignaturaAlumnos//////////////////////////////////////////////
MenuItem mnl17 = new MenuItem("Alta");
MenuItem mnl18= new MenuItem("Baja");
MenuItem mnl19= new MenuItem("Consultar");
MenuItem mnl20= new MenuItem("Modificar");

/////////////////////////////////////////////////Usuarios/////////////////////////////////////
MenuItem mnl21 = new MenuItem("Alta");
MenuItem mnl22 = new MenuItem("Baja");
MenuItem mnl23= new MenuItem("Consultar");
MenuItem mnl24= new MenuItem("Modificar");

	
	public Usuarios()
	{
		  //Definimos la Ventana Principal
		   setLayout(new FlowLayout());
		   setTitle("Menu Usuario");
		   setResizable(false);
		   //Establecer la Barra de Men� como tal
		   setMenuBar(mnbMenu);
		   setSize(450,300);
		   
		   mn1.add(mnl1);
		   mn1.addSeparator();
		   mn1.add(mnl2);
		   mn1.addSeparator();
		   mn1.add(mnl3);
		   mn1.addSeparator();
		   mn1.add(mnl4);
		   
		   mn2.add(mnl5);
		   mn2.addSeparator();
		   mn2.add(mnl6);
		   mn2.addSeparator();
		   mn2.add(mnl7);
		   mn2.addSeparator();
		   mn2.add(mnl8);
		   
		   mn3.add(mnl9);
		   mn3.addSeparator();
		   mn3.add(mnl10);
		   mn3.addSeparator();
		   mn3.add(mnl11);
		   mn3.addSeparator();
		   mn3.add(mnl12);
		   
		   mn4.add(mnl13);
		   mn4.addSeparator();
		   mn4.add(mnl14);
		   mn4.addSeparator();
		   mn4.add(mnl15);
		   mn4.addSeparator();
		   mn4.add(mnl16);
		   
		   mn5.add(mnl17);
		   mn5.addSeparator();
		   mn5.add(mnl18);
		   mn5.addSeparator();
		   mn5.add(mnl19);
		   mn5.addSeparator();
		   mn5.add(mnl20);
		   
		   mn6.add(mnl21);
		   mn6.addSeparator();
		   mn6.add(mnl22);
		   mn6.addSeparator();
		   mn6.add(mnl23);
		   mn6.addSeparator();
		   mn6.add(mnl24);
		   
		 //A�adir los submenus a la barra de men�
		   mnbMenu.add(mn1);
		   mnbMenu.add(mn2);
		   mnbMenu.add(mn3);
		   mnbMenu.add(mn4);
		   mnbMenu.add(mn5);
		   mnbMenu.add(mn6);

		   
		   mnl1.addActionListener(this);
		   mnl2.addActionListener(this);
		   mnl3.addActionListener(this);
		   mnl4.addActionListener(this);
		   
		   mnl5.addActionListener(this);
		   mnl6.addActionListener(this);
		   mnl7.addActionListener(this);
		   mnl8.addActionListener(this);
		   
		   mnl9.addActionListener(this);
		   mnl10.addActionListener(this);
		   mnl11.addActionListener(this);
		   mnl12.addActionListener(this);
		   
		   mnl13.addActionListener(this);
		   mnl14.addActionListener(this);
		   mnl15.addActionListener(this);
		   mnl16.addActionListener(this);
		   
		   mnl17.addActionListener(this);
		   mnl18.addActionListener(this);
		   mnl19.addActionListener(this);
		   mnl20.addActionListener(this);
		   
		   mnl21.addActionListener(this);
		   mnl22.addActionListener(this);
		   mnl23.addActionListener(this);
		   mnl24.addActionListener(this);
		   
		   addWindowListener(this);
		   
		   setVisible(true);
	}
	
	
	public static void main(String[] args)
	{
		 new Usuarios();

	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(mnl1.equals(e.getSource()))
		{
			 new AltaProfesor();
		}
		
		else if (mnl3.equals(e.getSource()))
		{
			new ConsultarProfesor();
		}
		
		else if (mnl5.equals(e.getSource()))
		{
			new AltaAsignatura();
		}
		
		else if (mnl7.equals(e.getSource()))
		{
			new ConsultarAsignatura();
		}
		
		else if (mnl9.equals(e.getSource()))
		{
			new AltaCursos();
		}
		
		else if (mnl11.equals(e.getSource()))
		{
			new ConsultarCurso();
		}
		
		else if (mnl13.equals(e.getSource()))
		{
			new AltaAsignatura();
		}
		
		else if (mnl15.equals(e.getSource()))
		{
			new ConsultarAsignatura();
		}
		
		else if (mnl17.equals(e.getSource()))
		{
			new AltaAsigAlumn();
		}
		
		else if (mnl19.equals(e.getSource()))
		{
			//ConsultarAsigAlumn
		}
		
		else if (mnl21.equals(e.getSource()))
		{
			new AltaUsuarios();
		}
		
		else if (mnl23.equals(e.getSource()))
		{
			new ConsultarUsuario();
		}
	
	}

	public void windowActivated(WindowEvent we) {}
	public void windowClosed(WindowEvent we) {}
	public void windowClosing(WindowEvent we)
	   {
		
		
		
			
			 System.exit(0);
		 
		
	   }
	public void windowDeactivated(WindowEvent we) {} 
	public void windowDeiconified(WindowEvent we) {} 
	public void windowIconified(WindowEvent we) {}
	
	public void windowOpened(WindowEvent we) {}

}