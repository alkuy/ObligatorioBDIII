package logica.poolConexiones;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import logica.Folio;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;

public class SavesAndLoads {
	
	public void SaveFolio(String nomArch, VOFolio T) throws PersistenciaException
	{
		try {

			FileOutputStream f = new FileOutputStream("folio" + nomArch);
			ObjectOutputStream o = new ObjectOutputStream(f);
			o.writeObject (T);
			o.close();
			f.close();
		}
		catch(IOException e){
			e.printStackTrace();
			throw new PersistenciaException("Error al Guardar");
		}
		
	}
	public void SaveTodasRevisiones(ArrayList<Revision> T, String codF) throws PersistenciaException
	{
		try {
				FileOutputStream f = new FileOutputStream("revisiones" + codF);
				ObjectOutputStream o = new ObjectOutputStream(f);
				o.writeObject (T);
				o.close();
				f.close();
			
		}catch(IOException e) {
			e.printStackTrace();
			throw new PersistenciaException("Error al Guardar");
		}
	}
	public Folio LoadFolios (String nomArch)throws PersistenciaException
	{ 
		try
			{
				FileInputStream f = new FileInputStream(nomArch);
				ObjectInputStream o = new ObjectInputStream(f);
				Folio V = (Folio) o.readObject();
				o.close();
				f.close();
				return V;
			}
			catch (IOException | ClassNotFoundException e){
				e.printStackTrace();
				throw new PersistenciaException("Error al Cargar");
			}
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Revision> LoadRevisiones(String codf)throws PersistenciaException
	{ 
		try
			{
				FileInputStream f = new FileInputStream("revision" + codf);
				ObjectInputStream o = new ObjectInputStream(f);
				ArrayList<Revision> arre = (ArrayList<Revision>) o.readObject();
				o.close();
				f.close();
				return arre;
			}
			catch (IOException | ClassNotFoundException e){
				e.printStackTrace();
				throw new PersistenciaException("Error al Cargar");
			}
	}

}
