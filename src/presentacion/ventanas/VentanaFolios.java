package presentacion.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Panel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.valueObjects.VOFolio;
import logica.valueObjects.VOFolioMaxRev;
import presentacion.controladores.ControladorVentanaFolios;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class VentanaFolios {
	// Atributos
	private JFrame frmFolios;
	private JTextField txtCodigo;
	private JTextField txtCaratula;
	private JTextField txtPaginas;
	private JTable TablaFolios;
	private ControladorVentanaFolios controlador;
	
	
	public VentanaFolios() {
		try {
			controlador = new ControladorVentanaFolios(this);
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
		initialize();
	}

	public void Visualizar () {
		frmFolios.setVisible(true);
	}
	
	private void initialize() {
		frmFolios = new JFrame();
		frmFolios.setTitle("Folios");
		frmFolios.setBounds(100, 100, 710, 481);
		frmFolios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFolios.getContentPane().setLayout(null);
		
		Panel panelAgregarFolio = new Panel();
		panelAgregarFolio.setBounds(10, 10, 672, 126);
		frmFolios.getContentPane().add(panelAgregarFolio);
		panelAgregarFolio.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(297, 20, 89, 23);
		panelAgregarFolio.add(btnAgregar);
		
		JLabel TxtCodigo = new JLabel("Codigo: ");
		TxtCodigo.setBounds(10, 24, 75, 14);
		panelAgregarFolio.add(TxtCodigo);
		
		JLabel TxtCaratula = new JLabel("Caratula: ");
		TxtCaratula.setBounds(10, 59, 75, 14);
		panelAgregarFolio.add(TxtCaratula);
		
		JLabel TxtPaginas = new JLabel("Paginas: ");
		TxtPaginas.setBounds(10, 90, 75, 14);
		panelAgregarFolio.add(TxtPaginas);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(95, 21, 134, 20);
		panelAgregarFolio.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCaratula = new JTextField();
		txtCaratula.setColumns(10);
		txtCaratula.setBounds(95, 56, 134, 20);
		panelAgregarFolio.add(txtCaratula);
		
		txtPaginas = new JTextField();
		txtPaginas.setColumns(10);
		txtPaginas.setBounds(95, 87, 134, 20);
		panelAgregarFolio.add(txtPaginas);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(297, 81, 89, 23);
		panelAgregarFolio.add(btnBorrar);
				
		JPanel panelListarFolios = new JPanel();
		panelListarFolios.setBounds(10, 166, 672, 276);
		frmFolios.getContentPane().add(panelListarFolios);
		panelListarFolios.setLayout(null);
		
	
		JButton btnListarFolios = new JButton("Listar Folios");
		btnListarFolios.setBounds(98, 11, 130, 23);
		panelListarFolios.add(btnListarFolios);
		
		JButton btnMasRevisado = new JButton("Mas revisado");
		btnMasRevisado.setBounds(446, 11, 130, 23);
		panelListarFolios.add(btnMasRevisado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(41, 45, 587, 218);
		panelListarFolios.add(scrollPane);
		
		TablaFolios = new JTable();
		TablaFolios = new javax.swing.JTable();
        TablaFolios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Codigo", "Caratula", "Paginas"
                }
        ));
        scrollPane.setViewportView(TablaFolios);
        		
		// Boton para agregar un folio
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodigo.getText().isEmpty() || txtCaratula.getText().isEmpty() || txtPaginas.getText().isEmpty() ) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar todos los campos antes de agregar el folio.");
				}else{
					String cod = txtCodigo.getText();
					String cara =  txtCaratula.getText();
					int pag = Integer.parseInt(txtPaginas.getText());
					
					try {
						// Llamo a AgregarFolio con los datos obtenidos de la ventana
						controlador.AgregarFolio(cod, cara, pag);
						
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (PersistenciaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					} catch (FolioException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					}
				}
			}
		});
		
		// Boton para borrar un folio y sus revisiones
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodigo.getText().isEmpty()) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe ingresar un codigo de folio antes borrarlo.");
				}else{
					String cod = txtCodigo.getText();
								
					try {
						// Llamo al borrarFolio con los datos obtenidos de la ventana
						controlador.BorrarFolio(cod);
						
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (RevisionException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					} catch (PersistenciaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					}
				}
			}
		});
		
		// Boton para listar el folio mas revisado
		btnMasRevisado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TablaListarFolioMasRevisado();
			}
		});
		
		
		// Boton para listar todos los folios
		btnListarFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TablaListarFolios();
			}
		});		
	}
	
    // Metodo que llama al controlador para obtener la lista de todos los folios, y cargar esa lista en una tabla
    public void TablaListarFolios(){
        DefaultTableModel model = (DefaultTableModel) TablaFolios.getModel();        
        ArrayList<VOFolio> arre = null;
        
		try {
			// Llamo a ListarFolios y me quedo con ese arraylist de VOFolio para cargar todas las filas
			arre = controlador.ListarFolios();
			
	        Object rowData[] = new Object[3];
            
	        Iterator<VOFolio> iter = arre.iterator(); 
	        while(iter.hasNext()) {
	        	VOFolio i = iter.next();
	        	rowData[0] = i.getCodigo();
	        	rowData[1] = i.getCaratula();
	        	rowData[2] = i.getPaginas();
	            model.addRow(rowData);
	        }
	        
		} catch (RemoteException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (PersistenciaException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMensaje());
		} catch (FolioException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
    }
    
    // Metodo que llama al controlador para obtener el folio mas revisado y cargarlo en la tabla
    public void TablaListarFolioMasRevisado(){
        VOFolioMaxRev VOF = null;
    	DefaultTableModel model = (DefaultTableModel) TablaFolios.getModel();
        Object rowData[] = new Object[3];
        
		try {
			// Llamo a FolioMasRevisado y me quedo con el VOFolioMaxRev para cargarlo en la tabla
			VOF = controlador.FolioMasRevisado();
			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		} catch (FolioException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
		
       	rowData[0] = VOF.getCodigo();
        rowData[1] = VOF.getCaratula();
        rowData[2] = VOF.getPaginas();
        model.addRow(rowData);
    }
    
}

