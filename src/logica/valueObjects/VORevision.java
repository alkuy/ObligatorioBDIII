package logica.valueObjects;

import java.io.Serializable;

public class VORevision implements Serializable{
	
	// Codigo para que sea serializable
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private int numero;
	private String descripcion;
	private String codFolio;

	public VORevision(int num, String desc, String codF){
		numero = num;
		descripcion = desc;
		codFolio = codF;
	}

	public int getNumero() {
		return numero;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getCodFolio() {
		return codFolio;
	}

}
