package presentacion.ventanas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
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
	private JTable TablaFolios_1;
	private ControladorVentanaFolios controlador;
	
	// Constructor
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
		btnAgregar.setBounds(297, 53, 105, 20);
		panelAgregarFolio.add(btnAgregar);
		btnAgregar.setVisible(false);
		
		JLabel lblCodigo = new JLabel("Codigo: ");
		lblCodigo.setBounds(30, 19, 75, 14);
		panelAgregarFolio.add(lblCodigo);
		
		JLabel lblCaratula = new JLabel("Caratula: ");
		lblCaratula.setBounds(30, 56, 75, 14);
		panelAgregarFolio.add(lblCaratula);
		lblCaratula.setVisible(false);
		
		JLabel lblPaginas = new JLabel("Paginas: ");
		lblPaginas.setBounds(30, 90, 75, 14);
		panelAgregarFolio.add(lblPaginas);
		lblPaginas.setVisible(false);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(111, 18, 134, 20);
		panelAgregarFolio.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtCaratula = new JTextField();
		txtCaratula.setColumns(10);
		txtCaratula.setBounds(111, 53, 134, 20);
		panelAgregarFolio.add(txtCaratula);
		txtCaratula.setVisible(false);
		
		txtPaginas = new JTextField();
		txtPaginas.setColumns(10);
		txtPaginas.setBounds(111, 87, 134, 20);
		panelAgregarFolio.add(txtPaginas);
		txtPaginas.setVisible(false);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(297, 53, 105, 20);
		panelAgregarFolio.add(btnBorrar);
		btnBorrar.setVisible(false);
		
		final JLabel lblFaltaCodigo = new JLabel("");
		lblFaltaCodigo.setForeground(Color.RED);
		lblFaltaCodigo.setBounds(246, 20, 46, 14);
		panelAgregarFolio.add(lblFaltaCodigo);
		
		final JLabel lblFaltaCaratula = new JLabel("");
		lblFaltaCaratula.setForeground(Color.RED);
		lblFaltaCaratula.setBounds(246, 55, 46, 14);
		panelAgregarFolio.add(lblFaltaCaratula);
		
		final JLabel lblFaltaPagina = new JLabel("");
		lblFaltaPagina.setForeground(Color.RED);
		lblFaltaPagina.setBounds(246, 89, 46, 14);
		panelAgregarFolio.add(lblFaltaPagina);
				
		JPanel panelListarFolios = new JPanel();
		panelListarFolios.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panelListarFolios.setBounds(10, 142, 672, 288);
		frmFolios.getContentPane().add(panelListarFolios);
		panelListarFolios.setLayout(null);
		
		JRadioButton rdbAgregar = new JRadioButton("Agregar folio");
		rdbAgregar.setBounds(297, 16, 131, 23);
		panelAgregarFolio.add(rdbAgregar);
		
		JRadioButton rdbBorrar = new JRadioButton("Borrar folio");
		rdbBorrar.setBounds(430, 15, 146, 23);
		panelAgregarFolio.add(rdbBorrar);
		
		ButtonGroup G = new ButtonGroup();
		G.add(rdbAgregar);	
		G.add(rdbBorrar);
	
		JButton btnListarFolios = new JButton("Listar Folios");
		btnListarFolios.setBounds(98, 11, 130, 23);
		panelListarFolios.add(btnListarFolios);
		
		JButton btnMasRevisado = new JButton("Mas revisado");
		btnMasRevisado.setBounds(446, 11, 130, 23);
		panelListarFolios.add(btnMasRevisado);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 652, 231);
		panelListarFolios.add(scrollPane);
		
		new JTable();
		TablaFolios_1 = new javax.swing.JTable() {
			// Codigo autogenerado
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column){
				return false;
				}
		};
		TablaFolios_1.setRowSelectionAllowed(false);
        TablaFolios_1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "Codigo", "Caratula", "Paginas"
                }
        ));
        scrollPane.setViewportView(TablaFolios_1);
        		
        // Se agregan las acciones de los radiobutton para la estetica
        rdbAgregar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
	        	if (rdbAgregar.isSelected()) {
	        		btnAgregar.setVisible(true);
	        		btnBorrar.setVisible(false);
	        		txtCaratula.setVisible(true);
	        		txtPaginas.setVisible(true);
	        		lblCaratula.setVisible(true);
	        		lblPaginas.setVisible(true);
	        		
	        		lblFaltaCodigo.setVisible(true);
					lblFaltaCaratula.setVisible(true);
					lblFaltaPagina.setVisible(true);
					
	        		lblFaltaCodigo.setText("");
					lblFaltaCaratula.setText("");
					lblFaltaPagina.setText("");
	        	}
        	}
        });
        
        rdbBorrar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
	        	if (rdbBorrar.isSelected()) {
	        		btnAgregar.setVisible(false);
	        		btnBorrar.setVisible(true);
	        		txtCaratula.setVisible(false);
	        		txtPaginas.setVisible(false);
	        		lblCaratula.setVisible(false);
	        		lblPaginas.setVisible(false);
	        		
	        		lblFaltaCodigo.setVisible(true);
					lblFaltaCaratula.setVisible(false);
					lblFaltaPagina.setVisible(false);
					
	        		lblFaltaCodigo.setText("");
					lblFaltaCaratula.setText("");
					lblFaltaPagina.setText("");
	        	}
        	}
        });
        
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
					
					// Pongo visible la marca de que se requieren datos
	        		lblFaltaCodigo.setVisible(true);
					lblFaltaCaratula.setVisible(true);
					lblFaltaPagina.setVisible(true);

					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe cargar todos los campos requeridos antes de agregar el folio (*).");
				}else{
					String cod = txtCodigo.getText();
					String cara =  txtCaratula.getText();
					String pagina = txtPaginas.getText();
					
					// Chequeo que lo que se haya ingresado como numero de revision
					Pattern pattern = Pattern.compile("^[0-9]+$", Pattern.CASE_INSENSITIVE);
				    Matcher matcher = pattern.matcher(pagina);
				    boolean matchFound = matcher.find();
					    
				    if(matchFound){// Si el numero de revision ingresado se encuentra entre 0 y 9 lo parseo a entero
					    int pag =  Integer.parseInt(pagina);
						
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
				    }else{
						// Si el numero de paginas no es valido despliego este mensaje
						JOptionPane.showMessageDialog(null, "Debe ingresar un numero de paginas valido para agregar el folio.");
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
					
					// Pongo visible la marca de que se requieren datos
	        		lblFaltaCodigo.setVisible(true);
					lblFaltaCaratula.setVisible(true);
					lblFaltaPagina.setVisible(true);
					
					// Si los campos no estan cargados despliego este mensaje en pantalla
					JOptionPane.showMessageDialog(null, "Debe ingresar un codigo de folio antes borrarlo (*).");
					
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
				// Pongo invisible la marca de que se requieren datos
        		lblFaltaCodigo.setVisible(false);
				lblFaltaCaratula.setVisible(false);
				lblFaltaPagina.setVisible(false);
				
				TablaListarFolioMasRevisado();
			}
		});
		
		
		// Boton para listar todos los folios
		btnListarFolios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pongo invisible la marca de que se requieren datos
        		lblFaltaCodigo.setVisible(false);
				lblFaltaCaratula.setVisible(false);
				lblFaltaPagina.setVisible(false);
				
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
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (PersistenciaException e) {
			JOptionPane.showMessageDialog(null, e.getMensaje());
		} catch (FolioException e) {
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
			
			// Manejar esto en otro lado
			if (VOF == null) {
				JOptionPane.showMessageDialog(null, "No existen folios con revisiones.");
			}
			else 
			{
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

