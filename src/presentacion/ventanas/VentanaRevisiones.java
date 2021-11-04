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
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

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
		frmRevisiones.setBounds(100, 100, 710, 480);
		frmRevisiones.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRevisiones.getContentPane().setLayout(null);
		
		JPanel panelAgregarRevision = new JPanel();
		panelAgregarRevision.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelAgregarRevision.setBounds(10, 11, 674, 147);
		frmRevisiones.getContentPane().add(panelAgregarRevision);
		panelAgregarRevision.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(288, 28, 138, 23);
		panelAgregarRevision.add(btnAgregar);
		
		JLabel lblCodF = new JLabel("C\u00F3digo de Folio:");
		lblCodF.setBounds(10, 16, 108, 14);
		panelAgregarRevision.add(lblCodF);
		
		JLabel lblDescrip = new JLabel("Descripci\u00F3n:");
		lblDescrip.setBounds(10, 50, 108, 14);
		panelAgregarRevision.add(lblDescrip);
		
		txtCodF = new JTextField();
		txtCodF.setColumns(10);
		txtCodF.setBounds(118, 13, 107, 20);
		panelAgregarRevision.add(txtCodF);
		
		txtDescrip = new JTextField();
		txtDescrip.setColumns(10);
		txtDescrip.setBounds(118, 47, 107, 20);
		panelAgregarRevision.add(txtDescrip);
		
		JLabel lblNumero = new JLabel("N\u00FAmero:");
		lblNumero.setBounds(10, 88, 108, 14);
		panelAgregarRevision.add(lblNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(118, 85, 107, 20);
		panelAgregarRevision.add(txtNumero);
		txtNumero.setColumns(10);
		
		JButton BtnDarDescrip = new JButton("Ver descripci\u00F3n");
		BtnDarDescrip.setBounds(288, 84, 138, 23);
		panelAgregarRevision.add(BtnDarDescrip);
		
		final JLabel lblDarDescrip = new JLabel("");
		lblDarDescrip.setBounds(443, 82, 221, 23);
		panelAgregarRevision.add(lblDarDescrip);
		
		final JLabel lblFaltaCod = new JLabel("");
		lblFaltaCod.setForeground(Color.RED);
		lblFaltaCod.setBounds(232, 16, 46, 14);
		panelAgregarRevision.add(lblFaltaCod);
		
		final JLabel lblFaltaDesc = new JLabel("");
		lblFaltaDesc.setForeground(Color.RED);
		lblFaltaDesc.setBounds(232, 50, 46, 14);
		panelAgregarRevision.add(lblFaltaDesc);
		
		final JLabel lblFaltaNum = new JLabel("");
		lblFaltaNum.setForeground(Color.RED);
		lblFaltaNum.setBounds(232, 88, 46, 14);
		panelAgregarRevision.add(lblFaltaNum);
		
		JLabel lblDescripcin = new JLabel("Descripci\u00F3n:");
		lblDescripcin.setBounds(514, 57, 83, 14);
		panelAgregarRevision.add(lblDescripcin);
		
		JPanel panelListarRevisiones = new JPanel();
		panelListarRevisiones.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelListarRevisiones.setBounds(10, 169, 674, 261);
		frmRevisiones.getContentPane().add(panelListarRevisiones);
		panelListarRevisiones.setLayout(null);
		
		JLabel lblCodigo = new JLabel("C\u00F3digo: ");
		lblCodigo.setBounds(10, 14, 63, 14);
		panelListarRevisiones.add(lblCodigo);
		
		txtCodigoBuscar = new JTextField();
		txtCodigoBuscar.setBounds(118, 11, 107, 20);
		panelListarRevisiones.add(txtCodigoBuscar);
		txtCodigoBuscar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 654, 204);
		panelListarRevisiones.add(scrollPane);

		TablaRevisiones = new JTable();
		TablaRevisiones = new JTable();
		TablaRevisiones = new javax.swing.JTable();
		TablaRevisiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Número", "Código de Folio", "Descripción"
                }
        ));
        scrollPane.setViewportView(TablaRevisiones);
        
        JButton btnListar = new JButton("Listar");
        btnListar.setBounds(288, 10, 138, 23);
        panelListarRevisiones.add(btnListar);
        
        final JLabel lblFaltaCodListar = new JLabel("");
        lblFaltaCodListar.setForeground(Color.RED);
        lblFaltaCodListar.setBounds(235, 14, 46, 14);
        panelListarRevisiones.add(lblFaltaCodListar);
        
        // Boton para listar todas las revisiones de un folio
        btnListar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(txtCodigoBuscar.getText().isEmpty()) {
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("(*)");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					
        		}else {
					// Dejo en blanco la marca de texto requerido
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					
					// Pongo los campos textos en blanco luego de ejecutar la accion del boton
					txtCodF.setText("");
					txtCodigoBuscar.setText("");
					txtNumero.setText("");
					txtDescrip.setText("");
					
					TablaListarRevisiones();
        		}
        	}
        });
        
        // Boton para agregar una revision a un folio
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Validaciones previas al obtener los datos:
				if(txtCodF.getText().isEmpty() || txtDescrip.getText().isEmpty() ) {
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCod.setText("(*)");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("(*)");
					lblFaltaNum.setText("");
					
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar los campos requeridos antes de agregar una nueva revisión (*).");
				}else{
					// Dejo en blanco la marca de texto requerido
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					
					String codF = txtCodF.getText();
					String descrip =  txtDescrip.getText();
					
					try {
						// Pongo los campos textos en blanco luego de ejecutar la accion del boton
						txtCodF.setText("");
						txtCodigoBuscar.setText("");
						txtNumero.setText("");
						txtDescrip.setText("");
						
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
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCod.setText("(*)");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("(*)");
					
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar los campos requeridos antes de obtener su descripción (*).");
				}else{
					// Dejo en blanco la marca de texto requerido
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					
					String codF = txtCodF.getText();
					String numR = txtNumero.getText();
					int num =  Integer.parseInt(numR);
					
					try {
						// Pongo los campos textos en blanco luego de ejecutar la accion del boton
						txtCodF.setText("");
						txtCodigoBuscar.setText("");
						txtNumero.setText("");
						txtDescrip.setText("");
						
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
            
	        model.setRowCount(0);
	        
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
