package logica.interfaces;


import java.rmi.Remote;
import java.util.ArrayList;

import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VORevision;


public interface IDAORevisiones extends Remote {
	
	public boolean ExisteRevisionFolio (IConexion iCon, String codF, int numR)  throws PersistenciaException;
	
	public void insback (IConexion iCon, Revision rev) throws PersistenciaException;
	
	public int largo (IConexion iCon) throws PersistenciaException;
	
	public Revision kesimo (IConexion iCon, int Numero) throws PersistenciaException;

	public ArrayList<VORevision> listarRevisiones (IConexion iCon) throws PersistenciaException;	
	
	public void borrarRevisiones (IConexion iCon) throws PersistenciaException;
}
