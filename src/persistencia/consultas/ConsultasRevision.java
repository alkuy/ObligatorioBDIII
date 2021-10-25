package persistencia.consultas;

public class ConsultasRevision {

	
	// Insertar una revision
	public String insertarRevision() {
		return "Insert into Revisiones values(?,?,?);";
	}
	
	// Eliminar todas las revisiones de un folio
	public String EliminarRevisionesFolio() {
		return "delete from revisiones where codigoFolio = (?) ";
	}
	
	// Dar una descipcion de una revision dado su numero y codigo de folio
	// Tambien lo usaremos de manera auxiliar para chequear que un folio tenga registrada una revision
	public String DarDescripcion() {
		return "Select descripcion from Revisiones where numero = (?) and codigoFolio = (?);";
	}
	
	
	// Listar todas las revisiones de un folio dado, ordenado por numero de revision
	public String ListarRevisiones() {
		return "Select * from Revisiones where codigoFolio = (?);";
	}
	
	// Consulta auxiliar para obtener el ultimo numero de revision de un folio
	public String NumeroUltimaRevision() {
		return "Select numero from Revisiones where codigoFolio = (?) order by numero desc limit 1;";
	}
		
}
