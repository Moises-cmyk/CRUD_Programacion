package principal;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Frame;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AltaAlumnos extends Frame implements WindowListener, ActionListener
{

	private static final long serialVersionUID = 1L;
	Label lblNombre = new Label("Nombre");
	TextField txtNombre = new TextField(20);
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField(20);
	Label lblEdad = new Label("Edad");
	TextField txtEdad = new TextField(20);
	Label lblNotas = new Label("Notas Alumnos");
	TextField txtNotas = new TextField(20);
	Label lblCurso = new Label("Curso");
	Choice choCurso = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");


public AltaAlumnos()
{
	setTitle("Alta Alumnos");
	setLayout(new FlowLayout());
	add(lblNombre);
	add(txtNombre);
	add(lblApellidos);
	add(txtApellidos);
	add(lblEdad);
	add(txtEdad);
	add(lblNotas);
	add(txtNotas);
	add(lblCurso);
	//Montar el choice
	choCurso.add("Seleccionar un...");
	//Conectar a la base de datos
	Connection con = conectar();
	//Sacar los datos de la tabla curso
	//Rellenar el choice
	String sqlSelect = "SELECT * FROM Curso ";
	try
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlSelect);
		while(rs.next())
		{
			choCurso.add(rs.getInt("idCurso")+"-"+rs.getInt("nCurso")+","+rs.getInt("nAlumnosCursos"));
		}
		rs.close();
		stmt.close();
	}
	catch (SQLException ex)
	{
		System.out.println("Error: al consultar");
		ex.printStackTrace();
	}
	//Cerrar la conexi�n
	desconectar(con);
	add(choCurso);
	add(btnAceptar);
	add(btnLimpiar);
	btnAceptar.addActionListener(this);
	btnLimpiar.addActionListener(this);
	addWindowListener(this);
	setSize(250,400);
	setResizable(false);
	setLocationRelativeTo(null);
	setVisible(true);
	
}

public static void main (String[]args)
{
	new AltaAlumnos();
}

public void ationPerformed(ActionEvent e)
{
	Object objetoPulsado = e.getSource();
	if(objetoPulsado.equals(btnLimpiar))
	{
		txtNombre.selectAll();
		txtNombre.setText("");
		txtNombre.requestFocus();
		txtApellidos.selectAll();
		txtApellidos.setText("");
		txtApellidos.requestFocus();
		txtEdad.selectAll();
		txtEdad.setText("");
		txtEdad.requestFocus();
		txtNotas.selectAll();
		txtNotas.setText("");
		txtNotas.requestFocus();
		choCurso.select(0);
	}
	else if(objetoPulsado.equals(btnAceptar))
	{
		//Conectar BD
		Connection con = conectar();
		
		String[] Curso =choCurso.getSelectedItem().split("-");
	
		//Hacer el INSERT
		int respuesta = insertar(con,"Alumnos",txtNombre.getText(),txtApellidos.getText(),
				txtEdad.getText(), txtNotas.getText(),Integer.parseInt(Curso[0]));
		
		//Mostramos resultado
		if(respuesta == 0)
		{
			System.out.println("ALTA de empleado correcta");
		}
		else
		{
			System.out.println("Error en ALTA de empleado");
		}
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
	// Establecer la conexi�n con la BD empresa
	con = DriverManager.getConnection(url, login, password);
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

public int insertar(Connection con, String Alumnos, String NombreAlumnos, String ApellidosAlumnos,
		String EdadAlumnos, String NotasAsignaturaAlumnos, int idCursoFK4) {
	int respuesta = 0;
	try {
		// Creamos un STATEMENT para una consulta SQL INSERT.
		Statement sta = con.createStatement();
		String cadenaSQL = "INSERT INTO " + Alumnos
				+ " (`NombreAlumnos`,`ApellidosAlumnos`,`EdadAlumnos`,`NotasAsignaturaAlumnos`,`idCursoFK4`) "
				+ "VALUES ('" + NombreAlumnos + "', '" + ApellidosAlumnos + "', '" + EdadAlumnos
				+ "', '" + NotasAsignaturaAlumnos + "', '" +idCursoFK4 +  "')";

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
private void desconectar(Connection con) 
{
	try
	{
		con.close();
	}
	catch(Exception e) {}
	
}

@Override
public void actionPerformed(ActionEvent arg0) {
	// TODO Auto-generated method stub
	
}

}
