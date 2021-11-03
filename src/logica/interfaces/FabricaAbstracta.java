package logica.interfaces;

import java.rmi.Remote;

public interface FabricaAbstracta extends Remote{
	
	public IDAOFolios crearIDAOFolios();
	
	public IDAORevisiones crearIDAORevision(String codF);
	
	public IPoolConexiones crearIPoolConexiones ();
	
}
