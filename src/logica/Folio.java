package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import logica.excepciones.PersistenciaException;
import logica.interfaces.FabricaAbstracta;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.valueObjects.VORevision;

public class Folio implements Serializable  {
	
	// Codigo autogenerado
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String codigo;
	private String caratula;
	private int paginas;
	private IDAORevisiones revisiones;
	private FabricaAbstracta fabrica;
	
	// Constructor
	public Folio (String cod, String car, int pag){
		codigo = cod;
		caratula = car;
		paginas = pag;
		try
		{
			Properties p = new Properties();
			
			String nomArch = "src/Config/Config.properties";
			p.load (new FileInputStream (nomArch));			
			String nomFab = p.getProperty("fabrica");
			fabrica = (FabricaAbstracta) Class.forName(nomFab).newInstance();
			revisiones = fabrica.crearIDAORevision(cod);		
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
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		}
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
		boolean existe = false;
		
		
		if (revisiones.largo(iCon) > 0)
		{
			Revision rev = revisiones.kesimo(iCon, numR);
			if (rev != null)
				existe = true;
		}
			
		
		return existe;
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
	
}
