package persistencia.daos;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.poolConexiones.Conexion;
import logica.valueObjects.VORevision;
import persistencia.consultas.ConsultasRevision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			throw new PersistenciaException("Ocurrió un error al intentar verificar la existencia de la revisión.");
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
			
			pstmt.executeUpdate();
			pstmt.close();
		}catch (SQLException e){
			throw new PersistenciaException("Ocurrió un error al insertar la revisión.");
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
			throw new PersistenciaException("Ocurrió un error al insertar la revisión.");
			// e.printStackTrace();
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
			revision = new Revision(Numero, codFolio, desc);
			
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrió un error al obtener la revisión.");
			// e.printStackTrace();
		}
	
		return revision;
	}
	
	// Metodo para obtener una lista con todas las revisiones
	// Precondición: Hay al menos 1 revision en el folio
	public ArrayList<VORevision> listarRevisiones (IConexion iCon) throws PersistenciaException { 
		ArrayList<VORevision> lista = new ArrayList<VORevision>();

		try {
			CR = new ConsultasRevision();
			String select = CR.ListarRevisiones();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setString(1, codFolio);
			
			ResultSet rs = pstmt.executeQuery();
	
			while (rs.next()) {
				if(rs.getString("codigo") == codFolio) {
					VORevision vo = new VORevision(rs.getInt("numero"), rs.getString("descripcion"), codFolio);
					lista.add(vo);
				}
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e){
			throw new PersistenciaException("Ocurrió un error al obtener las revisiones.");
			// e.printStackTrace();
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
			throw new PersistenciaException("Ocurrió un error al borrar las revisiones del folio.");
			// e.printStackTrace();
		}
	}
}
