package pe.gob.sunarp.app.solicitud.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter 
@Setter
@NoArgsConstructor
public class TIVeInternoRequest {
	private String codigoZona;
	private String codigoOficina;
	private String anioTitulo;
	private String numeroTitulo;
	private String numeroPlaca;
	private String codigoVerificacion;
	private String codCarpeta = "016";
	private String tipoDocumento = "0";
	private String user;

	public TIVeInternoRequest(String codigoZona, String codigoOficina,
			String anioTitulo, String numeroTitulo, String numeroPlaca,
			String codigoVerificacion, String tipo) {
		super();
		this.codigoZona = codigoZona;
		this.codigoOficina = codigoOficina;
		this.anioTitulo = anioTitulo;
		this.numeroTitulo = numeroTitulo;
		this.numeroPlaca = numeroPlaca;
		this.codigoVerificacion = codigoVerificacion;
		
		//"T" para titulos, "P" para publicidades
		if(tipo.equals("T")){
			this.user="app";
		}
		else if(tipo.equals("P")){
			this.user = "publicidad";
		}
	}
}
