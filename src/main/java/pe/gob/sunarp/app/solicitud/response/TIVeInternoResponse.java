package pe.gob.sunarp.app.solicitud.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TIVeInternoResponse {
	private String code;
	private String errorMessage;
	private String documento;
}

