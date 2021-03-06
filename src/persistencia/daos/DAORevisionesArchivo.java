package persistencia.daos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.poolConexiones.SavesAndLoads;
import logica.valueObjects.VORevision;

public class DAORevisionesArchivo implements IDAORevisiones, Serializable 
{
	// Codigo autogenerado
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String codFolio;
	private SavesAndLoads SaL;
	
	// Constructor
	public DAORevisionesArchivo(String codF){
		codFolio = codF;
		SaL = new SavesAndLoads();
	}
	
	
	// Metodo que inserta una revision al final de la lista de revisiones
	public void insback(IConexion iCon, Revision rev) throws PersistenciaException 
	{
		// cargas el arreglo de revisiones que tengan el mismo numero de folio y lo agregas al final
		ArrayList<Revision> arre = null;
		if(largo(iCon)>0)
		{
			arre = SaL.LoadRevisiones(codFolio);
			arre.add(rev);
		}
		else
		{
			arre = new ArrayList<Revision>();
			arre.add(rev);
			borrarRevisiones(iCon);
		}
		
		SaL.SaveTodasRevisiones(arre, codFolio);		
	}

	// Metodo que devuelve el largo de la secuencia de revisiones
	public int largo(IConexion iCon) throws PersistenciaException {
		ArrayList<Revision> arre = null;
		try
		{
			File file = new File("src/archivos/revisiones/"+"revisiones"+codFolio);
			if(file.exists())
			{
				arre = SaL.LoadRevisiones(codFolio);
			}
			
			if (arre != null)
				return arre.size();
			else
				return 0;
			
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
	}

	// Metodo para obtener el k-esimo elemento de la secuencia de revisiones
	public Revision kesimo(IConexion iCon, int Numero) throws PersistenciaException {
		int i=0;
		boolean encontro = false;
		ArrayList<Revision> arre = null;
		try{
			arre = SaL.LoadRevisiones(codFolio);
			while(i<arre.size() && !encontro) {
				if(Numero == arre.get(i).getNumero())
					encontro = true;
				else
					i++;
			}
			
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
		
		if (arre.size() >= Numero)
			return arre.get(i);
		else
			return null;

	}

	// Metodo para obtener una lista con todas las revisiones
	// Precondicion: Hay al menos 1 revision en el folio
	public ArrayList<VORevision> listarRevisiones(IConexion iCon) throws PersistenciaException 
	{
		int i=0;
		ArrayList<VORevision> A = new ArrayList<VORevision>();
		try{
			ArrayList<Revision> arre = SaL.LoadRevisiones(codFolio);
			for(i=0; i<arre.size(); i++) {
				int numero = arre.get(i).getNumero();
				String descripcion = arre.get(i).getDescripcion();
				String codigoF = codFolio;
				A.add(new VORevision(numero, descripcion, codigoF));
			}
			
			return A;
			
		}catch(PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}
		
	}

	// Metodo para borrar todas las revisiones de la secuencia
	@Override
	public void borrarRevisiones(IConexion iCon) throws PersistenciaException {
		File file = new File("src/archivos/revisiones/"+"revisiones" + codFolio);
		if(file.exists())
			file.delete();
	}

}
