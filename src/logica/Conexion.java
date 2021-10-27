package logica;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;

import logica.interfaces.IConexion;

public class Conexion extends UnicastRemoteObject implements IConexion{


	private static final long serialVersionUID = 1L;
	private Connection con;
	
	public Conexion(Connection c) throws RemoteException {
		con = c;
	}
	
	public Connection getConnection()
	{
		return con;
	}
}
