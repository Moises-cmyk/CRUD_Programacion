package principal;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class ConsultarAlumnos extends Frame implements WindowListener, ActionListener 
{
	TextArea consulta = new TextArea();
	Button btnAceptar = new Button("Aceptar");
	Button btnPDF = new Button("Exportar a PDF");

	//Creamos documento
		Document documento = new Document();
		File path = new File("fichero.pdf");


	ConsultarAlumnos()
	{
		setTitle("Consultar Alumnos");
		setLayout(new FlowLayout());
		// Conectar a la base de datos
		Connection con = conectar();
		
		//Sacar la información
		rellenarTextArea(con, consulta);
		//Cerrar la conexión
		desconectar(con);
		consulta.setEditable(false);
		add(consulta);
		add(btnAceptar);
		add(btnPDF);
		btnAceptar.addActionListener(this);
		btnPDF.addActionListener(this);
		addWindowListener(this);
		setSize(700,500);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	

	

	public static void main(String[] args) 
	{
		new ConsultarAlumnos();

	}

	@Override
	
		public void actionPerformed(ActionEvent ae) {
			Object objetoPulsado = ae.getSource();
			if(objetoPulsado.equals(btnAceptar))
			{
				setVisible(false);
			}
			
			if(objetoPulsado.equals(btnPDF))
			{
			try
			{
				// Se crea el OutputStream para el fichero donde queremos dejar el pdf.
				FileOutputStream ficheroPdf = new FileOutputStream("ficheroAlumnos.pdf");
				// Se asocia el documento al OutputStream y se indica que el espaciado entre
				// lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
				PdfWriter.getInstance(documento,ficheroPdf).setInitialLeading(20);
				// Se abre el documento.
				documento.open();
				documento.add(new Paragraph("Está usted consultando la tabla Alumnos.",
						FontFactory.getFont("arial",/*Fuetne*/16,/*tamaño*/Font.ITALIC)));
				Image foto = Image.getInstance("alumnos.jpg");
				foto.scaleToFit(100, 100); 
				foto.setAlignment(Chunk.ALIGN_MIDDLE);
				documento.add(foto);
				//documento.add(new Paragraph(consulta.getText()));
				//Definimos el número de columnas de la tabla
				PdfPTable tabla = new PdfPTable(7); 
				//Definimos los campos que seleccionaremos e introduciremos en la tabla
				String sqlSelect = "SELECT Alumnos.idAlumnos, NombreAlumnos, ApellidosAlumnos, EdadAlumnos,NotasAsignaturaAlumnos, nCurso, nAlumnosCursos FROM Alumnos, Curso WHERE Alumnos.idCursoFK4 = idCurso ";	
				try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Connection con = conectar();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next())
					{ 
						 
								 
							
							//Añadimos en la tabla los campos que deseamos
							tabla.addCell(rs.getString("idAlumnos"));
							tabla.addCell(rs.getString("NombreAlumnos"));
							tabla.addCell(rs.getString("ApellidosAlumnos"));
							tabla.addCell(rs.getString("EdadAlumnos"));
							tabla.addCell( rs.getString("NotasAsignaturaAlumnos"));
							tabla.addCell(rs.getString("nCurso"));
							tabla.addCell(rs.getString("nAlumnosCursos"));
					}	
					
					documento.add(tabla);
					documento.close();
					rs.close();
					stmt.close();
					
				}
				catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}
				
				//Abrimos el archivo PDF recién creado 
				try 
				{ 
					File path = new File ("fichero.pdf"); 
					Desktop.getDesktop().open(path);
					} 
				catch (IOException ex)
				{ 
					System.out.println("Se ha producido un error al abrir el archivo PDF"); 
				} 
				} 
			catch ( Exception e ) 
			{
				e.printStackTrace(); 
			} 
			}
			else
			{
				System.out.println("Exportando a PDF");
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
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
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
	
	private void rellenarTextArea(Connection con, TextArea t) {
		String sqlSelect = "SELECT Alumnos.idAlumnos, NombreAlumnos, ApellidosAlumnos, EdadAlumnos,NotasAsignaturaAlumnos, nCurso, nAlumnosCursos FROM Alumnos, Curso WHERE Alumnos.idCursoFK4 = idCurso ";	
		try {
					// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery(sqlSelect);
					while (rs.next()) {
						if (consulta.getText().length() == 0) {
							consulta.setText(rs.getInt("idAlumnos") + "-" + rs.getString("NombreAlumnos") + ", "
									+ rs.getString("ApellidosAlumnos") + ", " + rs.getInt("EdadAlumnos") + ", "
									+ rs.getInt("NotasAsignaturaAlumnos") +";"+rs.getInt("nCurso") + "," + rs.getInt("nAlumnosCursos")
										);
						} else {
							consulta.setText(consulta.getText() + "\n" +rs.getInt("idAlumnos") + "-" + rs.getString("NombreAlumnos") + ", "
									+ rs.getString("ApellidosAlumnos") + ", " + rs.getInt("EdadAlumnos") + ", "
									+ rs.getInt("NotasAsignaturaAlumnos") +";"+rs.getInt("nCurso") + "," + rs.getInt("nAlumnosCursos")
										);
						}
					}
					rs.close();
					stmt.close();
				} catch (SQLException ex) {
					System.out.println("ERROR:al consultar");
					ex.printStackTrace();
				}


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