package presentacion.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import logica.excepciones.PersistenciaException;
import logica.interfaces.IFachada;
import presentacion.ventanas.VentanaMenuPrincipal;

public class ControladorVentanaMenuPpal {
		// Tengo como atributo remoto a la fachada y como atributo local a mi ventana
		private IFachada fachada;
		//@SuppressWarnings("unused") // Codigo automatico para eliminar la warning de esta linea
		private VentanaMenuPrincipal vent;
		
		public ControladorVentanaMenuPpal (VentanaMenuPrincipal v) throws PersistenciaException {
			// Aquí me guardo la referencia a mi ventana y además hago el lookup para acceder remotamente a la fachada
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
}
