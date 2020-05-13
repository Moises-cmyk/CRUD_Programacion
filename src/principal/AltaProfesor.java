package principal;



import java.awt.Button;


import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaProfesor extends Frame implements WindowListener, ActionListener
{
	
	private static final long serialVersionUID = 1L;
	Label lblDni = new Label("DNI:");
	TextField txtDni = new TextField(20);
	Label lblClave = new Label("Clave:");
	TextField txtClave = new TextField(20);
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(20);
	Label lblApellido = new Label("Apellidos:");
	TextField txtApellido = new TextField(20);
	Label lblAsignatura = new Label("Asignatura:");
	TextField txtAsignatura = new TextField(15);
	Label lblEdad = new Label("Edad:");
	TextField txtEdad = new TextField(15);
	Label lblEspe = new Label("Especializacion:");
	TextField txtEspe = new TextField(15);
	
	Button btnAceptar  = new Button("Aceptar");
	Button btnLimpiar  = new Button("Limpiar");


	public AltaProfesor()
	{
		setTitle("Alta Profesor");
		setLayout(new FlowLayout());
		add(lblDni);
		add(txtDni);
		add(lblClave);
		add(txtClave);
		add(lblNombre);
		add(txtNombre);
		add(lblApellido);
		add(txtApellido);
		add(lblAsignatura);
		add(txtAsignatura);
		add(lblEdad);
		add(txtEdad);
		add(lblEspe);
		add(txtEspe);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		setSize(250,400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}


	public void actionPerformed(ActionEvent e)
	{
		/*Creamos el objeto "objetoPulsado" donde obtendremos el resultado de pulsar
		 * en el boton.*/
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			txtDni.selectAll();
			txtDni.setText("");
			txtDni.requestFocus();
			txtClave.selectAll();
			txtClave.setText("");
			txtClave.requestFocus();
			txtNombre.selectAll();
			txtNombre.setText("");
			txtNombre.requestFocus();
			txtApellido.selectAll();
			txtApellido.setText("");
			txtApellido.requestFocus();
			txtAsignatura.selectAll();
			txtAsignatura.setText("");
			txtAsignatura.requestFocus();
			txtEdad.selectAll();
			txtEdad.setText("");
			txtEdad.requestFocus();
			txtEspe.selectAll();
			txtEspe.setText("");
			txtEspe.requestFocus();
			
			 addWindowListener(this);
			
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			//Conectar BD
			Connection con = conectar();
			//Hacer el INSERT
			int respuesta = insertar(con,"Profesor",txtDni.getText(),txtClave.getText(),
					txtNombre.getText(),txtApellido.getText(),txtAsignatura.getText(),
					txtEdad.getText(), txtEspe.getText());
			//Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("ALTA de empleado correcta");
			}
			else
			{
				System.out.println("Error en ALTA de empleado");
			}
			//Desconectar de la base
			desconectar(con);
		}
	}
		
		@Override
		public void windowActivated(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e)
		{
			// TODO Auto-generated method stub
			//setVisible(false);
			 System.exit(0);
		
		}

		@Override
		public void windowDeactivated(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}
		


	
	public Connection conectar()
	{
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Instituto?useSSL=false";
		String login = "root";
		String password = "Studium2020;";
		Connection con = null;
	
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, login, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	
	
	private int insertar(Connection con, String Profesor, String dniProfesor , String claveProfesor, String NombreProfesor, String Apellidos,
			String AsignaturaProfesor, String EdadProfesor, String EspecializacionProfesor) {
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + Profesor + " (`dniProfesor`,`claveProfesor`,`NombreProfesor`,`Apellidos`,`AsignaturaProfesor`,`EdadProfesor`,`EspecializacionProfesor`) "
												+ "VALUES ('" +dniProfesor + "', '" + claveProfesor + "', '" + NombreProfesor + "', '" + Apellidos + "',"
														+ " '" + AsignaturaProfesor + "', '" + EdadProfesor + "', '" + EspecializacionProfesor + "')";
			
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	public void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
}