package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;

public class Fachada extends UnicastRemoteObject implements IFachada{
	
	private static final long serialVersionUID = 1L;
	
	// Atributos
	//private static Fachada fachada;
	private Connection con;
	private DAOFolio daoF = new DAOFolio();
	private static Fachada fachada;
	
	//Constructor de la clase
	public Fachada() throws RemoteException
	{
		try {
			Properties p = new Properties();
					
			String nomArch = "src/Config/Config.properties";
			p.load (new FileInputStream (nomArch));
			
			String driver = p.getProperty("driver");
			String url = p.getProperty("url");
			String usuario = p.getProperty("usuario");
			String password = p.getProperty("password");
			Class.forName(driver);
						
			// Creo la conexion
			con = DriverManager.getConnection(url, usuario, password);			
		}catch(RemoteException e){
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	public void AgregarFolio(VOFolio voF) throws RemoteException, PersistenciaException, FolioException{
		try {
			String codF = voF.getCodigo();
			// Chequeamos que no exista el folio
			boolean existe = daoF.member(con, codF);
			
			// Si no existe lo insertamos
			if (existe == false) {
				abd.insertarFolio(con, voF);
			}else {
				throw new FolioException("Ya existe un folio con ese codigo.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// agrega una nueva revisión a un folio del sistema, chequeando que el folio que le corresponde esté registrado
	public void AgregarRevision(String codF, String desc) throws RemoteException, PersistenciaException, RevisionException{
		try {
			// Chequeamos que exista el folio
			boolean existe = abd.ExisteFolio(con, codF);
			
			// Si existe el folio, se le agrega la revision
			if (existe == true) {
				int num = abd.NumeroUltimaRevision(con, codF);
				VORevision voR = new VORevision(num, codF, desc);
				abd.insertarRevision(con, voR);
			}else {
				throw new RevisionException("No existe un folio con ese codigo.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// elimina del sistema al folio con el código ingresado, y también elimina todas sus revisiones, chequeando que el folio con ese código esté registrado
	public void BorrarFolioRevisiones(String codF) throws RemoteException, PersistenciaException, RevisionException{
		try {
			// Chequeamos que exista el folio
			boolean existe = abd.ExisteFolio(con, codF);
			// Si existe el folio, se le eliminan las revisiones y el folio
			if (existe == true) {
				abd.EliminarFolioCascada(con, codF);
			}else {
				throw new RevisionException("No existe un folio con ese codigo.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
	}
	
	// devuelve la descripción de una revisión, dados su número y el código	del folio que le corresponde (chequeando que el folio exista y tenga una revisión con ese número).
	public String DarDescripcion(String codF, int numR) throws RemoteException, PersistenciaException, FolioException, RevisionException{
		boolean existe = false;
		boolean existeRev = false;
		String desc = null;
		try {
			existe = abd.ExisteFolio(con, codF);
			if (existe) {
				existeRev = abd.ExisteRevisionFolio(con, codF, numR);
				if (existeRev) {
					// Si existe el folio, y existe esa revision, la obtenemos
					desc = abd.DarDescripcion(con, codF, numR);
				}else {
					throw new RevisionException("No existe una revision con ese numero en el folio.");
				}
			}else {
				throw new FolioException("No existe un folio con ese codigo.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return desc;
	}
	
	// devuelve un listado de todos los folios registrados, ordenado por código
	public ArrayList<VOFolio> ListarFolios() throws RemoteException, PersistenciaException, FolioException{
		ArrayList<VOFolio> lista = new ArrayList<VOFolio>();
		boolean existeFolio = false;
		
		try {
			existeFolio = abd.ExisteAlgunFolio(con);
			if (existeFolio) {
				lista = abd.listarFolios(con);
			}else {
				throw new FolioException("No existe ningun folio para listar.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return lista;
	}
	
	// Devuelve un listado de todas las revisiones de un folio dado, (chequeando que dicho folio esté registrado) ordenado por número de revisión. 
	public ArrayList<VORevision> ListarRevisiones(String codF) throws RemoteException, PersistenciaException, FolioException{
		ArrayList<VORevision> lista = new ArrayList<VORevision>();
		boolean existe = false;
		
		try {
			// Chequeamos la existencia del folio
			existe = abd.ExisteFolio(con, codF);
			if (existe) {
				lista = abd.listarRevisiones(con, codF);
			}else {
				throw new FolioException("No existe un folio con ese codigo.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		
		return lista;
	}
	
	// devuelve los datos del folio con la mayor cantidad de revisiones, junto con la cantidad de revisiones correspondiente (chequeando que exista al menos un folio).
	public VOFolioMaxRev FolioMasRevisado() throws RemoteException, PersistenciaException, FolioException{
		VOFolioMaxRev voF = null;
		boolean existeFolio = false;
		
		try {
			existeFolio = abd.ExisteAlgunFolio(con);
			if (existeFolio) {
				voF = abd.FolioMasRevisado(con);
			}else {
				throw new FolioException("No existe ningun folio para listar.");
			}
		} catch (SQLException e) {
			throw new PersistenciaException("Ocurrio un error al acceder a la base de datos.");
		}
		return voF;
	}

}
