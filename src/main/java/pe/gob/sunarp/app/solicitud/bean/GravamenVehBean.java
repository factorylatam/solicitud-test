package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class GravamenVehBean {

    private long nsGravamen;
    private String anoTitu;
    private String numTitu;
    private String descTipoAfec;
    private String juzg;
    private String causAfec;
    private String juezAfec;
    private String secrAfect;
    private String juezDesc;
    private String secrDesc;
    private String fgEstado;
    private String numExpeAfec;
    private String numExpeDesc;
    private Date tsAfec;
    private Date tsExpeDesc;
    private Date tsProcDesc;
    private Date tsExpeAfec;
    private String fgPrenda;
    private BigDecimal nsActo;
    private String anoTituDesc;
    private String numTituDesc;
    private BigDecimal nsActoDesc;
    private String anoTituModi;
    private String numTituModi;
    private BigDecimal nsActoModi;

    private String fechAfec;
    private String fechExpeDesc;
    private String cantGravamen;

    private List<ParticipantesBean> listPartGravVigentes;
    private List<ParticipantesBean> listPartGravLevantados;
}
