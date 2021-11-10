package logica.poolConexiones;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.SQLException;
import logica.interfaces.IConexion;
import logica.interfaces.IPoolConexiones;

public class PoolConexionesArchivo implements IPoolConexiones, Serializable{

	// Codigo autogenerado
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private MonitorPool monitor;
	private boolean bandera = false;
	
	// Constructor
	public PoolConexionesArchivo(){
		monitor = MonitorPool.getInstancia(); //Instanciamos la cola de esperar para las solicitudes que deben esperar por una conexion
	}
	
	public IConexion obtenerConexion(boolean modifica) throws RemoteException {
		Conexion conect = null;
		if (modifica) {
			monitor.comienzoEscritura();
			bandera = true;
		}
		else
			monitor.comienzoLectura();
		return conect;
	}

	@Override
	public void liberarConexion(IConexion con, boolean ok) throws RemoteException, SQLException {
		if(bandera) {
			monitor.terminoEscritura();
			bandera = false;
		}
		else {
			monitor.terminoLectura();
		}	
	}

	
}
