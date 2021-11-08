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
import java.awt.Color;
import javax.swing.border.LineBorder;

public class VentanaFolios {
	// Atributos
	private JFrame frmFolios;
	private JTextField txtCodigo;
	private JTextField txtCaratula;
	private JTextField txtPaginas;
	private JTable TablaFolios;
	private JTable TablaFolios_1;
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
		frmFolios.setBounds(100, 100, 710, 480);
		frmFolios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFolios.getContentPane().setLayout(null);
		
		JPanel panelAgregarFolio = new JPanel();
		panelAgregarFolio.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelAgregarFolio.setBounds(10, 10, 672, 126);
		frmFolios.getContentPane().add(panelAgregarFolio);
		panelAgregarFolio.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(297, 20, 89, 23);
		panelAgregarFolio.add(btnAgregar);
		
		JLabel TxtCodigo = new JLabel("C\u00F3digo: ");
		TxtCodigo.setBounds(10, 24, 75, 14);
		panelAgregarFolio.add(TxtCodigo);
		
		JLabel TxtCaratula = new JLabel("Caratula: ");
		TxtCaratula.setBounds(10, 59, 75, 14);
		panelAgregarFolio.add(TxtCaratula);
		
		JLabel TxtPaginas = new JLabel("P\u00E1ginas: ");
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
		
		final JLabel lblFaltaCodigo = new JLabel("");
		lblFaltaCodigo.setForeground(Color.RED);
		lblFaltaCodigo.setBounds(241, 24, 46, 14);
		panelAgregarFolio.add(lblFaltaCodigo);
		
		final JLabel lblFaltaCaratula = new JLabel("");
		lblFaltaCaratula.setForeground(Color.RED);
		lblFaltaCaratula.setBounds(241, 59, 46, 14);
		panelAgregarFolio.add(lblFaltaCaratula);
		
		final JLabel lblFaltaPagina = new JLabel("");
		lblFaltaPagina.setForeground(Color.RED);
		lblFaltaPagina.setBounds(239, 90, 46, 14);
		panelAgregarFolio.add(lblFaltaPagina);
				
		JPanel panelListarFolios = new JPanel();
		panelListarFolios.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelListarFolios.setBounds(10, 142, 672, 288);
		frmFolios.getContentPane().add(panelListarFolios);
		panelListarFolios.setLayout(null);
		
	
		JButton btnListarFolios = new JButton("Listar Folios");
		btnListarFolios.setBounds(98, 11, 130, 23);
		panelListarFolios.add(btnListarFolios);
		
		JButton btnMasRevisado = new JButton("M\u00E1s revisado");
		btnMasRevisado.setBounds(446, 11, 130, 23);
		panelListarFolios.add(btnMasRevisado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 652, 231);
		panelListarFolios.add(scrollPane);
		
		TablaFolios = new JTable();
		TablaFolios_1 = new javax.swing.JTable() {
			public boolean isCellEditable(int row, int column){
				return false;
				}
		};
		TablaFolios_1.setRowSelectionAllowed(false);
        TablaFolios_1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Código", "Caratula", "Páginas"
                }
        ));
        scrollPane.setViewportView(TablaFolios_1);
        		
		// Boton para agregar un folio
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodigo.getText().isEmpty() || txtCaratula.getText().isEmpty() || txtPaginas.getText().isEmpty() ) {
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCodigo.setText("(*)");
					lblFaltaCodigo.setBackground(Color.red);
					lblFaltaCaratula.setText("(*)");
					lblFaltaPagina.setText("(*)");
					
					
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar todos los campos requeridos antes de agregar el folio "+lblFaltaCodigo.getText()+".");
				}else{
					String cod = txtCodigo.getText();
					String cara =  txtCaratula.getText();
					int pag = Integer.parseInt(txtPaginas.getText());
					
					// Dejo en blanco la marca de texto requerido
					lblFaltaCodigo.setText("");
					lblFaltaCaratula.setText("");
					lblFaltaPagina.setText("");
					
					try {
						// Pongo los campos textos en blanco luego de ejecutar la accion del boton
						txtCodigo.setText("");
						txtCaratula.setText("");
						txtPaginas.setText("");
						
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
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCodigo.setText("(*)");
					lblFaltaCaratula.setText("");
					lblFaltaPagina.setText("");
					
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe ingresar un codigo de folio antes borrarlo "+lblFaltaCodigo.getText()+".");
					
				}else{
					// Dejo en blanco la marca de texto requerido
					lblFaltaCodigo.setText("");
					lblFaltaCaratula.setText("");
					lblFaltaPagina.setText("");
					
					String cod = txtCodigo.getText();
								
					try {
						// Pongo los campos textos en blanco luego de ejecutar la accion del boton
						txtCodigo.setText("");
						txtCaratula.setText("");
						txtPaginas.setText("");
						
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
    public void TablaListarFolios()
    {
        DefaultTableModel model = (DefaultTableModel) TablaFolios_1.getModel();        
        ArrayList<VOFolio> arre = null;
        
		try {
			
			// Llamo a ListarFolios y me quedo con ese arraylist de VOFolio para cargar todas las filas
			arre = controlador.ListarFolios();
			
	        Object rowData[] = new Object[3];
            
	        Iterator<VOFolio> iter = arre.iterator();
	        
	        model.setRowCount(0);
	        
	        while(iter.hasNext()) 
	        {
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
    	DefaultTableModel model = (DefaultTableModel) TablaFolios_1.getModel();
        Object rowData[] = new Object[3];
        model.setRowCount(0);
        
		try {
			// Llamo a FolioMasRevisado y me quedo con el VOFolioMaxRev para cargarlo en la tabla
			VOF = controlador.FolioMasRevisado();
			

			if (VOF == null) {
				System.out.println("Es null");
			}
			else 
			{
				System.out.println("Cant revisiones de ese folio:" +VOF.getCantRevisiones());
				
		       	rowData[0] = VOF.getCodigo();
		        rowData[1] = VOF.getCaratula();
		        rowData[2] = VOF.getPaginas();
		        model.addRow(rowData);
			}


	        
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		} catch (FolioException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
    }
    
}

