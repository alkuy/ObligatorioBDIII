package Excepciones;

public class PersistenciaException extends Exception{
	// Codigo generado automaticamente
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String mensaje;
	
	// Constructor
	public PersistenciaException(String msj){
		mensaje = msj;
	}

	// Selectora	
	public String getMensaje(){
		return mensaje;
	}
}