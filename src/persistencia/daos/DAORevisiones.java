package persistencia.daos;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.poolConexiones.Conexion;
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

public class DAORevisiones {
	
	// Atributos
	private String codFolio;
	private Revision revision;
	private ConsultasRevision CR;
	
	public DAORevisiones(String codF){
		codFolio = codF;
	}
	
	// Metodo que permite recibir un numero de revision y verificar si existe en la secuencia
	public boolean ExisteRevisionFolio (IConexion iCon, String codF, int numR)  throws PersistenciaException {
		boolean resu = false;
		
		try {
			CR = new ConsultasRevision();
			String select = CR.DarDescripcion();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
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
	public void insback (IConexion iCon, Revision rev) throws PersistenciaException {		
		try {
			CR = new ConsultasRevision();
			String insert = CR.insertarRevision();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(insert);
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
