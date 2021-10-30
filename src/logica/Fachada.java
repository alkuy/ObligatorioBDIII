package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.interfaces.IConexion;
import logica.interfaces.IFachada;
import logica.interfaces.IPoolConexiones;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VORevision;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;

public class Fachada extends UnicastRemoteObject implements IFachada
{
	
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private DAOFolios daoF = new DAOFolios();
	private IPoolConexiones iPool;
	private static Fachada fachada;
	
	//Constructor de la clase
	public Fachada() throws RemoteException
	{
		try {
			Properties p = new Properties();
					
			String nomArch = "src/Config/Config.properties";
			p.load (new FileInputStream (nomArch));
			
			String poolConcreto = p.getProperty("pool");
			iPool = (IPoolConexiones) Class.forName(poolConcreto).newInstance();
								
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	// Aplicamos el patron singleton, para tener siempre solo una unica instancia de la fachada
	public static Fachada getFachada() throws PersistenciaException, RemoteException{
		if (fachada == null) {
			fachada = new Fachada();
		}
		return fachada;
	}
	
	//Agrega un nuevo folio al sistema, chequeando que no existiera
	public void AgregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioException
	{
		try 
		{
			IConexion iCon = null;
			iCon = iPool.obtenerConexion(true);
			String codF = voF.getCodigo();
			// Chequeamos que no exista el folio
			boolean existe = daoF.member(iCon, codF);
			
			// Si no existe lo insertamos
			if (existe == false) 
			{
				Folio Fol = new Folio(voF.getCodigo(), voF.getCaratula(), voF.getPaginas());
				daoF.insert(iCon, Fol);
				iPool.liberarConexion(iCon, true);
			}
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new FolioException("Ya existe un folio con ese codigo.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// agrega una nueva revisión a un folio del sistema, chequeando que el folio que le corresponde esté registrado
	public void AgregarRevision(String codF, String desc) throws RemoteException, PersistenciaException, RevisionException{
		IConexion iCon = null;
		try 
		{
			iCon = iPool.obtenerConexion(true);
			
			// Chequeamos que exista el folio
			Folio Fol = daoF.find(iCon, codF);
			
			// Si existe el folio, se le agrega la revision
			if (Fol.getCodigo() != null) 
			{
				int num = daoF.find(iCon, codF).NumeroUltimaRevision(iCon);
				Revision rev = new Revision(num, codF, desc);
				daoF.find(iCon, codF).addRevision(iCon,rev);
				iPool.liberarConexion(iCon, true);
			} 
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new RevisionException("No existe un folio con ese codigo.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// elimina del sistema al folio con el código ingresado, y también elimina todas sus revisiones, chequeando que el folio con ese código esté registrado
	public void BorrarFolioRevisiones(String codF) throws RemoteException, PersistenciaException, RevisionException{
		
		IConexion iCon = null;
		try 
		{
			iCon = iPool.obtenerConexion(true);
			
			// Chequeamos que exista el folio
			boolean existe = daoF.member(iCon, codF);
			
			// Si existe el folio, se le eliminan las revisiones y el folio
			if (existe == true) 
			{
				daoF.delete(iCon, codF);
				iPool.liberarConexion(iCon, true);
			}
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new RevisionException("No existe un folio con ese codigo.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// devuelve la descripción de una revisión, dados su número y el código	del folio que le corresponde (chequeando que el folio exista y tenga una revisión con ese número).
	public String DarDescripcion(String codF, int numR) throws RemoteException, PersistenciaException, FolioException, RevisionException{
		
		boolean existe = false;
		boolean existeRev = false;
		String desc = null;
		IConexion iCon = null;
		
		try {
			iCon = iPool.obtenerConexion(false);
			existe = daoF.member(iCon, codF);
			
			if (existe) 
			{
				existeRev = daoF.find(iCon, codF).tieneRevision(iCon, numR);
				if (existeRev) 
				{
					// Si existe el folio, y existe esa revision, la obtenemos
					desc = daoF.find(iCon, codF).obtenerRevision(iCon,numR).getDescripcion();
					iPool.liberarConexion(iCon, true);
				}
				else 
				{
					iPool.liberarConexion(iCon, false);
					throw new RevisionException("No existe una revision con ese numero en el folio.");
				}
			}else 
			{
				iPool.liberarConexion(iCon, false);
				throw new FolioException("No existe un folio con ese codigo.");
			}
		} 
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return desc;
	}
	
	// devuelve un listado de todos los folios registrados, ordenado por código
	public ArrayList<VOFolio> ListarFolios() throws RemoteException, PersistenciaException, FolioException
	{
		IConexion iCon = null;
		ArrayList<VOFolio> lista = new ArrayList<VOFolio>();
		boolean existeFolio = false;
		try 
			{
			iCon = iPool.obtenerConexion(false);
			existeFolio = daoF.esVacio(iCon);
			
			if (existeFolio) 
			{
				lista = daoF.listarFolios(iCon);
				iPool.liberarConexion(iCon, true);
			}
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new FolioException("No existe ningun folio para listar.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return lista;
	}
	
	// Devuelve un listado de todas las revisiones de un folio dado, (chequeando que dicho folio esté registrado) ordenado por número de revisión. 
	public ArrayList<VORevision> ListarRevisiones(String codF) throws RemoteException, PersistenciaException, FolioException
	{
		ArrayList<VORevision> lista = new ArrayList<VORevision>();
		boolean existe = false;
		IConexion iCon = null;
		try 
		{
			iCon = iPool.obtenerConexion(false);
			
			// Chequeamos la existencia del folio
			existe = daoF.member(iCon, codF);
			
			if (existe) 
			{
				DAORevisiones daoR = new DAORevisiones(codF);
				lista = daoR.listarRevisiones(iCon);
				iPool.liberarConexion(iCon, true);
			}
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new FolioException("No existe un folio con ese codigo.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return lista;
	}
	
	// devuelve los datos del folio con la mayor cantidad de revisiones, junto con la cantidad de revisiones correspondiente (chequeando que exista al menos un folio).
	public VOFolioMaxRev FolioMasRevisado() throws RemoteException, PersistenciaException, FolioException
	{
		VOFolioMaxRev voF = null;
		IConexion iCon = null;
		
		boolean noExisteFolio = true;
		try 
		{
			iCon = iPool.obtenerConexion(false);
			noExisteFolio = daoF.esVacio(iCon);
			
			if (!noExisteFolio) 
			{
				voF = daoF.folioMasRevisado(iCon);
				iPool.liberarConexion(iCon, true);
			}
			else 
			{
				iPool.liberarConexion(iCon, false);
				throw new FolioException("No existe ningun folio para listar.");
			}
		}
		catch (SQLException e) 
		{
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		return voF;
	}

}
