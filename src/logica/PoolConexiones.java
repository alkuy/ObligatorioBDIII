package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PoolConexiones {
	
	private String driver;
	private String url;
	private String user;
	private String password;
	private int nivelTransaccionalidad;
	private int tam = 5;
	private int creadas;
	private int tope;
	private Conexion Conexiones [];
	
	//Metodo constructor
	public PoolConexiones()
	{
		Conexiones = new Conexion[tam];
		tope = 0;
		creadas = 0;
		
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
	
	
	

}
