package pe.gob.sunarp.app.solicitud.bean;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TIVeBean {
	private String codigoZona;
	private String codigoOficina;
	private String anioTitulo;
	private String numeroTitulo;
	private String numeroPlaca;
	private String codigoVerificacion;
	private String oficina;
	private String documento;
	private String tipo;
	
	public TIVeBean(String codigoZona, String codigoOficina, String anioTitulo,
			String numeroTitulo, String numeroPlaca, String codigoVerificacion,
			String oficina, String tipo) {
		super();
		this.codigoZona = codigoZona;
		this.codigoOficina = codigoOficina;
		this.anioTitulo = anioTitulo;
		this.numeroTitulo = numeroTitulo;
		this.numeroPlaca = numeroPlaca;
		this.codigoVerificacion = codigoVerificacion;
		this.oficina = oficina;
		this.tipo = tipo;
	}
	
}