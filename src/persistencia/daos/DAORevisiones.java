package persistencia.daos;
import logica.Revision;
import persistencia.consultas.ConsultasRevision;

import java.sql.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DAORevisiones {

	// Atributos
	private String codFolio;
	private static Connection con;
	private Revision revision;
	private ConsultasRevision CR;
	
	public DAORevisiones(String codF)
	{
		codFolio = codF;
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
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// Metodo que permite recibir un numero de revision y verificar si existe en la secuencia
	public boolean member (String codF, int numR)  throws SQLException 
	{
		
		boolean resu = false;
		
		CR = new ConsultasRevision();
		String select = CR.DarDescripcion();
		PreparedStatement pstmt = con.prepareStatement(select);
		
		pstmt.setInt(1, numR);
		pstmt.setString(2, codF);
		
		ResultSet rs = pstmt.executeQuery();
		
		if (rs.next()) {
			resu = true;
		}

		rs.close();
		pstmt.close();
		return resu;
	}
	
	
}
