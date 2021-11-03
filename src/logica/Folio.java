package logica;

import java.sql.SQLException;
import java.util.ArrayList;

import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisiones;

public class Folio {
	
	// Atributos
	private String codigo;
	private String caratula;
	private int paginas;
	private IDAORevisiones revisiones;
	
	public Folio (String cod, String car, int pag){
		codigo = cod;
		caratula = car;
		paginas = pag;
		revisiones = new DAORevisiones(cod);
	}
	
	public String getCodigo() {
		return codigo;
	}

	public String getCaratula() {
		return caratula;
	}

	public int getPaginas() {
		return paginas;
	}
	
	public boolean tieneRevision(IConexion iCon, int numR) throws SQLException, PersistenciaException
	{
		return revisiones.ExisteRevisionFolio(iCon, codigo, numR);
	}
	
	public int cantidadRevisiones (IConexion iCon) throws PersistenciaException
	{
		return revisiones.largo(iCon);
	}
	
	public void addRevision (IConexion iCon, Revision rev) throws PersistenciaException
	{
		revisiones.insback(iCon, rev);
	}
	
	public Revision obtenerRevision (IConexion iCon, int numR) throws PersistenciaException
	{
		return revisiones.kesimo(iCon, numR);
	}
	
	public ArrayList<VORevision> listarRevision(IConexion iCon) throws PersistenciaException 
	{
		return revisiones.listarRevisiones(iCon);
	}
	
	public void borrarRevision (IConexion iCon) throws PersistenciaException
	{
		revisiones.borrarRevisiones(iCon);
	}
	
	public int NumeroUltimaRevision(IConexion iCon) throws PersistenciaException
	{
		return revisiones.largo(iCon);
	}
}
