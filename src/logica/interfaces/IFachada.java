package logica.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import logica.valueObjects.VORevision;


public interface IFachada extends Remote{
	
	public void AgregarFolio(VOFolio vof) throws RemoteException, PersistenciaException, FolioException, SQLException;
	
	public void AgregarRevision(String codF, String desc) throws RemoteException, PersistenciaException, RevisionException, SQLException;
	
	public void BorrarFolioRevisiones(String cod) throws RemoteException, PersistenciaException, RevisionException, SQLException;
	
	public String DarDescripcion(String codF, int numR) throws RemoteException, PersistenciaException, FolioException, RevisionException;
	
	public ArrayList<VOFolio> ListarFolios() throws RemoteException, PersistenciaException, FolioException, SQLException;
	
	public ArrayList<VORevision> ListarRevisiones(String codF) throws RemoteException, PersistenciaException, FolioException, SQLException;
	
	public VOFolioMaxRev FolioMasRevisado() throws RemoteException, PersistenciaException, FolioException, SQLException;

}
