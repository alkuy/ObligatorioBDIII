package logica.valueObjects;

import java.io.Serializable;

public class VOFolioMaxRev extends VOFolio implements Serializable {

		// Codigo para que sea serializable
		private static final long serialVersionUID = 1L;
		
		// Atributos
		private int cantRevisiones;
		
		public VOFolioMaxRev(String cod, String car, int pag, int cantRev){
			super(cod, car, pag);
			cantRevisiones = cantRev;
		}

		public int getCantRevisiones() {
			return cantRevisiones;
		}
}
