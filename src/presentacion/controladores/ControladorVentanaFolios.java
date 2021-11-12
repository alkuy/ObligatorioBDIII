package presentacion.controladores;

import java.io.FileInputStream;
import java.io.IOException;
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
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import presentacion.ventanas.VentanaFolios;

public class ControladorVentanaFolios {
	// Tengo como atributo remoto a la fachada y como atributo local a mi ventana
	private IFachada fachada;
	@SuppressWarnings("unused") // Codigo automatico para eliminar la warning de esta linea
	private VentanaFolios vent;
	
	// Constructor
	public ControladorVentanaFolios (VentanaFolios v) throws PersistenciaException {
		this.vent = v;
		try {
			//Obtengo desde el archivo de configuracion los datos del puerto, nombre del objeto e IP
			Properties p = new Properties();
			String nomArch = "src/Config/Config.properties";
			// Abro el archivo properties y leo los datos de configuración
			p.load (new FileInputStream (nomArch));
			String puerto = p.getProperty("portServer");
			String ip = p.getProperty("ipServer");
			String nomObjeto = p.getProperty("objetoRemoto");
			
			String ruta = "//" + ip + ":" + puerto + "/" + nomObjeto;
			
			fachada = (IFachada) Naming.lookup(ruta);
		} catch (NotBoundException | IOException e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un problema con la conexion del servidor.");
		}
	}

	public void AgregarFolio (String cod, String cara, int pag) throws PersistenciaException, RemoteException, FolioException{
		try{
			VOFolio vo = new VOFolio(cod, cara, pag);
			this.fachada.AgregarFolio(vo);
			
			// Despliego mensaje de confirmacion en pantalla
			JOptionPane.showMessageDialog(null, "Folio agregado correctamente.");
		}catch (PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}catch(RemoteException e) {
			throw new RemoteException(e.getMessage());
		} catch (FolioException e) {
			throw new FolioException(e.getMensaje());
		}
	}

	public void BorrarFolio (String cod) throws RevisionException, PersistenciaException, RemoteException{
		try{
			this.fachada.BorrarFolioRevisiones(cod);
			
			// Despliego mensaje de confirmacion en pantalla
			JOptionPane.showMessageDialog(null, "El folio con sus revisiones fue eliminado correctamente.");
			
		}catch (PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}catch(RemoteException e) {
			throw new RemoteException(e.getMessage());
		} catch (RevisionException e) {
			throw new RevisionException(e.getMensaje());
		}
	}
		
	public VOFolioMaxRev FolioMasRevisado() throws PersistenciaException, RemoteException, FolioException{
		VOFolioMaxRev vo = null;
		try{
			vo = this.fachada.FolioMasRevisado();
			
		}catch (PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}catch(RemoteException e) {
			throw new RemoteException(e.getMessage());
		} catch (FolioException e) {
			throw new FolioException(e.getMensaje());
		}
		
		return vo;
	}
	
	public ArrayList<VOFolio> ListarFolios() throws PersistenciaException, RemoteException, FolioException{
		ArrayList<VOFolio> lista = null;
		try{
			lista = this.fachada.ListarFolios();
			
		}catch (PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}catch(RemoteException e) {
			throw new RemoteException(e.getMessage());
		} catch (FolioException e) {
			throw new FolioException(e.getMensaje());
		}
		return lista;
	}
}

