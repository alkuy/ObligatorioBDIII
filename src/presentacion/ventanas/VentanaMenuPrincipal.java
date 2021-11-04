package presentacion.ventanas;

import java.awt.EventQueue;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

import logica.excepciones.PersistenciaException;
import presentacion.controladores.ControladorVentanaMenuPpal;

public class VentanaMenuPrincipal {

	public JFrame frame;
	private ControladorVentanaMenuPpal controlador;
	
	// Constructor
	public VentanaMenuPrincipal() throws PersistenciaException{
		controlador = new ControladorVentanaMenuPpal(this);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnIrAFolio = new JButton("Ir a Folio");
		btnIrAFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					VentanaFolios vent = new VentanaFolios();
					vent.Visualizar();
			}
		});
		btnIrAFolio.setBounds(68, 95, 114, 23);
		frame.getContentPane().add(btnIrAFolio);
		
		JButton btnIrARevision = new JButton("Ir a Revision");
		btnIrARevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRevisiones vent = new VentanaRevisiones();
				vent.Visualizar();
			}
		});
		btnIrARevision.setBounds(261, 95, 114, 23);
		frame.getContentPane().add(btnIrARevision);
	}

}
