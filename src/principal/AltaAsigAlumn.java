package principal;

import java.awt.Button;

import java.awt.Choice;
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





public class AltaAsigAlumn extends Frame implements WindowListener, ActionListener {

	private static final long serialVersionUID = 1L;
	Label lblAsignaturas = new Label("Asignaturas:");
	Choice choAsignatura = new Choice();
	Label lblAlumnos = new Label("Alumnos:");
	Choice choAlumnos = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	


	
	AltaAsigAlumn()
	{
		setTitle("Alta AsignaturaAlumnos");
		setLayout(new FlowLayout());
		//Montar el choice
		choAsignatura.add("Seleccionar uno...");
		choAlumnos.add("Seleccionar uno...");
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
		add(lblAsignaturas);
		add(choAsignatura);
		add(lblAlumnos);
		add(choAlumnos);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(280,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);


	}

//public static void main(String[] args) 
//{
	//new AltaAsigAlumn();

//}	
		
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
		
		// Mostramos resultado
		if (respuesta == 0) {
		
			
			System.out.println("ALTA de AsigAlumn correcta");
		} else {
			
			System.out.println("Error en ALTA de AsignAlumn");
		}
		// Desconectar de la base
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
	private void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
		
	}
}
