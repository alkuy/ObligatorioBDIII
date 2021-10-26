package persistencia.daos;
import logica.Revision;
import logica.valueObjects.VORevision;
import persistencia.consultas.ConsultasRevision;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import Excepciones.PersistenciaException;

public class DAORevisiones {
	
	// Atributos
	private String codFolio;
	private static Connection con;
	private Revision revision;
	private ConsultasRevision CR;
	
	public DAORevisiones(String codF){
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
	public boolean member (String codF, int numR)  throws PersistenciaException {
		boolean resu = false;
		
		try {
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
		}catch(SQLException e){
			
		}
		return resu;
	}
	
	// Metodo para insertar una revision al final de la secuencia de revisiones
	public void insback (Revision rev) throws PersistenciaException {		
		try {
			CR = new ConsultasRevision();
			String insert = CR.insertarRevision();
			PreparedStatement pstmt = con.prepareStatement(insert);
			pstmt.setInt(1, rev.getNumero());
			pstmt.setString(2, rev.getCodigoFolio());
			pstmt.setString(3, rev.getDescripcion());
			pstmt.executeUpdate ();
			pstmt.close();
		}catch (SQLException e){
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// Metodo para obtener el largo de la secuencia de revisiones
	public int largo () {
		return 0;
	}
	
	// Metodo para obtener el k-esimo elemento de la secuencia de revisiones
	public Revision kesimo () {
		Revision revi = null;
		return revi;
	}
	
	// Metodo para obtener una lista con todas las revisiones
	//public ArrayList<VORevision> listarRevisiones () { }
	
	// Metodo para borrar todas las revisiones de la secuencia
	public void borrarRevisiones () {
		
	}
}
