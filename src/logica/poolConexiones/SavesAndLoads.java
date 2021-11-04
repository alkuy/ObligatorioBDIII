package logica.poolConexiones;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import logica.Folio;
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

	public void SaveRevisiones(String nomArch, VOFolioMaxRev T) throws PersistenciaException
	{
		try {

			FileOutputStream f = new FileOutputStream("revisiones" + nomArch);
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
	public static VOFolioMaxRev LoadRevisiones (String nomArch)throws PersistenciaException
	{ 
		try
			{
				FileInputStream f = new FileInputStream(nomArch);
				ObjectInputStream o = new ObjectInputStream(f);
				VOFolioMaxRev V = (VOFolioMaxRev) o.readObject();
				o.close();
				f.close();
				return V;
			}
			catch (IOException | ClassNotFoundException e){
				e.printStackTrace();
				throw new PersistenciaException("Error al Cargar");
			}
	}	
}
