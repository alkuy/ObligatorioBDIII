package logica.poolConexiones;

import logica.interfaces.FabricaAbstracta;
import logica.interfaces.IDAOFolios;
import logica.interfaces.IDAORevisiones;
import logica.interfaces.IPoolConexiones;
import persistencia.daos.DAOFoliosXML;
import persistencia.daos.DAORevisionesXML;

public class FabricaXML implements FabricaAbstracta{

	@Override
	public IDAOFolios crearIDAOFolios() {
		// TODO Auto-generated method stub
		return new DAOFoliosXML();
	}

	@Override
	public IDAORevisiones crearIDAORevision(String codF) {
		// TODO Auto-generated method stub
		return new DAORevisionesXML();
	}
	
	public IPoolConexiones crearIPoolConexiones() {
		return new PoolConexionesXML();
	}

}
