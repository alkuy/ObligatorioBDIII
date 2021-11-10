package persistencia.daos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import logica.Folio;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAOFolios;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.poolConexiones.*;

public class DAOFoliosArchivo implements IDAOFolios, Serializable{

	// Codigo autogenerado
	private static final long serialVersionUID = 1L;
	
	// Atributos
	SavesAndLoads SaL = new SavesAndLoads();
	
	// Constructor
	public DAOFoliosArchivo() {
		
	}
	
	// Metodos
	public boolean member(IConexion iCon, String codF) throws PersistenciaException {
		boolean resu = false;
		String nomArch = "src/archivos/folios/"+"folio"+codF;
		File file = new File(nomArch);
		
		if(file.exists())
			resu = true;
		
		return resu;
	}

	public void insert(IConexion iCon, Folio fo) throws PersistenciaException 
	{	
		try 
		{
			SaL.SaveFolio(fo.getCodigo(), fo);
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
	}

	public Folio find(IConexion iCon, String codF) throws PersistenciaException 
	{
		Folio f = null;
		try 
		{
			String nomArch = "folio" + codF;			
			f = SaL.LoadFolios("src/archivos/folios/"+nomArch);
			
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
		return f;
	}

	public void delete(IConexion iCon, String codF) throws PersistenciaException 
	{
		File file = new File("src/archivos/folios/folio"+ codF);
		if(file.exists())
		{
			find(iCon, codF).borrarRevision(iCon);
			file.delete();
		}
			
	}

	public ArrayList<VOFolio> listarFolios(IConexion iCon) throws PersistenciaException 
	{
		ArrayList<VOFolio> arre = new ArrayList<VOFolio>();
		int index = 0;
		try 
		{
			final File folder = new File("src/archivos/folios/");
			for (final File fileEntry : folder.listFiles()) 
			{
				Folio F = SaL.LoadFolios(fileEntry.toString());
				VOFolio Folio = new VOFolio(F.getCodigo(), F.getCaratula(), F.getPaginas());
				arre.add(index, Folio);				
				
				index++;
			}
			return arre;
		}catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
	}

	public boolean esVacio(IConexion iCon) throws PersistenciaException 
	{
		boolean resu = false;

		String sCarpAct = "src/archivos/folios/";
		File carpeta = new File(sCarpAct);
		File [] archivos = carpeta.listFiles();
		if (archivos == null || archivos.length == 0) 
		    resu = true;
		
		return resu;
	}

	public VOFolioMaxRev folioMasRevisado(IConexion iCon) throws PersistenciaException 
	{
		int mayor = 0;
		VOFolioMaxRev Max = null;
		try 
		{
			final File folder = new File("src/archivos/folios/");
			for (final File fileEntry : folder.listFiles()) 
			{
				Folio F = SaL.LoadFolios(fileEntry.toString());
				if(F.cantidadRevisiones(iCon)>mayor)
				{
					mayor = F.cantidadRevisiones(iCon);
					Max = new VOFolioMaxRev(F.getCodigo(), F.getCaratula(), F.getPaginas(), mayor);
				}
				
			}
			return Max;	
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
	}

}
