package principal;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
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



public class ModificarProfesor extends Frame implements WindowListener, ActionListener, ItemListener {

	private static final long serialVersionUID = 1L;
		
		Label lblProfesor = new Label("Profesor a modificar:");
		Choice choProfesor = new Choice();
		Button btnAceptar = new Button("Modificar");
		Button btnBaja = new Button("Baja");
		Button btnLimpiar = new Button("Limpiar");
		Dialog seguro;
        //Dialog alta;
		Button btnSi;
		Button btnNo;

		Label lblDni = new Label("DNI:");
		TextField txtDni = new TextField(20);
		
		
		Label lblClave = new Label("Clave:");
		TextField txtClave = new TextField(20);
		
		
		Label lblNombre = new Label("Nombre:");
		TextField txtNombre = new TextField(20);
		
		
		Label lblApellido = new Label("Apellidos:");
		TextField txtApellido = new TextField(20);
		
		
		Label lblAsignatura = new Label("Asignatura:");
		TextField txtAsignatura = new TextField(15);
		
		
		Label lblEdad = new Label("Edad:");
		TextField txtEdad = new TextField(15);
		
		
		Label lblEspe = new Label("Especializacion:");
		TextField txtEspe = new TextField(15);
		
		
		Dialog correcto = new Dialog(this,"¡Operación realizada!",true);
		Dialog incorrecto = new Dialog(this,"Error",true);
		
		//Correcto
		Label lbCorrecto = new Label("La acción se ha realizado exitosamente.");
		//Incorrecto
		Label lbIncorrecto = new Label("La acción no se ha podido realizar.");
		
		Connection con = null;

		private String idProfesor;

		
		
		ModificarProfesor()
		{
			setTitle("Modificar Profesor");
			setLayout(new FlowLayout());
			// Montar el Choice
			choProfesor.add("Seleccionar uno...");
			

		
			// Conectar a la base de datos
			con = conectar();
			// Sacar los datos de la tabla edificios
			// Rellenar el Choice
			String sqlSelect = "SELECT * FROM Profesor";
			String sql1 = "SELECT dniProfesor, claveProfesor, NombreProfesor, Apellidos,AsignaturaProfesor, EdadProfesor, EspecializacionProfesor FROM Profesor WHERE idEmpleado , NombreProfesor ,dniProfesor  ";

			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choProfesor.add(rs.getInt("idProfesor")+
							"-"+rs.getString("nombreProfesor")+","+rs.getString("dniProfesor"));
					//String s = rs.getString("idProfesor");
					//s = s + "-"+ rs.getString("NombreProfesor");
					//choEmpleado.add(s);
					txtDni.add(rs.getString("dniProfesor"));
				}
				rs.close();
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("ERROR:al consultar");
				ex.printStackTrace();
			}
			// Cerrar la conexión
			//desconectar(con);
			
			add(choProfesor);
			
			
			add(lblDni);
			add(txtDni);
			
			add(lblClave);
			add(txtClave);
			
			add(lblNombre);
			add(txtNombre);
			
			add(lblApellido);
			add(txtApellido);
			
			add(lblAsignatura);
			add(txtAsignatura);
		
			add(lblEdad);
			add(txtEdad);
			
			add(lblEspe);
			add(txtEspe);
			
			add(btnAceptar);
			add(btnBaja);
			add(btnLimpiar);
			
			
			btnAceptar.addActionListener(this);
			btnBaja.addActionListener(this);
			btnLimpiar.addActionListener(this);
			
			choProfesor.addItemListener(this);
			
			//Dialosgos correcto e incorrecto
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
			setSize(400,500);
			setResizable(false);
			setLocationRelativeTo(null);
			setVisible(true);
		}

		
		public static void main(String[]args)
		{
			new ModificarProfesor();
		}



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
Object objetoPulsado = e.getSource();
		
		if(objetoPulsado.equals(btnLimpiar))
		{
			
			choProfesor.select(0);
			txtDni.setText(" ");
		}
		
			try
			{
				String dni = txtDni.getText();
				String clave = txtClave.getText();
				String nombre = txtNombre.getText();
				String apellido = txtApellido.getText();
				String asignatura = txtAsignatura.getText();
				String edad = txtEdad.getText();
				String espe = txtEspe.getText();
				
				Statement stmt = con.createStatement();
				int resp = stmt.executeUpdate("UPDATE Profesor SET dniProfesor='"+dni+"'AND claveProfesor='"+clave+"'NombreProfesor='"+nombre+"'Apellidos='"+apellido+"'AsignaturaProfesor='"+asignatura+"'EdadProfesor='"+edad+"'EspecializacionProfesor='"+espe+"' WHERE "
						+ "dniProfesor='"+txtDni.getText()+"'claveProfesor='"+txtClave.getText()+"'NombreProfesor='"+txtNombre.getText()+"'Apellidos='"+txtApellido.getText()+"'AsignaturaProfesor='"+txtAsignatura.getText()+"'EdadProfesor='"+txtEdad.getText()+"'EspecializacionProfesor='"+txtEspe.getText()+"';");
				
		        if (resp > 0) 
		        {
		            correcto.setVisible(true);
		        }
		        else
		        {
		        	incorrecto.setVisible(true);
		        }
				
			}
			
			catch (SQLException sqle)
			{
				System.out.println("Error 2-"+sqle.getMessage());
				incorrecto.setVisible(true);
			}
			
		
		if(objetoPulsado.equals(btnNo))
		{
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
			//setVisible(false);
			System.exit(0);
		}
		else
		{
			
			correcto.setVisible(false);
			incorrecto.setVisible(false);
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
	


}
