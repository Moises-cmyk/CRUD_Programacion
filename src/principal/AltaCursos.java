package principal;

import java.awt.Frame;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;

import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class AltaCursos extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblCurso = new Label("Número de Curso:");
	TextField txtCurso = new TextField(10);
	Label lblAlumnos = new Label("Número Alumnos Curso:");
	TextField txtAlumnos = new TextField(8);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	

	AltaCursos()
	{
		setTitle("Alta Cursos");
		setLayout(new FlowLayout());
		add(lblCurso);
		add(txtCurso);
		add(lblAlumnos);
		add(txtAlumnos);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		setSize(280,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		addWindowListener(this);
	}
	//public static void main(String[] args) 
	//{
		//new AltaCursos();

	//}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		 
			Object objetoPulsado = e.getSource();
			if(objetoPulsado.equals(btnLimpiar))
			{
				txtCurso.selectAll();
				txtCurso.setText("");
				
				txtAlumnos.selectAll();
				txtAlumnos.setText("");
				txtAlumnos.requestFocus();
				
			}
			else if(objetoPulsado.equals(btnAceptar))
			{
				//Conectar BD
				Connection con = conectar();
				//Hacer el INSERT
				int respuesta = insertar(con, "Curso",Integer.parseInt(txtCurso.getText()),Integer.parseInt(txtAlumnos.getText()));

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
	public void windowClosing(WindowEvent e)
	{
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
	public void windowOpened(WindowEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
	
	private Connection conectar() {
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
	private int insertar(Connection con, String Curso, int nCurso, int nAlumnosCursos) {
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + Curso + " (`nCurso`,`nAlumnosCursos`) "
												+ "VALUES ('" +nCurso + "', '" + nAlumnosCursos+  "')";
			
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
