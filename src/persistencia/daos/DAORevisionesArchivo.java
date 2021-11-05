package persistencia.daos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import logica.Folio;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.interfaces.IConexion;
import logica.interfaces.IDAORevisiones;
import logica.poolConexiones.SavesAndLoads;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VORevision;
import persistencia.consultas.ConsultasRevision;

public class DAORevisionesArchivo implements IDAORevisiones, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codFolio;
	private SavesAndLoads SaL;
	
	public DAORevisionesArchivo(String codF){
		codFolio = codF;
		SaL = new SavesAndLoads();
	}
	
	

	public boolean ExisteRevisionFolio(IConexion iCon, String codF, int numR) throws PersistenciaException 
	{
		try {
				ArrayList<Revision> arre = SaL.LoadRevisiones(codF);
				int i = 0;
				boolean encontro = false;
				while(i<arre.size() && !encontro) 
				{
					if(arre.get(i).getNumero() == numR)
						encontro = true;
					i++;
				}
				
				return encontro;
		}
		catch(PersistenciaException e)
		{
			throw new PersistenciaException(e.getMensaje());
		}
	}
	public void insback(IConexion iCon, Revision rev) throws PersistenciaException 
	{
		/*cargas el arreglo de revisiones que tengan el mismo numero de folio y lo agregas al final*/
		String codF = rev.getCodigoFolio();
		ArrayList<Revision> arre = null;
		
		if(largo(iCon)>0)
		{
			arre = SaL.LoadRevisiones(codF);
			arre.add(rev);
		}
		else
		{
			arre = new ArrayList<Revision>();
			arre.add(rev);
		}
		
		SaL.SaveTodasRevisiones(arre, codF);		
	}

	public int largo(IConexion iCon) throws PersistenciaException {
		ArrayList<Revision> arre = null;
		try
		{
			File file = new File("src/archivos/revisiones/"+"revision"+codFolio);
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

	public Revision kesimo(IConexion iCon, int Numero) throws PersistenciaException {
		int i=0;
		boolean encontro = false;
		try{
			ArrayList<Revision> arre = SaL.LoadRevisiones(codFolio);
			while(i<arre.size() && !encontro) {
				if(Numero == arre.get(i).getNumero())
					encontro = true;
				else
					i++;
			}
			return arre.get(i);
			
		}catch(PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}

	}

	public ArrayList<VORevision> listarRevisiones(IConexion iCon) throws PersistenciaException {
		int i=0;
		ArrayList<VORevision> A = new ArrayList<VORevision>();
		try{
			ArrayList<Revision> arre = SaL.LoadRevisiones(codFolio);
			for(i=0; i<arre.size(); i++) {
				int numero = arre.get(i).getNumero();
				String descripcion = arre.get(i).getDescripcion();
				String codigoF = arre.get(i).getCodigoFolio();
				A.add(new VORevision(numero, descripcion, codigoF));
				
			}
			return A;			
		}catch(PersistenciaException e){
			throw new PersistenciaException(e.getMensaje());
		}
	}

	@Override
	public void borrarRevisiones(IConexion iCon) throws PersistenciaException {
		File file = new File("folio" + codFolio);
		if(file.exists())
			file.delete();
	}

}
