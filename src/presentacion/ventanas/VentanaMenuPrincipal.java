package presentacion.ventanas;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

import logica.excepciones.PersistenciaException;
import presentacion.controladores.ControladorVentanaMenuPpal;
import javax.swing.JLabel;

public class VentanaMenuPrincipal {

	// Atributos
	public JFrame frame;
	
	// Constructor
	public VentanaMenuPrincipal() throws PersistenciaException{
		new ControladorVentanaMenuPpal(this);
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 429, 274);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnIrAFolio = new JButton("");
		ImageIcon ImgBtnFolio = new ImageIcon(getClass().getResource("/presentacion/imagenes/Folio.png"));
		btnIrAFolio.setIcon(ImgBtnFolio);
		btnIrAFolio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					VentanaFolios vent = new VentanaFolios();
					vent.Visualizar();
			}
		});
		btnIrAFolio.setBounds(10, 48, 183, 172);
		frame.getContentPane().add(btnIrAFolio);
		
		JButton btnIrARevision = new JButton("");
		ImageIcon ImgBtnRevision = new ImageIcon(getClass().getResource("/presentacion/imagenes/Revision.png"));
		btnIrARevision.setIcon(ImgBtnRevision);
		btnIrARevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaRevisiones vent = new VentanaRevisiones();
				vent.Visualizar();
			}
		});
		btnIrARevision.setBounds(220, 48, 183, 172);
		frame.getContentPane().add(btnIrARevision);
		
		JLabel lblIrAFolio = new JLabel("Ir a Folio");
		lblIrAFolio.setBounds(73, 23, 60, 14);
		frame.getContentPane().add(lblIrAFolio);
		
		JLabel lblIrARevision = new JLabel("Ir a Revisi\u00F3n");
		lblIrARevision.setBounds(275, 23, 81, 14);
		frame.getContentPane().add(lblIrARevision);
	}
}
