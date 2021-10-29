package logica.poolConexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.locks.Condition;

import logica.interfaces.IConexion;
import logica.interfaces.IFachada;
import logica.interfaces.IPoolConexiones;

public class PoolConexiones implements IPoolConexiones {
	
	private String driver;
	private String url;
	private String user;
	private String password;
	private int nivelTransaccionalidad;
	private int tam = 5;
	private int creadas;
	private int tope;
	private Conexion Conexiones [];
	private Cola cola; 
	
	//Metodo constructor
	public PoolConexiones()
	{
		Conexiones = new Conexion[tam];
		tope = 0;
		creadas = 0;
		nivelTransaccionalidad = 0;
		cola = Cola.getInstancia(); //Instanciamos la cola de esperar para las solicitudes que deben esperar por una conexion
		
		try {
			Properties p = new Properties();
			String nomArch = "src/Config/Config.properties";
			p.load (new FileInputStream (nomArch));
			driver = p.getProperty("driver");
			url = p.getProperty("url");
			user = p.getProperty("usuario");
			password = p.getProperty("password");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	//Solicita una conexión al pool. En caso de que todas estén actualmente en
	//uso, bloqueará al usuario hasta que otro usuario libere alguna.
	public IConexion obtenerConexion (boolean modifica) throws RemoteException
	{
		Conexion conect = null;
		if(creadas < tam)
		{
			try
			{		
				Class.forName(driver);
				
				Connection con = DriverManager.getConnection(url, user, password);	// Creamos una conexion
				
				if(modifica)	//Verificamos si la coneccione solicitada es para modificar datos en la base
					nivelTransaccionalidad = 5;
				else
					nivelTransaccionalidad = 4;
				
				switch(nivelTransaccionalidad) //Seteamos el nivel de transaccionaldiad
				{
					case 1:
						con.setTransactionIsolation(Connection.TRANSACTION_NONE);
						break;
					case 2:
						con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
						break;
					case 3:
						con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
						break;
					case 4:
						con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
						break;
					case 5:
						con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
						break;
					default:
						con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);	
				}

				con.setAutoCommit(false); //Seteamos AutoCommit en False para que nosotros controlemos el commit o el rollback
					
				conect = new Conexion(con);
				
				creadas++; //Se aumenta el contador de conexiones creadas
				
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) 
			{
				e.printStackTrace();
			}
		}
		else if ((tope >= 1) && (tope < tam)) //Caso donde tenemos conexiones diponibles aunque todas ya fueron creadas
		{
				conect = Conexiones[tope];
				tope--;
				cola.quitar();
		}
		else	//Caso donde no quedan conexiones por crear o disponibles
		{
			cola.quitar(); //Se envia la solicitud a la cola de espera
			conect =  Conexiones[tope];//Cuando sale de la cola de espera es porque se agrego una conexion al arreglo con tope
			tope--; //Se quita una conexion del arreglo
			cola.quitar();//Se quita un lugar de la cola de espera
		}
		
		return conect;
	}
	
	//Devuelve una conexión al pool y avisa a posibles usuarios bloqueados. Si
	//ok vale true, hará commit al devolverla, sino hará rollback.
	public void liberarConexion (IConexion con, boolean ok) throws SQLException
	{
		if(ok)
			((Conexion) con).getConnection().commit(); //Si la transaccion quedo correctamente realizada se hace commit
		else
			((Conexion) con).getConnection().rollback(); //En caso de no quedar correctamente realizaada se hace rollback
		
		Conexiones[tope] = (Conexion) con; //Se devuelve la conexion al arreglo de conexiones
		tope++; //Se aumenta el tope
		cola.insertar(1); //Se notifica a la cola que hay un lugar disponible
	}

}
