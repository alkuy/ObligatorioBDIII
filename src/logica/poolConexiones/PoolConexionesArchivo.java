package logica.poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.Condition;

import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IPoolConexiones;
import logica.valueObjects.*;

import java.util.concurrent.locks.Condition;

public class PoolConexionesArchivo implements IPoolConexiones, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MonitorPool monitor;
	private boolean bandera = false;
	
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
		}
		else {
			monitor.terminoLectura();
		}	
	}

	
}
