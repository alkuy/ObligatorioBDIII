package presentacion.ventanas;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;
import logica.excepciones.FolioException;
import logica.excepciones.PersistenciaException;
import logica.excepciones.RevisionException;
import logica.valueObjects.VORevision;
import presentacion.controladores.ControladorVentanaRevisiones;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JRadioButton;

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
		panelAgregarRevision.setBounds(10, 11, 674, 161);
		frmRevisiones.getContentPane().add(panelAgregarRevision);
		panelAgregarRevision.setLayout(null);
		
		JButton btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(138, 127, 138, 23);
		panelAgregarRevision.add(btnAgregar);
		btnAgregar.setVisible(false);
		
		JLabel lblCodF = new JLabel("C\u00F3digo de Folio:");
		lblCodF.setBounds(30, 19, 108, 14);
		panelAgregarRevision.add(lblCodF);
		
		txtCodF = new JTextField();
		txtCodF.setColumns(10);
		txtCodF.setBounds(138, 16, 107, 20);
		panelAgregarRevision.add(txtCodF);
		
		txtDescrip = new JTextField();
		txtDescrip.setColumns(10);
		txtDescrip.setBounds(138, 68, 107, 20);
		panelAgregarRevision.add(txtDescrip);
		txtDescrip.setVisible(false);
		
		JLabel lblNumeroODesc = new JLabel("");
		lblNumeroODesc.setBounds(30, 71, 108, 14);
		panelAgregarRevision.add(lblNumeroODesc);
		
		txtNumero = new JTextField();
		txtNumero.setBounds(138, 68, 107, 20);
		panelAgregarRevision.add(txtNumero);
		txtNumero.setColumns(10);
		txtNumero.setVisible(false);
		
		JButton BtnDarDescrip = new JButton("Ver descripcion");
		BtnDarDescrip.setBounds(138, 127, 138, 23);
		panelAgregarRevision.add(BtnDarDescrip);
		BtnDarDescrip.setVisible(false);
		
		final JLabel lblFaltaCod = new JLabel("");
		lblFaltaCod.setForeground(Color.RED);
		lblFaltaCod.setBounds(245, 19, 46, 14);
		panelAgregarRevision.add(lblFaltaCod);
		
		final JLabel lblFaltaDesc = new JLabel("");
		lblFaltaDesc.setForeground(Color.RED);
		lblFaltaDesc.setBounds(245, 74, 46, 14);
		panelAgregarRevision.add(lblFaltaDesc);
		
		final JLabel lblFaltaNum = new JLabel("");
		lblFaltaNum.setForeground(Color.RED);
		lblFaltaNum.setBounds(245, 74, 46, 14);
		panelAgregarRevision.add(lblFaltaNum);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		lblDescripcion.setBounds(301, 71, 75, 14);
		panelAgregarRevision.add(lblDescripcion);
		lblDescripcion.setVisible(false);
		
		JRadioButton rdbAgregar = new JRadioButton("Agregar revision");
		rdbAgregar.setBounds(297, 16, 131, 23);
		panelAgregarRevision.add(rdbAgregar);
		
		JRadioButton rdbDesc = new JRadioButton("Ver descripcion");
		rdbDesc.setBounds(430, 15, 146, 23);
		panelAgregarRevision.add(rdbDesc);
		
		ButtonGroup G = new ButtonGroup();
		G.add(rdbAgregar);	
		G.add(rdbDesc);
		
		JPanel panelVerDescripcion = new JPanel();
		panelVerDescripcion.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelVerDescripcion.setBounds(382, 65, 282, 63);
		panelAgregarRevision.add(panelVerDescripcion);
		panelVerDescripcion.setVisible(false);
		
		final JLabel lblDarDescrip = new JLabel("");
		panelVerDescripcion.add(lblDarDescrip);
		
		JPanel panelListarRevisiones = new JPanel();
		panelListarRevisiones.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelListarRevisiones.setBounds(10, 183, 674, 247);
		frmRevisiones.getContentPane().add(panelListarRevisiones);
		panelListarRevisiones.setLayout(null);
		
		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(10, 14, 63, 14);
		panelListarRevisiones.add(lblCodigo);
		
		txtCodigoBuscar = new JTextField();
		txtCodigoBuscar.setBounds(118, 11, 107, 20);
		panelListarRevisiones.add(txtCodigoBuscar);
		txtCodigoBuscar.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 47, 654, 189);
		panelListarRevisiones.add(scrollPane);

		TablaRevisiones = new JTable();
		TablaRevisiones = new JTable();
		TablaRevisiones = new javax.swing.JTable(){
			// Codigo autogenerado
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){
				return false;
				}
		};
		TablaRevisiones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Numero", "Codigo de Folio", "Descripcion"
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
        
        
        // Se agregan las acciones de los radiobutton para la estetica
        rdbAgregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
	        	if (rdbAgregar.isSelected()) {
	        		lblNumeroODesc.setText("Descripcion: ");
	        		lblDescripcion.setVisible(false);
	        		panelVerDescripcion.setVisible(false);
	        		lblDarDescrip.setVisible(false);
	        		txtDescrip.setVisible(true);
	        		txtNumero.setVisible(false);
	        		btnAgregar.setVisible(true);
	        		BtnDarDescrip.setVisible(false);	 
	        	}
        	}
        });
        
        rdbDesc.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
	        	if (rdbDesc.isSelected()) {
	        		lblNumeroODesc.setText("Numero: ");
	        		lblDescripcion.setVisible(true);
	        		panelVerDescripcion.setVisible(true);
	        		lblDarDescrip.setVisible(true);
	        		txtDescrip.setVisible(false);
	        		txtNumero.setVisible(true);
	        		btnAgregar.setVisible(false);
	        		BtnDarDescrip.setVisible(true);
	        	}
        	}
        });
        
        // Boton para listar todas las revisiones de un folio
        btnListar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(txtCodigoBuscar.getText().isEmpty()) {
					// Muestro una marca de texto requerido en rojo sobre el campo que falta ser cargado
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("(*)");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					lblDarDescrip.setText("");
					
					// Si el codigo de folio esta vacio despliego este mensaje
					JOptionPane.showMessageDialog(null, "Debe ingresar el codigo del folio antes de listar sus revisiones.");
        		}else {
					// Dejo en blanco la marca de texto requerido
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					lblDarDescrip.setText("");
					
					// Pongo los campos textos en blanco luego de ejecutar la accion del boton
					txtCodF.setText("");
					//txtCodigoBuscar.setText("");
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
					JOptionPane.showMessageDialog(null, "Debe cargar los campos requeridos antes de agregar una nueva revision (*).");
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
					JOptionPane.showMessageDialog(null, "Debe cargar los campos requeridos antes de obtener su descripcion (*).");
				}else{
					// Dejo en blanco la marca de texto requerido
					lblFaltaCod.setText("");
					lblFaltaCodListar.setText("");
					lblFaltaDesc.setText("");
					lblFaltaNum.setText("");
					
					String codF = txtCodF.getText();
					String numR = txtNumero.getText();
					
					// Chequeo que lo que se haya ingresado como numero de revision
					if (numR.length() > 0){
						Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
					    Matcher matcher = pattern.matcher(numR);
					    boolean matchFound = matcher.find();
					    
					    if(matchFound){
					    	// Si el numero de revision ingresado se encuentra entre 0 y 9 lo parseo a entero
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
					    }else {
							// Si el numero de revision no es valido despliego este mensaje
							JOptionPane.showMessageDialog(null, "Debe ingresar un numero de revision valido para ver la descripcion del mismo.");
					    }
					}else{
						// Si el numero de revision esta vacio despliego este mensaje
						JOptionPane.showMessageDialog(null, "Debe ingresar un numero de revision para ver la descripcion del mismo.");
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
		} catch (RevisionException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		}
    }
}
