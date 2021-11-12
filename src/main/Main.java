package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

import javax.swing.JOptionPane;

import logica.Fachada;
import logica.excepciones.PersistenciaException;

public class Main {

	public static void main(String[] args) 
	{
		try {
			Properties p = new Properties();
		
			String nomArch = "src/Config/Config.properties";
			p.load (new FileInputStream (nomArch));

			String port = p.getProperty("portServer");
			String ip = p.getProperty("ipServer");
						
			// instancio mi Objeto Remoto y lo publico en el rmiregistry
			// pongo a correr el rmiregistry
			LocateRegistry.createRegistry(Integer.parseInt(port));
			
			// instancio mi Objeto Remoto y lo publico en la ruta especificada
			Fachada fachada = null;
			try {
				fachada = Fachada.getFachada();
			} catch (PersistenciaException e) {
				JOptionPane.showMessageDialog(null, "Ocurrio un error al iniciar el servidor.");
			}
			String ruta = "//" + ip + ":" + port + "/logica";
			
			Naming.rebind(ruta, fachada);
			JOptionPane.showMessageDialog(null, "Servidor iniciado");
		}
		// Manejar estas excepciones de manera personalizada
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "Ocurrio un error no especificado, contacte con el administrador.");
		} 
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, "Ocurrio un error no especificado, contacte con el administrador.");
		}

	}

}
