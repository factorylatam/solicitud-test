package pe.gob.sunarp.app.solicitud.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TIVeInternoValidationResponse {
	private String codigoRespuesta;
	private String descripcionRespuesta;
	private String resultado;
}
