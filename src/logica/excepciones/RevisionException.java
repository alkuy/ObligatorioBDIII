package logica.excepciones;

public class RevisionException extends Exception{
	
	// Codigo generado automaticamente
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String mensaje;
	
	// Constructor
	public RevisionException(String msj){
		mensaje = msj;
	}

	// Selectora	
	public String getMensaje(){
		return mensaje;
	}

}
