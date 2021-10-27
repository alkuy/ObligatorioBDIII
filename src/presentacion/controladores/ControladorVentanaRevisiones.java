package presentacion.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.interfaces.IFachada;
import logica.valueObjects.VORevision;
import presentacion.ventanas.VentanaRevisiones;

public class ControladorVentanaRevisiones {
		// Tengo como atributo remoto a la fachada y como atributo local a mi ventana
		private IFachada fachada;
		@SuppressWarnings("unused") // Codigo automatico para eliminar la warning de esta linea
		private VentanaRevisiones vent;
		
		public ControladorVentanaRevisiones (VentanaRevisiones v) throws PersistenciaException {
			this.vent = v;
		}
				
		public void AgregarRevision (String cod, String desc){
			try{
				this.fachada.AgregarRevision(cod, desc);
				
				// Despliego mensaje de confirmacion en pantalla
				JOptionPane.showMessageDialog(null, "Revision agregada correctamente.");
				
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RevisionException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}
		}
		
		public String DarDescripcion (String cod, int num){
			String desc = null;
			try{
				desc = this.fachada.DarDescripcion(cod, num);
				
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (FolioException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RevisionException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}
			return desc;
		}
		
		public ArrayList<VORevision> ListarRevisiones (String cod){
			ArrayList<VORevision> lista = null;
			try{
				lista = this.fachada.ListarRevisiones(cod);
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (FolioException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}
			return lista;
		}
}
