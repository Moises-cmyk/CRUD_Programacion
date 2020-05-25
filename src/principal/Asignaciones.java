package principal;

import java.awt.Button;

import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;





public class Asignaciones extends Frame implements WindowListener, ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	
	Label lblAsignaturas = new Label("Asignaturas:");
	Choice choAsignatura = new Choice();
	//Label lblAlumnos = new Label("Alumnos:");
	Choice choAlumnos = new Choice();
	Choice choBaja = new Choice();
	TextArea txtAsignatura = new TextArea();
	Button btnAceptar = new Button("+");
	Button btnBaja = new Button("-");
	Button btnLimpiar = new Button("Limpiar");
	
	///////////Baja///////////////
	Dialog seguro;
	Button btnSi;
	Button btnNo;


	
	Asignaciones()
	{
		setTitle("Alta AsignaturaAlumnos");
		setLayout(new FlowLayout());
		//Montar el choice
		choAsignatura.add("Seleccionar uno...");
		choAlumnos.add("Seleccionar uno...");
		choBaja.add("Datos Seleccionados");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM Asignatura";

		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choAsignatura.add(rs.getInt("idAsignatura") + "-" + rs.getString("FechaExamenesAsignatura")+","+
						rs.getInt("nAlumnosAsignatura"));
						
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		
		
		// Conectar a la base de datos
		Connection con1 = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect1 = "SELECT * FROM Alumnos";

		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con1.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect1);
			while (rs.next()) {
				choAlumnos.add(rs.getInt("idAlumnos") + "-" + rs.getString("NombreAlumnos")+","+
			rs.getString("ApellidosAlumnos"));
						
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		

		// Conectar a la base de datos
		Connection con2 = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect2 = "SELECT * FROM Asignatura";

		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con2.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect2);
			while (rs.next()) {
				choBaja.add(rs.getInt("idAsignatura") + "-" + rs.getString("FechaExamenesAsignatura")+","+
						rs.getInt("nAlumnosAsignatura"));
						
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		add(lblAsignaturas);
		add(choAsignatura);
		add(txtAsignatura);
		add(btnAceptar);
		add(choAlumnos);
		add(btnBaja);
		add(choBaja);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnBaja.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(450,500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


	}

public static void main(String[] args) 
{
	new Asignaciones();

}	
		
public void actionPerformed(ActionEvent e)
{
	Object objetoPulsado = e.getSource();
	if(objetoPulsado.equals(btnLimpiar))
	{
	choAsignatura.select(0);
	choAlumnos.select(0);
	}
	else if(objetoPulsado.equals(btnAceptar))
	{
		Connection con = conectar();
		
		String[] Asignatura=choAsignatura.getSelectedItem().split("-");
		String[] Alumnos =choAlumnos.getSelectedItem().split("-");
		
		// Hacer el INSERT
		int respuesta = insertar(con, "AsignaturasAlumnos",Asignatura[0],Alumnos[0]);
		ficheroLog.metodo("usuario", "Alta AsignaturaAlumnos");
		
		// Mostramos resultado
		if (respuesta == 0) {
		
			
			System.out.println("ALTA de AsigAlumn correcta");
		} else {
			
			System.out.println("Error en ALTA de AsignAlumn");
		}
		// Desconectar de la base
		desconectar(con);
	}
	else if(objetoPulsado.equals(btnBaja))
	{
		seguro = new Dialog(this,"¿Seguro?", true);
		btnSi = new Button("Sí");
		btnNo = new Button("No");
		Label lblEtiqueta = new Label("¿Está seguro de eliminar?");
		seguro.setLayout(new FlowLayout());
		//Tamaño del layout (ancho y largo) del dialog
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
		String[] Alumnos =choAlumnos.getSelectedItem().split("-");
		int respuesta = borrar(con, Asignatura[0], Alumnos[0]);
		ficheroLog.metodo("usuario", "Baja AsignaturaAlumnos");

		// Mostramos resultado
		if(respuesta == 0)
		{
			System.out.println("Borrado de AsignaturaAlumnos correcto");
		}
		else
		{
			System.out.println("Error en borrado de AsignaturaAlumnos");
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
	public void windowOpened(WindowEvent e) {
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
	public int insertar(Connection conectar, String AsignaturasAlumnos, String idAsignaturaFK2,String idAlumnosFK3) {
		int respuesta = 0;
		try {
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = conectar.createStatement();
			String cadenaSQL = "INSERT INTO " + AsignaturasAlumnos
					+ " (`idAsignaturaFK2`,`idAlumnosFK3`) "
					+ "VALUES ('" + idAsignaturaFK2 + "','" + idAlumnosFK3 + "')";

			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	
	private int borrar(Connection con, String idAsignatura, String idAlumnos) {
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
		String sql1 = "DELETE FROM Alumnos WHERE idAlumnos = " + idAlumnos;
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
		try {
			con.close();
		} catch (Exception e) {
		}
		
	}
	
	public void itemStateChanged(ItemEvent e) {
		// Conectar a la base de datos
				Connection con3 = conectar();
				/*
				 * Poner en el campo de texto, los datos del alumno que hemos seleccionado de la
				 * lista
				 */
				String[] array = e.getItem().toString().split("-");
				// idAlumno = array[0] --> SELECT * FROM alumnos WHERE idAlumno = array[0]
				String sqlSelect2 = "SELECT idAsignatura, FechaExamenesAsignatura, nAlumnosAsignatura  FROM Asignatura WHERE idAsignatura = "+array[0];

				System.out.println(sqlSelect2);
				// rs.getInt('Edad');
				// rs.getString('Notas');
				// rs.getString(idCursoFK)
				// ...
				int idAsignatura=0;
				try {
					Statement stmt2 = con3.createStatement();
					ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
					rs2.next();
						txtAsignatura.setText(rs2.getString("idAsignatura"));
						txtAsignatura.setText(rs2.getString("FechaExamenesAsignatura"));
						txtAsignatura.setText(rs2.getString("nAlumnosAsignatura"));
						
					idAsignatura = Integer.parseInt(rs2.getString("idAsignatura"));
					rs2.close();
					stmt2.close();
					
					

				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();

				}
			

				
				String sqlSelect3 = "SELECT idAsignatura, FechaExamenesAsignatura, nAlumnosAsignatura  FROM Asignatura WHERE idAsignatura = "+idAsignatura;

					try {
						// Con Statement procesamos la sentencia SQL
						Statement stmt3 = con3.createStatement();
						// Guardamos la sentencia
						ResultSet rs3 = stmt3.executeQuery(sqlSelect3);
						// Seleccionar el elemento concreto del Choice
						
						// Recorrer el Choice
						// Y si coninicide con el idCursoFK, seleccionar ese elemento del choice
						while(rs3.next()) {
							
							choBaja.add(rs3.getInt("idAsignatura") + "-" + rs3.getString("FechaExamenesAsignatura")+","+
									rs3.getInt("nAlumnosAsignatura"));
							
						}
						rs3.close();
						stmt3.close();
					} catch (SQLException ex) {
						System.out.println("Error: al consultar");
						ex.printStackTrace();
					}
			

			
			}
}