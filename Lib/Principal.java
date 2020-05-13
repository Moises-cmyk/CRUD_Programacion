package principal;

import java.awt.Button;


import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Principal extends Frame implements WindowListener, ActionListener

{

	private static final long serialVersionUID = 1L;
	
	//////////////////////////////////////////// AWT /////////////////////////////////////////////////////
	Label lblUsuario = new Label("Usuario : ");
	TextField txtUsuario = new TextField(40);
	Label lblClave = new Label("Clave : ");
	TextField txtClave = new TextField(40);
	Button btnAceptar = new Button ("Aceptar");
	Button btnBorrar = new Button ("Borrar");
	
  ////////////////////////////////////////////Constructor /////////////////////////////////////////////////////
	
	 Principal()
	{
		setLayout(new FlowLayout());
		setTitle("Login");
	
	
		txtClave.setEchoChar('*');
		add(lblUsuario);
		add(txtUsuario);
		add(lblClave);
		add(txtClave);
		add(btnAceptar);
		add(btnBorrar);
		btnAceptar.addActionListener(this);
		btnBorrar.addActionListener(this);
		addWindowListener(this);
		
		setSize(400,300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
	
	
	
	public static void main(String[] args)
	{
		 new Principal();

	}
	
	public void actionPerformed(ActionEvent e)
	{
		/////////////////////////////////// BORRAR //////////////////////////////////////////
		
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnBorrar))
		{
			txtUsuario.selectAll();
			txtUsuario.setText("");
			txtUsuario.requestFocus();
			txtClave.selectAll();
			txtClave.setText("");
			txtClave.requestFocus();
		}
		
		else if (objetoPulsado.equals(btnAceptar))
		{
  
			
			//Obtenemos lo que hemos escrito en usuario, clave y tipo
			String usuario = txtUsuario.getText();
			//Lo ponemos como un String.valueOf porque el txtClave está en un componente JParrafield
			String clave = txtClave.getText();
		
		
			/**
			 * Conectar a la base de datos
			 * Lanzar SELECT idUsuario, tipoUsuario WHERE usuario y clave 
			 * Si HAY uno
			 * Obtengo el tipoUsuario y según sea, mando a un menú o a otro
			 * Instanciar un objeto de la clase MenuPrincipal(tipoUsuario)
			 * SI NO HAY
			 * Mensaje de Error: "Credecniales incorrectas"
			 */
			//Conecto
			Connection con = conectar();
			//Seleccionar
			String sqlSelect = "SELECT tipoUsuario FROM Usuarios WHERE nombreUsuario='"+usuario+"' AND claveUsuario='"+clave+"'";
			try
			{
				//CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				if(rs.next()) // Hay uno
				   {
						
					new menu();	
					
					}
				else // No hay--> Usuario y/o clave erróneos
				{
					
				}
			}
			catch(SQLException ex)
			{
				System.out.println("No funciona");
			}

		}
	}




	



	@Override
	public void windowActivated(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowClosing(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		System.exit(0);
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
	
	public String convertirSHA256(String password)
	{
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("SHA-256");
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
			return null;
		}
		byte[] hash = md.digest(password.getBytes());
		StringBuffer sb = new StringBuffer();
		
		for(byte b : hash)
		{
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}
	
	private Connection conectar() {
		Connection con = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Instituto?useSSL=false";
		String user = "root";
		String password = "Studium2020;";
	
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
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

	
	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
		}

}
