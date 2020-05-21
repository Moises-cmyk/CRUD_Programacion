package principal;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaUsuarios extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblNombre = new Label("Nombre:");
	TextField txtNombre = new TextField(20);
	Label lblClave = new Label("Clave:");
	TextField txtClave = new TextField(20);
	Label lblTipo = new Label("Tipo Usuario:");
	TextField txtTipo = new TextField(20);
	Button btnAceptar  = new Button("Aceptar");
	Button btnLimpiar  = new Button("Limpiar");

	AltaUsuarios()
	{
		setTitle("Alta Usuarios");
		setLayout(new FlowLayout());
		add(lblNombre);
		add(txtNombre);
		add(lblClave);
		add(txtClave);
		add(lblTipo);
		add(txtTipo);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		setSize(280,250);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(this);
	}
	//public static void main(String[] args) 
	//{
	//	new AltaUsuarios();

	//}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			txtNombre.selectAll();
			txtNombre.setText("");
			
			txtClave.selectAll();
			txtClave.setText("");
			
			txtTipo.selectAll();
			txtTipo.setText("");
			txtTipo.requestFocus();
		
	}
		else if(objetoPulsado.equals(btnAceptar))
		{
			//Conectar BD
			Connection con = conectar();
			//Hacer el INSERT
			int respuesta = insertar(con, "Usuarios",txtNombre.getText(),txtClave.getText(),txtTipo.getText());

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
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		setVisible(false);
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private Connection conectar() 
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
	
	private int insertar(Connection con, String Usuarios, String nombreUsuario, String claveUsuario, String tipoUsuario) {
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + Usuarios + " (`nombreUsuario`,`claveUsuario`,`tipoUsuario`) "
												+ "VALUES ('" + nombreUsuario + "', '" + claveUsuario + "', '" + tipoUsuario  + "')";
			
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
	
	private void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
		
	}


