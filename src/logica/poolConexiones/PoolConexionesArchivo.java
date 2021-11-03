package logica.poolConexiones;

import java.rmi.RemoteException;
import java.sql.SQLException;

import logica.interfaces.IConexion;
import logica.interfaces.IPoolConexiones;

public class PoolConexionesArchivo implements IPoolConexiones{

	@Override
	public IConexion obtenerConexion(boolean modifica) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void liberarConexion(IConexion con, boolean ok) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
