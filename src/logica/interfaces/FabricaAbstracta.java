package logica.interfaces;

public interface FabricaAbstracta {
	
	public IDAOFolios crearIDAOFolios();
	
	public IDAORevisiones crearIDAORevision(String codF);
	
	public IPoolConexiones crearIPoolConexiones ();
	
}
