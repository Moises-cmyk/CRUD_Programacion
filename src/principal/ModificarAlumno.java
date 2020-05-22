package principal;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.WindowEvent;

public class ModificarAlumno extends Frame implements WindowListener, ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	Label lblNombre = new Label("Nombre");
	TextField txtNombre = new TextField(20);
	Label lblApellidos = new Label("Apellidos");
	TextField txtApellidos = new TextField(20);
	Label lblEdad = new Label("Edad");
	TextField txtEdad = new TextField(20);
	Label lblNotas = new Label("Notas Alumnos");
	TextField txtNotas = new TextField(15);
	Label lblCurso = new Label("Curso");
	Choice choCurso = new Choice();
	Choice choAlumnos = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnBaja = new Button("Baja");
	Button btnLimpiar = new Button("Limpiar");

	Dialog seguro;
//	Dialog alta;
	Button btnSi;
	Button btnNo;

	Dialog correcto = new Dialog(this, "¡Operación realizada!", true);
	Dialog incorrecto = new Dialog(this, "Error", true);

	// Correcto
	Label lbCorrecto = new Label("La acción se ha realizado exitosamente.");
	// Incorrecto
	Label lbIncorrecto = new Label("La acción no se ha podido realizar.");

	Connection con = null;

	ModificarAlumno() {
		setTitle("Modificar Alumnos");
		setLayout(new FlowLayout());
		add(choAlumnos);
		choAlumnos.add("Seleccionar uno...");
		add(lblNombre);
		add(txtNombre);
		add(lblApellidos);
		add(txtApellidos);
		add(lblEdad);
		add(txtEdad);
		add(lblNotas);
		add(txtNotas);
		add(lblCurso);

		///////////////////////////////////////// Insercion datos en Choice
		///////////////////////////////////////// Curso//////////////////////////////////////////////////////////
		// Montar el choice
		choCurso.add("Seleccionar un...");

		// Conectar a la base de datos
		Connection con = conectar();

		// Sacar los datos de la tabla curso
		// Rellenar el choice
		String sqlSelect = "SELECT * FROM Curso ";
		try {
			// Con Statement procesamos la sentencia SQL
			Statement stmt = con.createStatement();
			// Guardamos la sentencia
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choCurso.add(rs.getInt("idCurso") + "-" + rs.getInt("nCurso") + "," + rs.getInt("nAlumnosCursos"));
				// 1-5,15
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("Error: al consultar");
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
				choAlumnos.add(rs.getInt("idAlumnos") + "-" + rs.getString("NombreAlumnos") + ","
						+ rs.getString("ApellidosAlumnos"));

			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}

		// Cerrar la conexión
		desconectar(con);
		add(choCurso);
		add(btnAceptar);
		add(btnBaja);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		choAlumnos.addItemListener(this);

		// Dialosgos correcto e incorrecto
		correcto.setLayout(new FlowLayout());
		correcto.setSize(300, 200);
		correcto.addWindowListener(this);
		correcto.setVisible(false);
		correcto.add(lbCorrecto);

		incorrecto.setLayout(new FlowLayout());
		incorrecto.setSize(300, 200);
		incorrecto.addWindowListener(this);
		incorrecto.setVisible(false);
		incorrecto.add(lbIncorrecto);

		addWindowListener(this);
		setSize(250, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		new ModificarAlumno();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
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
		} else if (objetoPulsado.equals(btnAceptar)) {
			String nombre = txtNombre.getText();
			String apellido = txtApellidos.getText();
			String edad = txtEdad.getText();
			String notas = txtNotas.getText();
			String curso = choCurso.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayCho = curso.split("-");
			// int idCursoFK = arrayCho[0];
			String idCursoFK = arrayCho[0];

			try {

				Statement stmt = con.createStatement();
				int resp = stmt.executeUpdate("UPDATE Alumnos SET NombreAlumnos='" + nombre + "'+ ApellidosAlumnos='"
						+ apellido + "'+EdadAlumnos='" + edad + "'+NotasAsignaturaAlumnos='" + notas + "'+idCursoFK4='"
						+ idCursoFK + "'  WHERE NombreAlumnos='" + txtNombre.getText() + "'" + " +ApellidosAlumnos='"
						+ txtApellidos.getText() + "'+ EdadAlumnos= '" + txtEdad.getText()
						+ "'+ NotasAsignaturaAlumnos= '" + txtNotas.getText() + "'+idCursoFK4='" + arrayCho[0] + "';");
				
				

				if (resp > 0) {

					correcto.setVisible(true);
				} else {
					incorrecto.setVisible(true);
				}

			}

			catch (SQLException sqle) {
				System.out.println("Error 2-" + sqle.getMessage());
				incorrecto.setVisible(true);
			}

		} else if (objetoPulsado.equals(btnNo)) {
			seguro.setVisible(false);
		}
		desconectar(con);

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

		if (this.isActive()) {
			// setVisible(false);
			System.exit(0);
		} else {

			correcto.setVisible(false);
			incorrecto.setVisible(false);
			// seguro.setVisible(false);
			// alta.setVisible(false);
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

	public void desconectar(Connection con) {
		try {
			con.close();
		} catch (Exception e) {
		}
	}

	public void itemStateChanged(ItemEvent e) {
		// Conectar a la base de datos
		Connection con2 = conectar();
		/*
		 * Poner en el campo de texto, los datos del alumno que hemos seleccionado de la
		 * lista
		 */
		String[] array = e.getItem().toString().split("-");
		// idAlumno = array[0] --> SELECT * FROM alumnos WHERE idAlumno = array[0]
		String sqlSelect2 = "SELECT NombreAlumnos,ApellidosAlumnos,EdadAlumnos,NotasASignaturaAlumnos,idCursoFK4 FROM Alumnos WHERE idAlumnos = "+array[0];

		System.out.println(sqlSelect2);
		// rs.getInt('Edad');
		// rs.getString('Notas');
		// rs.getString(idCursoFK)
		// ...
		int idCursoFK=0;
		try {
			Statement stmt2 = con2.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
			rs2.next();
				txtNombre.setText(rs2.getString("NombreAlumnos"));
				txtApellidos.setText(rs2.getString("ApellidosAlumnos"));
				txtEdad.setText(rs2.getString("EdadAlumnos"));
				txtNotas.setText(rs2.getString("NotasASignaturaAlumnos"));
			idCursoFK = Integer.parseInt(rs2.getString("idCursoFK4"));
			rs2.close();
			stmt2.close();
			
			

		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();

		}
	

		
	
		
			try {
				// Con Statement procesamos la sentencia SQL
				Statement stmt2 = con.createStatement();
				// Guardamos la sentencia
				ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
				// Seleccionar el elemento concreto del Choice
				
				// Recorrer el Choice
				// Y si coninicide con el idCursoFK4, seleccionar ese elemento del choice
				while(rs2.next()) {
					if(rs2.getString("idAlumnos").equals("idCursoFK"))
					{
					choCurso.select(
							rs2.getString("idCursoFK4") + "-" + rs2.getInt("nCurso") + "," + rs2.getInt("nAlumnosCurso"));
					}
				}
				rs2.close();
				stmt2.close();
			} catch (SQLException ex) {
				System.out.println("Error: al consultar");
				ex.printStackTrace();
			}
	

	
	}
}

