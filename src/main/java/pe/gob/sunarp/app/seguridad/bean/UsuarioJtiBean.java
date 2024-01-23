package pe.gob.sunarp.app.seguridad.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioJtiBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String tipo;
    private Long idUser;
    private String email;
    private String tipoDoc;
    private String nroDoc;
    private String nombres;
    private String priApe;
    private String segApe;
    private String fecNac;
    private String nroCelular;
    private String userKeyId;   
    private String userSessionId;
}