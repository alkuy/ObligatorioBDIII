package main;

import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class dbCreation {
	
	// Atributos
	private static Connection con;

	public static void main(String[] args) {
		
		try {
			Properties p = new Properties();
			
			String nomArch = "src/config/Config.properties";
			p.load (new FileInputStream (nomArch));

			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String usuario = p.getProperty("usuario");
			String password = p.getProperty("password");
			Class.forName(driver);
			
			// Creamos la conexion
			con = DriverManager.getConnection(url, usuario, password);
			
			// Creamos las distintas sentencias para crear la bd, usarla, y crear 3 tablas
			String create = "create database foliosRev;";
			String use = "use foliosRev;";
			String table1 = "create table Folios(codigo VARCHAR(60) primary key, caratula VARCHAR(60), paginas INT);";
			String table2 = "create table Revisiones(numero int, codigoFolio VARCHAR(60), FOREIGN KEY (codigoFolio) REFERENCES Folios(codigo), descripcion VARCHAR(60), primary key (numero, codigoFolio));";
					
			// Creamos statement para la creacion de la BD y tablas
			Statement stmt = con.createStatement();

			// Ejecutamos las sentencias anteriores y cerramos tanto el Statment como la conexion
			int cant1 = stmt.executeUpdate(create);
			int cant2 = stmt.executeUpdate(use);
			int cant3 = stmt.executeUpdate(table1);
			int cant4 = stmt.executeUpdate(table2);
			
			// Creamos las distintas sentencias para insertar los datos
			String data1 = "insert into Folios values ('FGH-0015', 'La comuna contra la señora con 38 gatos', 5);";
			String data2 = "insert into Folios values ('BBD-1278', 'Adolescentes descontrolados hasta las 5 AM', 2);";
			String data3 = "insert into Folios values ('JJ-202', 'Vecinos reclaman por heces de perro en el hall', 9);";
			String data4 = "insert into Folios values ('CEFJ-63', 'Vecinas rivales se tiran macetas con frecuencia', 463);";

			stmt.executeUpdate(data1);
			stmt.executeUpdate(data2);
			stmt.executeUpdate(data3);
			stmt.executeUpdate(data4);
			
			stmt.close();
			
			con.close();
			
			System.out.println("La base de datos ha sido creada exitosamente");
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
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
