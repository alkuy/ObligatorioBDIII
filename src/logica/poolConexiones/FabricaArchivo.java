package logica.poolConexiones;

import logica.interfaces.FabricaAbstracta;
import logica.interfaces.IDAOFolios;
import logica.interfaces.IDAORevisiones;
import logica.interfaces.IPoolConexiones;
import persistencia.daos.DAOFoliosArchivo;
import persistencia.daos.DAORevisionesArchivo;

public class FabricaArchivo implements FabricaAbstracta{

	@Override
	public IDAOFolios crearIDAOFolios() {
		// TODO Auto-generated method stub
		return new DAOFoliosArchivo();
	}

	@Override
	public IDAORevisiones crearIDAORevision(String codF) {
		// TODO Auto-generated method stub
		return new DAORevisionesArchivo();
	}
	
	public IPoolConexiones crearIPoolConexiones() {
		return new PoolConexionesArchivo();
	}

}
