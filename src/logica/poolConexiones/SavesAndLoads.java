package logica.poolConexiones;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;

import logica.Folio;
import logica.Revision;
import logica.excepciones.PersistenciaException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;

public class SavesAndLoads implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void SaveFolio(String nomArch, Folio T) throws PersistenciaException
	{
		try {
			//Writer output = null;
			FileOutputStream f = new FileOutputStream("src/archivos/folios/"+"folio" + nomArch, true);
			//File f = new File ("C:/Users/Alvaro/Google Drive/Estudios/Ingeniería/UDE/6 - Sexto Semestre/Bases de Datos III/Obligatorio/ObligatorioBDIII/src/archivos/","folio" + nomArch);
			ObjectOutputStream o = new ObjectOutputStream(f);
			//output = new BufferedWriter(new FileWriter(f));"C:/Users/Alvaro/Google Drive/Estudios/Ingeniería/UDE/6 - Sexto Semestre/Bases de Datos III/Obligatorio/ObligatorioBDIII/src/archivos/"
			o.writeObject (T);
			o.close();
			f.close();
			//output.close();
		}
		catch(IOException e){
			e.printStackTrace();
			throw new PersistenciaException("Error al Guardar");
		}
		
	}
	public void SaveTodasRevisiones(ArrayList<Revision> T, String codF) throws PersistenciaException
	{
		try {
				FileOutputStream f = new FileOutputStream("src/archivos/revisiones/"+"revisiones"+codF, true);
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
				FileInputStream f = new FileInputStream("src/archivos/revisiones/"+"revisiones" + codf);
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
