package logica.excepciones;

public class FolioException extends Exception{
	
	// Codigo generado automaticamente
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String mensaje;
	
	// Constructor
	public FolioException(String msj){
		mensaje = msj;
	}

	// Selectora	
	public String getMensaje(){
		return mensaje;
	}

}
