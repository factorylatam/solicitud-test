package pe.gob.sunarp.app.solicitud.config;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AuthModel {
 
	private String userName;
	private String jti;
	private ArrayList<String> authorities;

}
