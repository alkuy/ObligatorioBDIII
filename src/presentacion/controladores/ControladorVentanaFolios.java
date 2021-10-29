package presentacion.controladores;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JOptionPane;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.interfaces.IFachada;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import presentacion.ventanas.VentanaFolios;



public class ControladorVentanaFolios {
	// Tengo como atributo remoto a la fachada y como atributo local a mi ventana
		private IFachada fachada;
		@SuppressWarnings("unused") // Codigo automatico para eliminar la warning de esta linea
		private VentanaFolios vent;
		
		public ControladorVentanaFolios (VentanaFolios v){
			ControladorVentanaFolios controlador = new ControladorVentanaFolios(this);
			Properties p = new Properties();
			
			String nomArch = "src/Config/Config.properties";
			try {
				p.load (new FileInputStream (nomArch));
				String port = p.getProperty("portServer");
				String ip = p.getProperty("ipServer");
				
				String ruta = "//" + ip + ":" + port + "/fachada";
				
				fachada = (IFachada) Naming.lookup(ruta);
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Ocurrio un problema con la conexion del servidor.");
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Ocurrio un problema con la conexion del servidor.");
			} catch (NotBoundException e) {
				JOptionPane.showMessageDialog(null, "Ocurrio un problema con la conexion del servidor.");
			}
		}
		
		public ControladorVentanaFolios(ControladorVentanaFolios controladorVentanaFolios) {
			// TODO Auto-generated constructor stub
		}

		public void AgregarFolio (String cod, String cara, int pag){
			try{
				VOFolio vo = new VOFolio(cod, cara, pag);
				this.fachada.AgregarFolio(vo);
				
				// Despliego mensaje de confirmacion en pantalla
				JOptionPane.showMessageDialog(null, "Folio agregado correctamente.");
	
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}catch(FolioException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}catch(RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void BorrarFolio (String cod){
			try{
				this.fachada.BorrarFolioRevisiones(cod);
				
				// Despliego mensaje de confirmacion en pantalla
				JOptionPane.showMessageDialog(null, "El folio con sus revisiones fue eliminado correctamente.");
				
			}catch (PersistenciaException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMensaje());
			}catch(RemoteException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (RevisionException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public VOFolioMaxRev FolioMasRevisado (){
			VOFolioMaxRev vo = null;
			try{
				vo = this.fachada.FolioMasRevisado();
				
				// Despliego mensaje de confirmacion en pantalla (opcional)
				JOptionPane.showMessageDialog(null, "El folio con mas revisiones fue mostrado en pantalla correctamente.");
				
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (FolioException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return vo;
		}
		
		public ArrayList<VOFolio> ListarFolios(){
			ArrayList<VOFolio> lista = null;
			try{
				lista = this.fachada.ListarFolios();
			}catch (PersistenciaException e){
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (RemoteException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			} catch (FolioException e) {
				JOptionPane.showMessageDialog(null, e.getMensaje());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return lista;
		}
}

