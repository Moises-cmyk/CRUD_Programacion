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

public class AltaAsignatura extends Frame implements WindowListener, ActionListener {

	
	
	private static final long serialVersionUID = 1L;
		
		Label lblNumeroAlumnos = new Label("Numero Alumnos:");
		TextField txtNumeroAlumnos = new TextField(20);
		Label lblDuracion = new Label("Duraci�n Asignatura:");
		TextField txtDuracion = new TextField(20);
		Label lblExamenes = new Label("Fecha Examenes:");
		TextField txtExamenes = new TextField(20);
		Label lblPracticas = new Label("Fecha Practicas:");
		TextField txtPracticas = new TextField(20);
		Label lblNumero = new Label("N�mero Profesores:");
		TextField txtNumero = new TextField(20);
		Label lblProfesor = new Label("Profesor:");
		Choice choProfesor = new Choice();
		Button btnAceptar = new Button("Aceptar");
		Button btnLimpiar = new Button("Limpiar");
		
		public AltaAsignatura()
		{
			setTitle("Alta Asignatura");
			setLayout(new FlowLayout());
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
			//Montar el choice
			choProfesor.add("Seleccionar un...");
			//Conectar a la base de datos
			Connection con = conectar();
			//Sacar los datos de la tabla curso
			//Rellenar el choice
			String sqlSelect = "SELECT * FROM Profesor ";
			try
			{
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while(rs.next())
				{
					choProfesor.add(rs.getInt("idProfesor") + "-" + rs.getString("NombreProfesor") + ", "
							+ rs.getInt("dniProfesor"));				
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
			add(choProfesor);
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
	
	
		public static void main(String[] args) 
		{

			new AltaAsignatura();
	    }
		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object objetoPulsado = e.getSource();
			if(objetoPulsado.equals(btnLimpiar))
			{
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
			else if(objetoPulsado.equals(btnAceptar))
			{
				//Conectar BD
				Connection con = conectar();
				
				String[] Profesor = choProfesor.getSelectedItem().split("-");
			
				//Hacer el INSERT
				int respuesta = insertar(con, "Asignatura", Integer.parseInt(txtNumeroAlumnos.getText()),
						Integer.parseInt(txtDuracion.getText()),txtExamenes.getText(),txtPracticas.getText(),
						Integer.parseInt(txtNumero.getText()),Profesor[0]);
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
		
		private int insertar(Connection con, String string, int parseInt, int parseInt2, String text, String text2,
				int parseInt3, String string2) {
			// TODO Auto-generated method stub
			return 0;
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
		public int insertar(Connection con, String Asignatura, String nAlumnosAsignatura, String DuracionAsignatura,
				String FechaExamenesAsignatura, String FechaPracticasAsignatura, String nProfesorAsignatura,
				String idProfesorFK1) {
			int respuesta = 0;
			try {
				// Creamos un STATEMENT para una consulta SQL INSERT.
				Statement sta = con.createStatement();
				String cadenaSQL = "INSERT INTO " + Asignatura
						+ " (`nAlumnosAsignatura`,`DuracionAsignatura`,`FechaExamenesAsignatura`,`FechaPracticasAsignatura`,`nProfesorAsignatura`,`idProfesorFK1`) "
						+ "VALUES ('" + nAlumnosAsignatura + "', '" + DuracionAsignatura + "', '" + FechaExamenesAsignatura
						+ "', '" + FechaPracticasAsignatura + "', '" + nProfesorAsignatura + "','" + idProfesorFK1 + "')";

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

		public void desconectar(Connection con) {
			try {
				con.close();
			} catch (Exception e) {
			}

		}

}
