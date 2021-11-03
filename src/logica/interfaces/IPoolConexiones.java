package logica.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

public interface IPoolConexiones extends Remote
{

	//Solicita una conexión (abstracta) al pool. El parámetro modifica
	//indica si la conexión que se va a pedir es para realizar una
	//transacción con posibles sentencias de modificación o no.
	public IConexion obtenerConexion (boolean modifica) throws RemoteException;
	
	//Devuelve una conexión al pool indicando si la transacción fue
	//exitosa o no. Si ok vale true, significa que lo fue, si vale false,
	//significa que no lo fue.
	public void liberarConexion (IConexion con, boolean ok) throws RemoteException, SQLException;
}
