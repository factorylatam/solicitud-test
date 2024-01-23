package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExtTbSubsanacionBean implements Serializable {
    private Long solicitudId;
    private Long nobsCodigo;
    private String dessub;
    private String fecrea;
    private String usrcre;
    private String usrsub;
    private LocalDateTime dateSub;
    private byte[] doc;
}
