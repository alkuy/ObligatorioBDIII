package persistencia.daos;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.poolConexiones.Conexion;
import logica.valueObjects.VORevision;
import persistencia.consultas.ConsultasRevision;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAORevisiones implements IDAORevisiones, Serializable{
	
	// Codigo autogenerado
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String codFolio;
	private Revision revision = null;
	private ConsultasRevision CR;
	
	// Constructor
	public DAORevisiones(String codF){
		codFolio = codF;
	}
	
	
	// Metodo para insertar una revision al final de la secuencia de revisiones
	public void insback (IConexion iCon, Revision rev) throws PersistenciaException {		
		try {
			CR = new ConsultasRevision();
			String insert = CR.insertarRevision();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(insert);
			
			pstmt.setInt(1, rev.getNumero());
			pstmt.setString(2, codFolio);
			pstmt.setString(3, rev.getDescripcion());
			
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e){
			throw new PersistenciaException("Ocurrio un error al insertar la revision.");
		}
	}
	
	// Metodo para obtener el largo de la secuencia de revisiones
	public int largo (IConexion iCon) throws PersistenciaException{
		int cant = 0;
	
		try {
			CR = new ConsultasRevision();
			String select = CR.ListarRevisiones();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setString(1, codFolio);
			
			ResultSet rs;
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				cant++;
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al obtener la cantidad de revisiones.");
		}
		return cant;
	}
	
	// Metodo para obtener el k-esimo elemento de la secuencia de revisiones
	public Revision kesimo (IConexion iCon, int Numero) throws PersistenciaException {
		String desc = null;
		
		try {
			CR = new ConsultasRevision();
			String select = CR.DarDescripcion();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setInt(1, Numero);
			pstmt.setString(2, codFolio);
			
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				desc = rs.getString("descripcion");
			}
			
			// Armo el objeto revision con la descripcion obtenida, el numero por parametro y el codigo de folio de la clase
			if(desc != null)
				revision = new Revision(Numero, desc);
			
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al obtener la revision.");
		}
	
		return revision;
	}
	
	// Metodo para obtener una lista con todas las revisiones
	// Precondicion: Hay al menos 1 revision en el folio
	public ArrayList<VORevision> listarRevisiones (IConexion iCon) throws PersistenciaException { 
		ArrayList<VORevision> lista = new ArrayList<VORevision>();

		try {
			CR = new ConsultasRevision();
			String select = CR.ListarRevisiones();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setString(1, codFolio);
			
			ResultSet rs = pstmt.executeQuery();
	
			while (rs.next()) {
				if(codFolio.equals(rs.getString("codigoFolio"))) {
					VORevision vo = new VORevision(rs.getInt("numero"), rs.getString("descripcion"), codFolio);
					lista.add(vo);
				}
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e)
		{
			throw new PersistenciaException("Ocurrio un error al obtener las revisiones.");
		}
		return lista;
	}
	
	// Metodo para borrar todas las revisiones de la secuencia
	public void borrarRevisiones (IConexion iCon) throws PersistenciaException {
		try {
			CR = new ConsultasRevision();
			String delete = CR.EliminarRevisionesFolio();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(delete);
			
			pstmt.setString(1, codFolio);

			pstmt.close();
		}catch(SQLException e){
			throw new PersistenciaException("Ocurrio un error al borrar las revisiones del folio.");
		}
	}
}
