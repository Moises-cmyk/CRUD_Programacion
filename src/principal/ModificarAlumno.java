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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
	Button btnAceptar = new Button("Modificar");
	Button btnBaja = new Button("Baja");
	Button btnLimpiar = new Button("Limpiar");

	////////////////////////Dialogo de Baja/////////////////////////////////
	Dialog seguro;
//	Dialog Baja;
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
		btnBaja.addActionListener(this);
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
		} 
		 
		else if (objetoPulsado.equals(btnAceptar)) {
			
		
			
			String nombre = txtNombre.getText();
			String apellido = txtApellidos.getText();
			int edad = Integer.parseInt(txtEdad.getText());
			int notas = Integer.parseInt(txtNotas.getText());
			String curso = choCurso.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayCho = curso.split("-");
			// int idCursoFK = arrayCho[0];
			String idCursoFK = arrayCho[0];
			
			String id= choAlumnos.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayChos = id.split("-");
			// int idCursoFK = arrayCho[0];
			String idAlumno = arrayChos[0];

			try {

				Connection con = conectar();
				
				String sql = "UPDATE Alumnos SET"
						+ " NombreAlumnos=? ,"
						+ " ApellidosAlumnos=? ,"
						+ " EdadAlumnos=? , "
						+ "NotasAsignaturaAlumnos=? ,"
						+ "idCursoFK4=? "
						+ "WHERE idAlumnos=? "  ;	
				
				PreparedStatement pStatement = con.prepareStatement(sql);
				ficheroLog.metodo("usuario", "ModificarAlumno");
				
		
				pStatement.setString(6, arrayChos[0]);
				pStatement.setString(1, txtNombre.getText());
				pStatement.setString(2, txtApellidos.getText());
				pStatement.setInt(3, edad);
				pStatement.setInt(4, notas);
				pStatement.setString(5, arrayCho[0]);
				
				int result = pStatement.executeUpdate();
				pStatement.close();
				con.close();

				if (result > 0) {

					correcto.setVisible(true);
				} else {
					incorrecto.setVisible(true);
				}
			}
				catch (SQLException sqle) {
					System.out.println("Error 2-" + sqle.getMessage());
					incorrecto.setVisible(true);
				}
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
					String[] Alumno =choAlumnos.getSelectedItem().split("-");
					int respuesta = borrar(con, Integer.parseInt(Alumno[0]));
					ficheroLog.metodo("usuario", "BajaAlumno");
					// Mostramos resultado
					if(respuesta == 0)
					{
						System.out.println("Borrado de Alumno correcto");
					}
					else
					{
						System.out.println("Error en Alumno ");
					}
					// Actualizar el Choice
					choAlumnos.removeAll();
					choAlumnos.add("Seleccionar uno...");
					String sqlSelect = "SELECT * FROM Alumnos";
					try {
						// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(sqlSelect);
						while (rs.next()) 
						{

							choAlumnos.add(rs.getInt("idAlumnos") 
									+ "-" + rs.getString("NombreAlumnos") 
									+ ", "+ rs.getString("ApellidosAlumnos"));
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

		if (this.isActive()) {
			
			setVisible(false);
			
		} else {

			correcto.setVisible(false);
			incorrecto.setVisible(false);
			seguro.setVisible(false);
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
	

	private int borrar(Connection con, int idAlumnos) {
		int respuesta = 0;
		String sql = "DELETE FROM alumnos WHERE idAlumnos = " + idAlumnos;
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
			System.out.println("ERROR:No se puede dar de baja al alumno");
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
	

		
		String sqlSelect3 = "SELECT idCurso,nCurso,nAlumnosCursos  FROM  Curso WHERE idCurso = "+idCursoFK;

			try {
				// Con Statement procesamos la sentencia SQL
				Statement stmt3 = con2.createStatement();
				// Guardamos la sentencia
				ResultSet rs3 = stmt3.executeQuery(sqlSelect3);
				// Seleccionar el elemento concreto del Choice
				
				// Recorrer el Choice
				// Y si coninicide con el idCursoFK, seleccionar ese elemento del choice
				while(rs3.next()) {
					
					choCurso.select(
							rs3.getString("idCurso") + "-" + rs3.getInt("nCurso") + "," + rs3.getInt("nAlumnosCursos"));
					
				}
				rs3.close();
				stmt3.close();
			} catch (SQLException ex) {
				System.out.println("Error: al consultar");
				ex.printStackTrace();
			}
	

	
	}
}

