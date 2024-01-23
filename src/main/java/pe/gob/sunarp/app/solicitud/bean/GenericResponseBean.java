package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GenericResponseBean implements Serializable {
    private String code;
    private String errorMessage;
}
