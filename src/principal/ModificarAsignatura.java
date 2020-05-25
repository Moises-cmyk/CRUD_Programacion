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

public class ModificarAsignatura extends Frame implements WindowListener, ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	Label lblNumeroAlumnos = new Label("Numero Alumnos:");
	TextField txtNumeroAlumnos = new TextField(30);
	Label lblDuracion = new Label("Duración Asignatura:");
	TextField txtDuracion = new TextField(30);
	Label lblExamenes = new Label("Fecha Examenes:");
	TextField txtExamenes = new TextField(30);
	Label lblPracticas = new Label("Fecha Practicas:");
	TextField txtPracticas = new TextField(30);
	Label lblNumero = new Label("Número Profesores:");
	TextField txtNumero = new TextField(30);
	Label lblProfesor = new Label("Profesor:");
	Choice choProfesor = new Choice();
	Button btnAcep = new Button("Aceptar");
	Button btnLimp = new Button("Limpiar");
	Dialog dlgcorrecto;
	Dialog dlgIncorrecto;
	
	Choice choAsignatura = new Choice();
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

	ModificarAsignatura() {
		setTitle("Modificar Asignatura");
		setLayout(new FlowLayout());
		add(choAsignatura);
		choAsignatura.add("Seleccionar uno...");
		add(lblNumeroAlumnos);
		add(txtNumeroAlumnos);
		add(lblDuracion);
		add(txtDuracion);
		add(lblExamenes);
		add(txtExamenes);
		add(lblPracticas);
		add(txtPracticas);
		add(lblNumero);
		add(txtNumero);
		

		///////////////////////////////////////// Insercion datos en Choice
		///////////////////////////////////////// Profesor//////////////////////////////////////////////////////////
		// Montar el choice
		choProfesor.add("Seleccionar un...");

		// Conectar a la base de datos
		Connection con = conectar();

		// Sacar los datos de la tabla curso
		// Rellenar el choice
		String sqlSelect = "SELECT * FROM Profesor ";
		try {
			// Con Statement procesamos la sentencia SQL
			Statement stmt = con.createStatement();
			// Guardamos la sentencia
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) {
				choProfesor.add(rs.getInt("idProfesor") + "-" + rs.getString("NombreProfesor") + ", "
						+ rs.getInt("dniProfesor"));				// 1-5,15
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
		String sqlSelect1 = "SELECT * FROM Asignatura";

		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con1.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect1);
			while (rs.next()) {
				choAsignatura.add(rs.getInt("idAsignatura") + "-" + rs.getString("FechaExamenesAsignatura") + ","
						+ rs.getString("nAlumnosAsignatura"));

			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}

		// Cerrar la conexión
		desconectar(con);
		add(choProfesor);
		add(btnAceptar);
		add(btnBaja);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnBaja.addActionListener(this);
		btnLimpiar.addActionListener(this);
		choAsignatura.addItemListener(this);

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
		setSize(260, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
	new ModificarAsignatura();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
			txtNumeroAlumnos.selectAll();
			txtNumeroAlumnos.setText("");
			txtDuracion.selectAll();
			txtDuracion.setText("");
			txtExamenes.selectAll();
			txtExamenes.setText("");
			txtPracticas.selectAll();
			txtPracticas.setText("");
			txtNumero.selectAll();
			txtNumero.setText("");
			choProfesor.select(0);
			txtNumeroAlumnos.requestFocus();
		} 
		 
		else if (objetoPulsado.equals(btnAceptar)) {
			
		
			
			int  nAlumnos = Integer.parseInt(txtNumeroAlumnos.getText());
			int duracion = Integer.parseInt(txtDuracion.getText());
			String examenes = txtExamenes.getText();
			String practicas = txtPracticas.getText();
			int  numero = Integer.parseInt(txtNumero.getText());

			String profesor = choProfesor.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayCho = profesor.split("-");
			// int idCursoFK = arrayCho[0];
			String idProfesorFK = arrayCho[0];
			
			String asignatura= choAsignatura.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayChos = asignatura.split("-");
			// int idCursoFK = arrayCho[0];
			String idAsignatura = arrayChos[0];

			try {

				Connection con = conectar();
				
				String sql = "UPDATE Asignatura SET"
						+ " nAlumnosAsignatura=? ,"
						+ " DuracionAsignatura=? ,"
						+ " FechaExamenesAsignatura=? , "
						+ "FechaPracticasAsignatura=? ,"
						+ "nProfesorAsignatura=? ,"
						+ "idProfesorFK1=? "
						+ "WHERE idAsignatura=? "  ;	
				
				PreparedStatement pStatement = con.prepareStatement(sql);
				ficheroLog.metodo("usuario", "ModificarAsignatura");
				
		
				pStatement.setString(7, arrayChos[0]);
				pStatement.setInt(1, nAlumnos);
				pStatement.setInt(2, duracion);
				pStatement.setString(3, txtExamenes.getText());
				pStatement.setString(4, txtPracticas.getText());
				pStatement.setInt(5, numero);
				pStatement.setString(6, arrayCho[0]);
				
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
					String[] Asignatura =choAsignatura.getSelectedItem().split("-");
					int respuesta = borrar(con, Integer.parseInt(Asignatura[0]));
					ficheroLog.metodo("usuario", "BajaAsignatura");
					// Mostramos resultado
					if(respuesta == 0)
					{
						System.out.println("Borrado de Asignatura correcto");
					}
					else
					{
						System.out.println("Error en Baja Asignatura ");
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

							choAsignatura.add(rs.getInt("idAsignatura") 
									+ "-" + rs.getString("FechaExamenesAsignatura") 
									+ ", "+ rs.getString("nProfesorAsignatura"));
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
	

	private int borrar(Connection con, int idAsignatura) {
		int respuesta = 0;
		String sql = "DELETE FROM asignatura WHERE idAsignatura = " + idAsignatura;
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
		String sqlSelect2 = "SELECT nAlumnosAsignatura, DuracionAsignatura, FechaExamenesAsignatura, FechaPracticasAsignatura, nProfesorASignatura, idProfesorFK1 FROM Asignatura WHERE idAsignatura = "+array[0];

		System.out.println(sqlSelect2);
		// rs.getInt('Edad');
		// rs.getString('Notas');
		// rs.getString(idCursoFK)
		// ...
		int idProfesorFK=0;
		try {
			Statement stmt2 = con2.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
			rs2.next();
				txtNumeroAlumnos.setText(rs2.getString("nAlumnosasignatura"));
				txtDuracion.setText(rs2.getString("DuracionAsignatura"));
				txtExamenes.setText(rs2.getString("FechaExamenesAsignatura"));
				txtPracticas.setText(rs2.getString("FechaPracticasAsignatura"));
				txtNumero.setText(rs2.getString("nProfesorAsignatura"));
              idProfesorFK = Integer.parseInt(rs2.getString("idProfesorFK1"));
			rs2.close();
			stmt2.close();
			
			

		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();

		}
	

		
		String sqlSelect3 = "SELECT idProfesor,NombreProfesor,dniProfesor  FROM  Profesor WHERE idProfesor = "+idProfesorFK;

			try {
				// Con Statement procesamos la sentencia SQL
				Statement stmt3 = con2.createStatement();
				// Guardamos la sentencia
				ResultSet rs3 = stmt3.executeQuery(sqlSelect3);
				// Seleccionar el elemento concreto del Choice
				
				// Recorrer el Choice
				// Y si coninicide con el idProfesorFK, seleccionar ese elemento del choice
				while(rs3.next()) {
					
					choProfesor.add(rs3.getInt("idProfesor") + "-" + rs3.getString("NombreProfesor") + ", "
							+ rs3.getInt("dniProfesor"));
					
				}
				rs3.close();
				stmt3.close();
			} catch (SQLException ex) {
				System.out.println("Error: al consultar");
				ex.printStackTrace();
			}
	

	
	}
}