package logica.poolConexiones;

import java.io.Serializable;

import logica.interfaces.FabricaAbstracta;
import logica.interfaces.IDAOFolios;
import logica.interfaces.IDAORevisiones;
import logica.interfaces.IPoolConexiones;
import persistencia.daos.DAOFoliosArchivo;
import persistencia.daos.DAORevisionesArchivo;

public class FabricaArchivo implements FabricaAbstracta, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IDAOFolios crearIDAOFolios() {
		// TODO Auto-generated method stub
		return new DAOFoliosArchivo();
	}

	@Override
	public IDAORevisiones crearIDAORevision(String codF) {
		// TODO Auto-generated method stub
		return new DAORevisionesArchivo(codF);
	}
	
	public IPoolConexiones crearIPoolConexiones() {
		return new PoolConexionesArchivo();
	}

}
