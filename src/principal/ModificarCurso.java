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

public class ModificarCurso extends Frame implements WindowListener, ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
	Label lblCurso = new Label("Número de Curso:");
	TextField txtCurso = new TextField(10);
	Label lblAlumnos = new Label("Número Alumnos Curso:");
	TextField txtAlumnos = new TextField(8);
	Choice choCurso = new Choice();
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

	ModificarCurso() {
		setTitle("Modificar Curso");
		setLayout(new FlowLayout());
		add(choCurso);
		add(lblCurso);
		add(txtCurso);
		add(lblAlumnos);
		add(txtAlumnos);
		
		

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

		

		// Cerrar la conexión
		desconectar(con);
		
		add(btnAceptar);
		add(btnBaja);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnBaja.addActionListener(this);
		btnLimpiar.addActionListener(this);
		

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
		new ModificarCurso();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object objetoPulsado = e.getSource();
		if (objetoPulsado.equals(btnLimpiar)) {
			txtCurso.selectAll();
			txtCurso.setText("");
			
			txtAlumnos.selectAll();
			txtAlumnos.setText("");
			txtAlumnos.requestFocus();
			
		} 
		 
		else if (objetoPulsado.equals(btnAceptar)) {
			
		
			
			
			int curso = Integer.parseInt(txtCurso.getText());
			int alumnos = Integer.parseInt(txtAlumnos.getText());
			String Curso = choCurso.getSelectedItem(); // curso="1-5,15"
			// arrayCho curso.split -
			String[] arrayCho = Curso.split("-");
			// int idCursoFK = arrayCho[0];
			String idCurso = arrayCho[0];
			
		

			try {

				Connection con = conectar();
				
				String sql = "UPDATE Curso SET"
						+ " nCurso=? ,"
						+ " nAlumnosCursos=? ,"
					
						+ "WHERE idCurso=? "  ;	
				
				PreparedStatement pStatement = con.prepareStatement(sql);
				ficheroLog.metodo("usuario", "ModificarCurso");
				
		
				pStatement.setString(3, arrayCho[0]);
				pStatement.setInt(1, curso);
				pStatement.setInt(2, alumnos);
				
				
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
					String[] Curso =choCurso.getSelectedItem().split("-");
					int respuesta = borrar(con, Integer.parseInt(Curso[0]));
					ficheroLog.metodo("usuario", "BajaCurso");
					// Mostramos resultado
					if(respuesta == 0)
					{
						System.out.println("Borrado de Curso correcto");
					}
					else
					{
						System.out.println("Error en Curso ");
					}
					// Actualizar el Choice
					choCurso.removeAll();
					choCurso.add("Seleccionar uno...");
					String sqlSelect = "SELECT * FROM Curso";
					try {
						// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
						Statement stmt = con.createStatement();
						ResultSet rs = stmt.executeQuery(sqlSelect);
						while (rs.next()) 
						{

							choCurso.add(rs.getInt("iCurso") 
									+ "-" + rs.getInt("nCurso") 
									+ ", "+ rs.getInt("nAlumnosCursos"));
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
	

	private int borrar(Connection con, int idCurso) {
		int respuesta = 0;
		String sql = "DELETE FROM curso WHERE idCurso = " + idCurso;
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
			System.out.println("ERROR:No se puede dar de baja al curso");
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
		String sqlSelect2 = "SELECT nCurso, nAlumnosCursos FROM Curso WHERE idCurso = "+array[0];

		System.out.println(sqlSelect2);
		// rs.getInt('Edad');
		// rs.getString('Notas');
		// rs.getString(idCursoFK)
		// ...
		
		try {
			Statement stmt2 = con2.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
			rs2.next();
				txtCurso.setText(rs2.getString("nCurso"));
				txtAlumnos.setText(rs2.getString("nAlumnosCursos"));
				
		
			rs2.close();
			stmt2.close();
			
			

		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();

		}
	

	

	
	}
}
