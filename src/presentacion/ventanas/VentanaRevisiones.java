package presentacion.ventanas;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.valueObjects.VORevision;
import presentacion.controladores.ControladorVentanaRevisiones;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaRevisiones {

	private JFrame frmRevisiones;
	private ControladorVentanaRevisiones controlador;
	private JTextField txtCodigoBuscar;
	private JTextField txtNumero;
	private JTextField txtCodF;
	private JTextField txtDescrip;
	private JTable TablaRevisiones;

	public VentanaRevisiones() {
		try {
			controlador = new ControladorVentanaRevisiones(this);
			// Inicializo todos los componentes de la ventana
			initialize();
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
	}
	
	public void Visualizar () {
		frmRevisiones.setVisible(true);
	}

	private void initialize() {
		frmRevisiones = new JFrame();
		frmRevisiones.setTitle("Revisiones");
		frmRevisiones.setBounds(100, 100, 755, 506);
		frmRevisiones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRevisiones.getContentPane().setLayout(null);
		
		JPanel panelAgregarRevision = new JPanel();
		panelAgregarRevision.setBounds(10, 11, 703, 223);
		frmRevisiones.getContentPane().add(panelAgregarRevision);
		panelAgregarRevision.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(286, 44, 119, 23);
		panelAgregarRevision.add(btnAgregar);
		
		JLabel lblCodF = new JLabel("Codigo de Folio:");
		lblCodF.setBounds(10, 32, 108, 14);
		panelAgregarRevision.add(lblCodF);
		
		JLabel lblDescrip = new JLabel("Descripcion:");
		lblDescrip.setBounds(10, 66, 108, 14);
		panelAgregarRevision.add(lblDescrip);
		
		txtCodF = new JTextField();
		txtCodF.setColumns(10);
		txtCodF.setBounds(128, 27, 86, 20);
		panelAgregarRevision.add(txtCodF);
		
		txtDescrip = new JTextField();
		txtDescrip.setColumns(10);
		txtDescrip.setBounds(128, 63, 86, 20);
		panelAgregarRevision.add(txtDescrip);
		
		JLabel lblNumero = new JLabel("Numero:");
		lblNumero.setBounds(10, 144, 108, 14);
		panelAgregarRevision.add(lblNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(128, 141, 86, 20);
		panelAgregarRevision.add(txtNumero);
		txtNumero.setColumns(10);
		
		JButton BtnDarDescrip = new JButton("Dar descripcion");
		BtnDarDescrip.setBounds(286, 140, 119, 23);
		panelAgregarRevision.add(BtnDarDescrip);
		
		JLabel lblDarDescrip = new JLabel("Descripcion:");
		lblDarDescrip.setBounds(469, 144, 206, 50);
		panelAgregarRevision.add(lblDarDescrip);
		
		JPanel panelListarRevisiones = new JPanel();
		panelListarRevisiones.setBounds(10, 245, 703, 211);
		frmRevisiones.getContentPane().add(panelListarRevisiones);
		panelListarRevisiones.setLayout(null);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(286, 10, 119, 23);
		panelListarRevisiones.add(btnListar);
		
		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(10, 14, 63, 14);
		panelListarRevisiones.add(lblCodigo);
		
		txtCodigoBuscar = new JTextField();
		txtCodigoBuscar.setBounds(128, 11, 86, 20);
		panelListarRevisiones.add(txtCodigoBuscar);
		txtCodigoBuscar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(37, 44, 641, 156);
		panelListarRevisiones.add(scrollPane);

		TablaRevisiones = new JTable();
		TablaRevisiones = new JTable();
		TablaRevisiones = new javax.swing.JTable();
		TablaRevisiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Numero", "Codigo de Folio", "Descripcion"
                }
        ));
        scrollPane.setViewportView(TablaRevisiones);
        
        // Boton para agregar una revision a un folio
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodF.getText().isEmpty() || txtDescrip.getText().isEmpty() ) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar el c�digo del folio y la descripci�n de la revisi�n antes de agregar una nueva revisi�n.");
				}else{
					String codF = txtCodF.getText();
					String descrip =  txtDescrip.getText();
					
					try {
						// Llamo a AgregarFolio con los datos obtenidos de la ventana
						controlador.AgregarRevision(codF, descrip);
						
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (PersistenciaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					} catch (RevisionException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					}
				}
			}
		});
		
		// Boton para obtener la descripcion de una revision
		BtnDarDescrip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodF.getText().isEmpty() || txtNumero.getText().isEmpty() ) {
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar el c�digo del folio y el n�mero de la revisi�n antes de obtener su descripci�n.");
				}else{
					String codF = txtCodF.getText();
					String numR = txtNumero.getText();
					int num =  Integer.parseInt(numR);
					
					try {
						// Llamo a AgregarFolio con los datos obtenidos de la ventana
						lblDarDescrip.setText(controlador.DarDescripcion(codF, num));
						
					} catch (RemoteException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (PersistenciaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					} catch (FolioException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					} catch (RevisionException e1) {
						JOptionPane.showMessageDialog(null, e1.getMensaje());
					}
				}
			}
		});
		
		
		// Boton para listar todas las revisiones de un folio
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
	}
	
	// Metodo que llama al controlador para obtener la lista de todos las revisiones de un folio, y cargar esa lista en una tabla
    public void TablaListarRevisiones(){
        DefaultTableModel model = (DefaultTableModel) TablaRevisiones.getModel();        
        ArrayList<VORevision> arre = null;
        String codF = txtCodigoBuscar.getText();
        
		try {
			// Llamo a ListarFolios y me quedo con ese arraylist de VOFolio para cargar todas las filas
			arre = controlador.ListarRevisiones(codF);
			
	        Object rowData[] = new Object[3];
            
	        Iterator<VORevision> iter = arre.iterator(); 
	        while(iter.hasNext()) {
	        	VORevision i = iter.next();
	        	rowData[0] = i.getNumero();
	        	rowData[1] = i.getCodFolio();
	        	rowData[2] = i.getDescripcion();
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
