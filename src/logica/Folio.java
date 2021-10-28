package logica;

import java.sql.SQLException;

import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.valueObjects.VORevision;
import persistencia.daos.DAORevisiones;

public class Folio {
	
	// Atributos
	private String codigo;
	private String caratula;
	private int paginas;
	private DAORevisiones revisiones;
	
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
	
	public int cantidadRevisiones ()
	{
		return 0;
	}
	
	public void addRevision (Revision rev)
	{
		
	}
	
	public Revision obtenerRevision (int numR)
	{
		Revision rev;
		return rev = new Revision(1,"a","a");
	}
	
	public VORevision listarRevision() 
	{
		VORevision VOr = new VORevision(2, "a", "a");
		return VOr;
	}
	
	public void borrarRevision ()
	{
		
	}
}
