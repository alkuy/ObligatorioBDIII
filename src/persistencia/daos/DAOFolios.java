package persistencia.daos;
import logica.Folio;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAOFolios;
import logica.poolConexiones.Conexion;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import persistencia.consultas.ConsultasFolio;
import persistencia.consultas.ConsultasRevision;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAOFolios implements IDAOFolios{

	// Atributos
	private ConsultasFolio CF;
	
	// Constructor
	public DAOFolios() {
			
	}

	// Metodo que permite recibir codigo de folio y verificar si existe en la secuencia
	public boolean member (IConexion iCon, String codF)  throws PersistenciaException {
		boolean resu = false;
		try {
			CF = new ConsultasFolio();
			String select = CF.ExisteFolio();
			
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setString(1, codF);
			
			ResultSet rs = pstmt.executeQuery();
			
			
			while (rs.next()) 
			{
				
				if(codF.equals(rs.getString("codigo"))) 
				{
					resu=true;
				}
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return resu;
	}
		
	// Metodo para insertar un folio al final de la secuencia de folios
	public void insert (IConexion iCon, Folio fo) throws PersistenciaException {		
		try {
			CF = new ConsultasFolio();
			String insert = CF.InsertarFolio();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(insert);
			pstmt.setString(1, fo.getCodigo());
			pstmt.setString(2, fo.getCaratula());
			pstmt.setInt(3, fo.getPaginas());
			
			pstmt.executeUpdate ();
			pstmt.close();
		}catch (SQLException e){
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
		
	//Metodo para encontrar y devolver un folio 
	public Folio find (IConexion iCon, String codF) throws PersistenciaException {		
		Folio fol = null;
		try {
			CF = new ConsultasFolio();
			String select = CF.ExisteFolio();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			pstmt.setString(1, codF);
			
			ResultSet rs = pstmt.executeQuery();
 				while (rs.next()) 
			{
				if(codF.equals(rs.getString("codigo"))) 
				{
					 fol = new Folio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
				}
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e){
			
		}
		return fol;
	}
	
	// Metodo para eliminar un folio
	public void delete (IConexion iCon, String codF) throws PersistenciaException {
		ConsultasRevision CR;
		try {
			CF = new ConsultasFolio();
			CR = new ConsultasRevision();
			
			String deleteR = CR.EliminarRevisionesFolio();
			
			String deleteF = CF.EliminarFolio();
			PreparedStatement pstmt1 = ((Conexion) iCon).getConnection().prepareStatement(deleteR);
			PreparedStatement pstmt2 = ((Conexion) iCon).getConnection().prepareStatement(deleteF);
			pstmt1.setString(1, codF);
			pstmt2.setString(1, codF);
			pstmt1.executeUpdate ();
			pstmt2.executeUpdate ();
			pstmt1.close();
			pstmt2.close();
		}
		catch (SQLException e)
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// Metodo para listar todos los folios almacenados
	// Precondicion no debe estar vaia la tabla de folios
	public ArrayList<VOFolio> listarFolios (IConexion iCon) {
		ArrayList<VOFolio> arre = new ArrayList<VOFolio>();
		int index=0;
		try {
			
			CF = new ConsultasFolio();
			String select = CF.ListarFolios();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
			
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
					 VOFolio vo= new VOFolio(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"));
					 arre.add(index, vo);
					 index++;
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e){
			
		}
		
		return arre;
	}
	
	//Metodo que devuelve si hay folios o si esta vacio
	public boolean esVacio (IConexion iCon)  throws PersistenciaException {
		boolean vacia = true;
		
		try {
			CF = new ConsultasFolio();
			String select = CF.ListarFolios();
			Statement stmt = ((Conexion) iCon).getConnection().createStatement();
			
			ResultSet rs = stmt.executeQuery(select);
				if (rs.next()) 
			{
				vacia = false;
			}
	
			rs.close();
			stmt.close();
		}catch(SQLException e){
			
		}
		return vacia;
	}
		
	// Metodo para devolver folio mas revisado
	public VOFolioMaxRev folioMasRevisado (IConexion iCon) {
		VOFolioMaxRev FmaxRev = null;
		try {
			CF = new ConsultasFolio();
			String select = CF.FolioMasRevisado();
			PreparedStatement pstmt = ((Conexion) iCon).getConnection().prepareStatement(select);
					
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) 
			{
				FmaxRev = new VOFolioMaxRev(rs.getString("codigo"), rs.getString("caratula"), rs.getInt("paginas"), rs.getInt("Cantidad"));
			}
	
			rs.close();
			pstmt.close();
		}catch(SQLException e){
			
		}
		return FmaxRev;
	}
}