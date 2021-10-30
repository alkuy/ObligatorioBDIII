package presentacion.ventanas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import logica.valueObjects.VOFolioMaxRev;
import presentacion.controladores.ControladorVentanaFolios;

import javax.swing.JPanel;
import javax.swing.JTable;



import javax.swing.JFrame;

public class VentanaFolios {
	// Atributos
	private JFrame frame;
	private JTextField txtCodigoBorrar;
	private JTextField txtCodigo;
	private JTextField txtCaratula;
	private JTextField txtPaginas;
	private JTable table;
	private ControladorVentanaFolios controlador;

	
	/**
	 * Create the application.
	 */
	public VentanaFolios() {
		controlador = new ControladorVentanaFolios(this);
		initialize();
	}

	public void Visualizar () {
		frame.setVisible(true);
	}
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1038, 528);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmAgregarFolio = new JMenuItem("Agregar Folio");
		menuBar.add(mntmAgregarFolio);
		
		JMenuItem mntmBorrarFolio = new JMenuItem("Borrar Folio");
		menuBar.add(mntmBorrarFolio);
		
		JMenuItem mntmListarFolios = new JMenuItem("Listar Folios");
		menuBar.add(mntmListarFolios);
		
		JMenuItem mntmMasRevisado = new JMenuItem("Mas revisado");
		menuBar.add(mntmMasRevisado);
		frame.getContentPane().setLayout(null);
		
		Panel panelAgregarFolio = new Panel();
		panelAgregarFolio.setBounds(10, 10, 1002, 126);
		frame.getContentPane().add(panelAgregarFolio);
		panelAgregarFolio.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(301, 55, 89, 23);
		panelAgregarFolio.add(btnAgregar);
		
		JLabel label_2 = new JLabel("Codigo: ");
		label_2.setBounds(10, 24, 75, 14);
		panelAgregarFolio.add(label_2);
		
		JLabel label_3 = new JLabel("Caratula: ");
		label_3.setBounds(10, 59, 75, 14);
		panelAgregarFolio.add(label_3);
		
		JLabel label_4 = new JLabel("Paginas: ");
		label_4.setBounds(10, 90, 75, 14);
		panelAgregarFolio.add(label_4);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(95, 21, 134, 20);
		panelAgregarFolio.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCaratula = new JTextField();
		txtCaratula.setColumns(10);
		txtCaratula.setBounds(95, 56, 134, 20);
		panelAgregarFolio.add(txtCaratula);
		
		txtPaginas = new JTextField();
		txtPaginas.setColumns(10);
		txtPaginas.setBounds(95, 87, 134, 20);
		panelAgregarFolio.add(txtPaginas);
		
		Panel panelBorrarFolio = new Panel();
		panelBorrarFolio.setBounds(10, 142, 1002, 43);
		frame.getContentPane().add(panelBorrarFolio);
		panelBorrarFolio.setLayout(null);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(265, 11, 89, 23);
		panelBorrarFolio.add(btnBorrar);
		
		JLabel label = new JLabel("Codigo: ");
		label.setBounds(10, 15, 75, 14);
		panelBorrarFolio.add(label);
		
		txtCodigoBorrar = new JTextField();
		txtCodigoBorrar.setBounds(95, 12, 130, 20);
		panelBorrarFolio.add(txtCodigoBorrar);
		txtCodigoBorrar.setColumns(10);
		
		Panel panelMasRevisado = new Panel();
		panelMasRevisado.setBounds(10, 191, 1002, 85);
		frame.getContentPane().add(panelMasRevisado);
		panelMasRevisado.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(10, 11, 75, 14);
		panelMasRevisado.add(lblCodigo);
		
		JLabel lblCaratula = new JLabel("Caratula: ");
		lblCaratula.setBounds(10, 35, 75, 14);
		panelMasRevisado.add(lblCaratula);
		
		JLabel lblPaginas = new JLabel("Paginas: ");
		lblPaginas.setBounds(10, 60, 75, 14);
		panelMasRevisado.add(lblPaginas);
		
		JLabel lblcodigoMasRevisado = new JLabel("LblCodigo");
		lblcodigoMasRevisado.setBounds(93, 11, 201, 14);
		panelMasRevisado.add(lblcodigoMasRevisado);
		
		JLabel lblcaratulaMasRevisado = new JLabel("LblCaratula");
		lblcaratulaMasRevisado.setBounds(93, 35, 201, 14);
		panelMasRevisado.add(lblcaratulaMasRevisado);
		
		JLabel lblpaginasMasRevisado = new JLabel("LblPaginas");
		lblpaginasMasRevisado.setBounds(93, 60, 201, 14);
		panelMasRevisado.add(lblpaginasMasRevisado);
		
		JPanel panelListarFolios = new JPanel();
		panelListarFolios.setBounds(10, 282, 1002, 172);
		frame.getContentPane().add(panelListarFolios);
		
		table = new JTable();
		panelListarFolios.add(table);
	
		mntmAgregarFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarFolio.setVisible(true);
				panelMasRevisado.setVisible(false);
				panelBorrarFolio.setVisible(false);
				panelListarFolios.setVisible(false);
			}
		});
		
		mntmBorrarFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarFolio.setVisible(false);
				panelMasRevisado.setVisible(false);
				panelBorrarFolio.setVisible(true);
				panelListarFolios.setVisible(false);
			}
		});
		
		mntmListarFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarFolio.setVisible(false);
				panelMasRevisado.setVisible(false);
				panelBorrarFolio.setVisible(false);
				panelListarFolios.setVisible(true);
			}
		});
		
		// Boton mostrar el folio mas revisado
		mntmMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarFolio.setVisible(false);
				panelMasRevisado.setVisible(true);
				panelBorrarFolio.setVisible(false);
				panelListarFolios.setVisible(false);
				
				String cod = txtCodigoBorrar.getText();

				// Llamo al controlador de agregar vianda con los datos obtenidos de la ventana
				VOFolioMaxRev vo = controlador.FolioMasRevisado();
				lblpaginasMasRevisado.setText(String.valueOf(vo.getPaginas()));
				lblcaratulaMasRevisado.setText(vo.getCaratula());
				lblcodigoMasRevisado.setText(vo.getCodigo());
				//lblCantMasRevisado = vo.getCantRevisiones();
			}
		});
		
		// Boton para agregar un folio
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodigo.getText().isEmpty() || txtCaratula.getText().isEmpty() || txtPaginas.getText().isEmpty() ) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar todos los campos antes de agregar el folio.");
				}else{
					String cod = txtCodigo.getText();
					String cara =  txtCaratula.getText();
					int pag = Integer.parseInt(txtPaginas.getText());
					
					// Llamo al controlador de agregar vianda con los datos obtenidos de la ventana
					controlador.AgregarFolio(cod, cara, pag);
				}
			}
		});
		
		// Boton para borrar un folio y sus revisiones
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodigoBorrar.getText().isEmpty()) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe ingresar un codigo de folio antes borrarlo.");
				}else{
					String cod = txtCodigoBorrar.getText();
								
					// Llamo al controlador borrar folio con los datos obtenidos de la ventana
					controlador.BorrarFolio(cod);
				}
			}
		});
		
	}

}
