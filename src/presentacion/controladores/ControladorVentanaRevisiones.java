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
		
		// Constructor
		public ControladorVentanaRevisiones (VentanaRevisiones v) throws PersistenciaException {
			this.vent = v;
			try {
				//Obtengo desde el archivo de configuracion los datos del puerto, nombre del objeto e IP
				Properties p = new Properties();
				String nomArch = "src/Config/Config.properties";
				// Abro el archivo properties y leo los datos de configuración
				p.load (new FileInputStream (nomArch));
				String ip = p.getProperty("ipServer");
				String puerto = p.getProperty("portServer");
				String nomObjeto = p.getProperty("objetoRemoto");
				
				String ruta = "//" + ip + ":" + puerto + "/" + nomObjeto;
				this.fachada = (IFachada) Naming.lookup(ruta);
				
			} catch (MalformedURLException | RemoteException | NotBoundException | FileNotFoundException e) {
				e.printStackTrace();
				throw new PersistenciaException("Ocurrio un error de comunicacion con el servidor, contacte con el administrador.");
			} catch (IOException a) {
				throw new PersistenciaException("Ocurrio un error no especificado, contacte con el administrador.");
			}
		}
				
		public void AgregarRevision (String cod, String desc) throws PersistenciaException, RemoteException, RevisionException, FolioException{
			try{
				this.fachada.AgregarRevision(cod, desc);
				
				// Despliego mensaje de confirmacion en pantalla
				JOptionPane.showMessageDialog(null, "Revision agregada correctamente.");
				
			}catch (PersistenciaException e){
				throw new PersistenciaException(e.getMensaje());
			} catch (RemoteException e) {
				throw new RemoteException(e.getMessage());
			} catch (RevisionException e) {
				throw new RevisionException(e.getMensaje());
			} catch (FolioException e) {
				throw new FolioException(e.getMensaje());
			}
		}
		
		public String DarDescripcion (String cod, int num) throws PersistenciaException, RemoteException, RevisionException, FolioException{
			String desc = null;
			try{
				desc = this.fachada.DarDescripcion(cod, num);
				
			}catch (PersistenciaException e){
				throw new PersistenciaException(e.getMensaje());
			} catch (RemoteException e) {
				throw new RemoteException(e.getMessage());
			} catch (RevisionException e) {
				throw new RevisionException(e.getMensaje());
			} catch (FolioException e) {
				throw new FolioException(e.getMensaje());
			}
			return desc;
		}
		
		public ArrayList<VORevision> ListarRevisiones (String cod) throws PersistenciaException, RemoteException, FolioException, RevisionException{
			ArrayList<VORevision> lista = null;
			try{
				lista = this.fachada.ListarRevisiones(cod);
			}catch (PersistenciaException e){
				throw new PersistenciaException(e.getMensaje());
			} catch (RemoteException e) {
				throw new RemoteException(e.getMessage());
			} catch (FolioException e) {
				throw new FolioException(e.getMensaje());
			} catch (RevisionException e) {
				throw new RevisionException(e.getMensaje());
			} 
			
			return lista;
		}
}
