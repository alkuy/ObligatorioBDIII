package presentacion.ventanas;

import logica.excepciones.PersistenciaException;

public class VentanaLanzador {
	// Lo unico que hara esta clase sera llamar a la ventana del menu principal y mostrarla en pantalla
		public static void main(String[] args) {
			VentanaMenuPrincipal v = null;
			try {
				v = new VentanaMenuPrincipal();
				v.frame.setVisible(true);
			} catch (PersistenciaException e) {
				e.printStackTrace();
				//JOptionPane.showMessageDialog(null, e.getMensaje());
			}
		}
}
