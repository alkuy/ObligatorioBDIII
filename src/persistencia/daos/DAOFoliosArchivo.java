package persistencia.daos;

import java.io.File;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.Folio;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAOFolios;
import logica.poolConexiones.Conexion;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import persistencia.consultas.ConsultasFolio;
import logica.poolConexiones.*;

public class DAOFoliosArchivo implements IDAOFolios, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SavesAndLoads SaL = new SavesAndLoads();
	public DAOFoliosArchivo() {
		
	}
	public boolean member(IConexion iCon, String codF) throws PersistenciaException {
		boolean resu = false;
		String nomArch = "src/archivos/folios/"+"folio"+codF;
		File file = new File(nomArch);
		
		if(file.exists())
			resu = true;
		
		//System.out.println("member: "+nomArch+" "+resu);
		
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
		File file = new File("folio" + codF);
		if(file.exists())
			file.delete();
	}

	public ArrayList<VOFolio> listarFolios(IConexion iCon) throws PersistenciaException 
	{
		ArrayList<VOFolio> arre = new ArrayList<VOFolio>();
		int index = 0;
		//System.out.println("Listar Folios");
		try 
		{
			final File folder = new File("src/archivos/folios/");
			//System.out.println("Levanta Folder"+folder);
			for (final File fileEntry : folder.listFiles()) 
			{
				//System.out.println("FileEntry = "+fileEntry.toString());
				
				//System.out.println("Nomarch: "+fileEntry.toString());
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
				if (fileEntry.isDirectory()) 
				{
					Folio F = SaL.LoadFolios(fileEntry.toString());
					if(F.cantidadRevisiones(iCon)>mayor)
					{
						mayor = F.cantidadRevisiones(iCon);
						Max = new VOFolioMaxRev(F.getCodigo(), F.getCaratula(), F.getPaginas(), mayor);
					}
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
