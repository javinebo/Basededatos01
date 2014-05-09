import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

/**
 * comentario para la clase formulario
 * @author Usuario
 * @version 1.0, 09-05-14
 */
public class formulario extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tf1;
	private JTextField tf2;
	private JLabel labelResultado;
	private JButton btnConsultaPorCdigo;
	private JLabel lblIngreseCdigoDe;
	private JTextField tf3;

	/**
	 * metodo principal main 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					formulario frame = new formulario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creacion de la ventana donde se crearan las etiquetas y cajas de texto.
	 */
	
	public formulario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 405);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDescripcinDelArtculo = new JLabel("Descripci�n del art�culo:");
		lblDescripcinDelArtculo.setBounds(23, 38, 193, 14);
		contentPane.add(lblDescripcinDelArtculo);

		tf1 = new JTextField();
		tf1.setBounds(247, 35, 193, 20);
		contentPane.add(tf1);
		tf1.setColumns(10);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(23, 74, 95, 14);
		contentPane.add(lblPrecio);

		tf2 = new JTextField();
		tf2.setBounds(247, 71, 107, 20);
		contentPane.add(tf2);
		tf2.setColumns(10);

	/**
	 * accion de dar de alta a la base de datos en mysql y tiene que estar el servidor arancado.
	 */
		JButton btnAlta = new JButton("Alta");
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelResultado.setText("");
				try {
					Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/bd1", "root", "");
					Statement comando = conexion.createStatement();
					comando.executeUpdate("insert into articulos (descripcion, precio) values ('"+ tf1.getText() + "'," + tf2.getText() + ")");
					conexion.close();
					labelResultado.setText("se registraron los datos");
					tf1.setText("");
					tf2.setText("");
				} catch (SQLException ex) {
					setTitle(ex.toString());
				}
			}
		});
		btnAlta.setBounds(247, 118, 89, 23);
		contentPane.add(btnAlta);

		labelResultado = new JLabel("resultado");
		labelResultado.setBounds(361, 122, 229, 14);
		contentPane.add(labelResultado);
		/**
		 * accion que consulta un codigo 
		 */
		btnConsultaPorCdigo = new JButton("Consulta por c�digo");
		btnConsultaPorCdigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				labelResultado.setText("");
				tf1.setText("");
				tf2.setText("");
				try {
					Connection conexion = DriverManager.getConnection(
							"jdbc:mysql://localhost/bd1", "root", "");
					Statement comando = conexion.createStatement();
					ResultSet registro = comando
							.executeQuery("select descripcion,precio from articulos where codigo="
									+ tf3.getText());
					if (registro.next() == true) {
						tf1.setText(registro.getString("descripcion"));
						tf2.setText(registro.getString("precio"));
					} else {
						labelResultado
								.setText("No existe un art�culo con dicho c�digo");
					}
					conexion.close();
				} catch (SQLException ex) {
					setTitle(ex.toString());
				}
			}
		});
		btnConsultaPorCdigo.setBounds(23, 212, 177, 23);
		contentPane.add(btnConsultaPorCdigo);

		lblIngreseCdigoDe = new JLabel(
				"Ingrese c�digo de articulo a consultar:");
		lblIngreseCdigoDe.setBounds(10, 179, 243, 14);
		contentPane.add(lblIngreseCdigoDe);

		tf3 = new JTextField();
		tf3.setBounds(247, 176, 86, 20);
		contentPane.add(tf3);
		tf3.setColumns(10);
		cargarDriver();
	}
	/**
	 * metodo para cargar el driver en memoria.
	 */
	private void cargarDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception ex) {
			setTitle(ex.toString());
		}
	}
}