package principal;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class BajaAsignatura extends Frame implements WindowListener, ActionListener
{

	private static final long serialVersionUID = 1L;
	Label lblAsignatura = new Label("Asignatura borrar:");
	Choice choAsignatura = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	
	BajaAsignatura()
	{
		setTitle("Baja Asignatura");
		setLayout(new FlowLayout());
		// Montar el Choice
		choAsignatura.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla empleados
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM Asignatura";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				//Queremos que nos aparezca el el choice, el idEmpleado y el nombreEmpleado
				choAsignatura.add(rs.getInt("idAsignatura") + "-" + rs.getInt("nAlumnosAsignatura") + ", "
						+ rs.getString("FechaExamenesAsignatura"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexi�n
		desconectar(con);
		add(choAsignatura);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		//Tama�o del layout (ancho y largo)
		setSize(300,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	

	public static void main(String[] args) 
	{
		new BajaAsignatura();

	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			choAsignatura.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			seguro = new Dialog(this,"�Seguro?", true);
			btnSi = new Button("S�");
			btnNo = new Button("No");
			Label lblEtiqueta = new Label("�Est� seguro de eliminar?");
			seguro.setLayout(new FlowLayout());
			//Tama�o del layout (ancho y largo) del dialog
			seguro.setSize(200,100);
			btnSi.addActionListener(this);
			btnNo.addActionListener(this);
			seguro.add(lblEtiqueta);
			seguro.add(btnSi);
			seguro.add(btnNo);
			seguro.addWindowListener(this);
			seguro.setResizable(false);
			seguro.setLocationRelativeTo(null);
			seguro.setVisible(true);
		}
		else if(objetoPulsado.equals(btnNo))
		{
			seguro.setVisible(false);
		}
		else if(objetoPulsado.equals(btnSi))
		{
			// Conectar a BD
			Connection con = conectar(); 
			// Borrar
			String[] Asignatura =choAsignatura.getSelectedItem().split("-");
			int respuesta = borrar(con, Integer.parseInt(Asignatura[0]));
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("Borrado de Asignatura correcto");
			}
			else
			{
				System.out.println("Error en borrado de Asignatura");
			}
			// Actualizar el Choice
			choAsignatura.removeAll();
			choAsignatura.add("Seleccionar uno...");
			String sqlSelect = "SELECT * FROM Asignatura";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choAsignatura.add(rs.getInt("idAsignatura") + "-" + rs.getInt("nAlumnosAsignatura") + ", "
							+ rs.getString("FechaExamenesAsignatura"));
				}
				rs.close();
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("ERROR:al consultar");
				ex.printStackTrace();
			}
			// Desconectar
			desconectar(con);
			seguro.setVisible(false);
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
		// TODO Auto-generated method stub
		if(this.isActive())
		{
			setVisible(false);
		}
		else
		{
			seguro.setVisible(false);
		}
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
	
	
	private Connection conectar() {
		// TODO Auto-generated method stub
		Connection con = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/Instituto?useSSL=false";
		String user = "root";
		String password = "Studium2020;";
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexi�n con la BD empresa
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La direcci�n no es v�lida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	
	private int borrar(Connection con, int idAsignatura) {
		int respuesta = 0;
		String sql = "DELETE FROM Asignatura WHERE idAsignatura = " + idAsignatura;
		System.out.println(sql);
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		}
		catch(MySQLIntegrityConstraintViolationException fk)
		{
			System.out.println("ERROR:No se puede dar de baja esta asignatura");
			respuesta = 1;
		}
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}

	
	
	private void desconectar(Connection con) {
		// TODO Auto-generated method stub
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}

	
}

