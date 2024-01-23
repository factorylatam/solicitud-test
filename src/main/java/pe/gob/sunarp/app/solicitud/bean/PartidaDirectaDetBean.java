package pe.gob.sunarp.app.solicitud.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PartidaDirectaDetBean {
    /********************* VEHICULAR ***************************/
    private String fecIns = "";
    private String marca = "";
    private String descTipoVeh = "";
    private String descTipoComb = "";
    private String numSerie = "";
    private String numMotor = "";
    private String descCondVeh = "";
    private String modelo = "";
    private String descTipoCarr = "";
    private String color = "";
    private String anoFab = "";
    private String ancho = "";
    private String numAsientos = "";
    private String altura = "";
    private String numPuertas = "";
    private String numEjes = "";
    private String numRuedas = "";
    private String pesoSeco = "";
    private String pesoBruto = "";
    private String pesoUtil = "";
    private String numPasajeros = "";
    private String longitud = "";
    private String numCilindros = "";
    private String descCetico = "";
    private String nomOficina = "";
    private String estado = "";
    private String refNumPar="";
    private String numPartida = "";
    private String numPlaca = "";
    private String fechaPropi;
    private String regPubId;
    private String oficRegId;
    private String areaRegId;
    private String noVin;
    private String coCateg;
    private String anMode;
    private String noVers;
    private String poMotr;
    private String noClda;
    private String noForm;
    private String color1;
    private String color2;
    private String color3;
    private String descTipoUso;


    /*********** OPCIONES **************/
    private String optionCetico;
    /*********** PROPIETRARIOS *********************/
    private List<ParticipantesBean> listProp;
    /*********** GRAVAMENES *******************************/
    private List<GravamenVehBean> listGrav;
    private List<GravamenVehBean> listGravLev;
    /*********** MOTORES Y SERIES ANTERIORES *********************************/
    private List<VehiculoHistoricoBean> listParAnt;
    /************ PLACAS ANTERIORES ******************************************/
    private List<VehiculoPlacaAnteriorBean> listPlacasAnteri;
    /************ TITULOS PENDIENTES *****************************************/
    private List<TitulosPendientesBean> listTituPend;
    /************* PROPIETARIOS HISTORICOS VEH ********************************************/
    private List<ParticipantesBean> listPropHist;
    /************* ESTAO VEHICULO BAJA ******************************/
    private String optionBaja;

    private String nuevaPlaca;
}
