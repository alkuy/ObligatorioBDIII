package logica.interfaces;


import java.rmi.Remote;
import java.util.ArrayList;

import logica.Folio;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;


public interface IDAOFolios extends Remote
{
	public boolean member (IConexion iCon, String codF)  throws PersistenciaException;
	
	public void insert (IConexion iCon, Folio fo) throws PersistenciaException;
	 
	public Folio find (IConexion iCon, String codF) throws PersistenciaException;
	
	public void delete (IConexion iCon, String codF) throws PersistenciaException;
	
	public ArrayList<VOFolio> listarFolios (IConexion iCon);
	
	public boolean esVacio (IConexion iCon)  throws PersistenciaException;

	public VOFolioMaxRev folioMasRevisado (IConexion iCon);
	
}
