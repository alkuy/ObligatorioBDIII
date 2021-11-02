package logica.poolConexiones;

import logica.interfaces.FabricaAbstracta;
import logica.interfaces.IDAOFolios;
import logica.interfaces.IDAORevisiones;
import logica.interfaces.IPoolConexiones;
import persistencia.daos.DAOFolios;
import persistencia.daos.DAORevisiones;

public class FabricaSQL implements FabricaAbstracta{

	@Override
	public IDAOFolios crearIDAOFolios() {
		// TODO Auto-generated method stub
		return new DAOFolios();
	}

	@Override
	public IDAORevisiones crearIDAORevision(String codF) {
		// TODO Auto-generated method stub
		return new DAORevisiones(codF);
	}

	public IPoolConexiones crearIPoolConexiones() {
		return new PoolConexiones();
	}
	
}
