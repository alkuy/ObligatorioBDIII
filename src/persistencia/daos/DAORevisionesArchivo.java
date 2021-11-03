package persistencia.daos;

import java.util.ArrayList;

import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.valueObjects.VORevision;

public class DAORevisionesArchivo implements IDAORevisiones {

	@Override
	public boolean ExisteRevisionFolio(IConexion iCon, String codF, int numR) throws PersistenciaException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void insback(IConexion iCon, Revision rev) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int largo(IConexion iCon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Revision kesimo(IConexion iCon, int Numero) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VORevision> listarRevisiones(IConexion iCon) throws PersistenciaException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void borrarRevisiones(IConexion iCon) throws PersistenciaException {
		// TODO Auto-generated method stub
		
	}

}
