package logica.poolConexiones;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;

import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IPoolConexiones;
import logica.valueObjects.*;

import java.util.concurrent.locks.Condition;

public class PoolConexionesArchivo implements IPoolConexiones{
	private MonitorPool monitor = new MonitorPool();

	private boolean bandera = false;
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
