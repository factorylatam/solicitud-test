package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TitulosPendientesBean {
    private String ano;
    private String numTitulo;
    private String regPubId;
    private String oficRegId;
    private String areaRegistralId;
    /********** Titulos Pendientes RMC ********************/
    private long refNumTitu;
    private String nombreZona;
    private String nombreOficina;
    private long estadoTituId;
    private Date fechaPresentacion;
    private Date fechaVencimiento;
    private String estadoDescripcion;

    private String sede;
    private String fechaPres;
    private String fechaVenc;
    private String oficinas;
    private String descActo;
}
