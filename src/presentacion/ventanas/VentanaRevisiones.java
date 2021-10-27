package presentacion.ventanas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToolBar;

import logica.excepciones.PersistenciaException;
import presentacion.controladores.ControladorVentanaRevisiones;

import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class VentanaRevisiones {

	private JFrame frame;
	private ControladorVentanaRevisiones controlador;
	private JTextField txtCodigoBuscar;

	public VentanaRevisiones() {
		try {
			controlador = new ControladorVentanaRevisiones(this);
			// Inicializo todos los componentes de la ventana
			initialize();
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1023, 507);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenuItem mntmAgregarRevision = new JMenuItem("Agregar Revision");
		menuBar.add(mntmAgregarRevision);
		
		JMenuItem mntmDarDescripcion = new JMenuItem("Dar Descripcion");
		menuBar.add(mntmDarDescripcion);
		
		JMenuItem mntmListarRevisiones = new JMenuItem("Listar Revisiones");
		menuBar.add(mntmListarRevisiones);
		frame.getContentPane().setLayout(null);
		
		JPanel panelAgregarRevision = new JPanel();
		panelAgregarRevision.setBounds(10, 11, 987, 129);
		frame.getContentPane().add(panelAgregarRevision);
		panelAgregarRevision.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(333, 45, 89, 23);
		panelAgregarRevision.add(btnAgregar);
		
		JPanel panelDarDescripcion = new JPanel();
		panelDarDescripcion.setBounds(10, 151, 987, 89);
		frame.getContentPane().add(panelDarDescripcion);
		panelDarDescripcion.setLayout(null);
		
		JPanel panelListarRevisiones = new JPanel();
		panelListarRevisiones.setBounds(10, 251, 987, 182);
		frame.getContentPane().add(panelListarRevisiones);
		panelListarRevisiones.setLayout(null);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(336, 51, 89, 23);
		panelListarRevisiones.add(btnListar);
		
		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(10, 55, 63, 14);
		panelListarRevisiones.add(lblCodigo);
		
		txtCodigoBuscar = new JTextField();
		txtCodigoBuscar.setBounds(88, 52, 152, 20);
		panelListarRevisiones.add(txtCodigoBuscar);
		txtCodigoBuscar.setColumns(10);
		
		mntmAgregarRevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarRevision.setVisible(true);
				panelDarDescripcion.setVisible(false);
				panelListarRevisiones.setVisible(false);
			}
		});
		
		mntmDarDescripcion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarRevision.setVisible(false);
				panelDarDescripcion.setVisible(true);
				panelListarRevisiones.setVisible(false);
			}
		});
		
		mntmListarRevisiones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelAgregarRevision.setVisible(false);
				panelDarDescripcion.setVisible(false);
				panelListarRevisiones.setVisible(true);
			}
		});
	}

}
