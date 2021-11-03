package persistencia.daos;

import java.util.ArrayList;

import logica.Folio;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAOFolios;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;

public class DAOFoliosArchivo implements IDAOFolios{

	@Override
	public boolean member(IConexion iCon, String codF) throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insert(IConexion iCon, Folio fo) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Folio find(IConexion iCon, String codF) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(IConexion iCon, String codF) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<VOFolio> listarFolios(IConexion iCon) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean esVacio(IConexion iCon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VOFolioMaxRev folioMasRevisado(IConexion iCon) {
		// TODO Auto-generated method stub
		return null;
	}

}
