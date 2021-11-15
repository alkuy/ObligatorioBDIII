package logica;

import java.io.Serializable;

public class Revision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numero;
	private String descripcion;
	
	public Revision (int num, String desc)
	{
		numero = num;
		descripcion = desc;
	}
	
	public int getNumero()
	{
		return numero;
	}
	
	public String getDescripcion ()
	{
		return descripcion;
	}
}
