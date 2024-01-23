package pe.gob.sunarp.app.solicitud.repository.impl;

import com.google.gson.Gson;
import oracle.jdbc.OracleTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import pe.gob.sunarp.app.solicitud.bean.*;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.repository.SolicitudRepository;
import pe.gob.sunarp.app.solicitud.repository.rowmapper.*;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pe.gob.sunarp.app.solicitud.util.Constantes.*;

@Repository
public class SolicitudRepositoryImpl extends JdbcDaoSupport implements SolicitudRepository {

    private static final Logger LOG = LoggerFactory.getLogger(SolicitudRepositoryImpl.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ExceptionProperties exceptionProperties;

    @Autowired
    public void DataSource(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<TransaccionBean> getTransactionFromUser(String key) throws ExceptionBean {
        LOG.info("Inicio getTransactionFromUser.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_TRANSACTION)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new GetTransactionFromUserRowMapper()),
                        new SqlParameter("pin_user_key_id", OracleTypes.NVARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_user_key_id", key);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<TransaccionBean> list = (List<TransaccionBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getTransactionFromUser = " + list.size());
        return list;
    }

    @SuppressWarnings("unchecked")
	@Override
    public List<TransaccionBean> getTransactionFromUserNews(String key) throws ExceptionBean {
        LOG.info("Inicio getTransactionFromUser.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_TRANSACTION_NEWS)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new GetTransactionFromUserRowMapper()),
                        new SqlParameter("pin_user_key_id", OracleTypes.NVARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_user_key_id", key);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<TransaccionBean> list = (List<TransaccionBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getTransactionFromUser = " + list.size());
        return list;
    }

    @Override
    public String getSolicitudId(String transId) throws ExceptionBean {
        // LOG.info("Inicio getSolicitudId.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withFunctionName(SF_GET_SOLICITUD_ID)
                .declareParameters( new SqlOutParameter("pon_valor", OracleTypes.NUMBER),
                        new SqlParameter("piv_transId", OracleTypes.NVARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("piv_transId", transId);

        BigDecimal outParameters = simpleJdbcCall.executeFunction(java.math.BigDecimal.class, inputParameters);

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        // LOG.info("resultado getSolicitudId = " + outParameters);
        return outParameters.toString();
    }

    @Override
    public String getSolicitudIdNews(String transId) throws ExceptionBean {
        // LOG.info("Inicio getSolicitudId.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withFunctionName(SF_GET_SOLICITUD_ID)
                .declareParameters( new SqlOutParameter("pon_valor", OracleTypes.NUMBER),
                        new SqlParameter("piv_transId", OracleTypes.NVARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("piv_transId", transId);

        BigDecimal outParameters = simpleJdbcCall.executeFunction(java.math.BigDecimal.class, inputParameters);

        if(outParameters.longValue() <= 0) {
            return "";
        }
        // LOG.info("resultado getSolicitudId = " + outParameters);
        return outParameters.toString();
    }

    
    @SuppressWarnings("unchecked")
	@Override
    public ObjetoSolicitudBean getObjetoSolicitudBySolicitudId(String solicitudId) throws ExceptionBean {
        // LOG.info("Inicio getObjetoSolicitudBySolicitudId.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_OBJETO_SOLICITUD)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new GetSolicitudByIdRowMapper()),
                        new SqlParameter("pin_solicitudId", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitudId", Long.parseLong(solicitudId));

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<ObjetoSolicitudBean> list = (List<ObjetoSolicitudBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        // LOG.info("resultado getObjetoSolicitudBySolicitudId = " + list.size());
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public SolicitudBean getSolicitudById(String solicitudId) throws ExceptionBean {
        LOG.info("Inicio getSolicitudById.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_SOLICITUD)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new SolicitudRowMapper()),
                        new SqlParameter("pin_solicitudId", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitudId", Long.parseLong(solicitudId));

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<SolicitudBean> list = (List<SolicitudBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getSolicitudById = " + list.size());
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public SolicitudDataBean getDataSolicitud(String solicitudId) throws ExceptionBean {
        LOG.info("Inicio getDataSolicitud.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_DATA_SOLICITUD)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new SolicitudDataRowMapper()),
                        new SqlParameter("pin_solicitudId", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitudId", Long.parseLong(solicitudId));

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<SolicitudDataBean> list = (List<SolicitudDataBean>) outParameters.get("pocur");

        if(list == null || list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getDataSolicitud = " + list.size());
        return list.get(0);
    }

    @SuppressWarnings("unchecked")
	@Override
    public SolicitudDataBean getDataSolicitudNews(String solicitudId) throws ExceptionBean {
        LOG.info("Inicio getDataSolicitud.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_DATA_SOLICITUD)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new SolicitudDataRowMapper()),
                        new SqlParameter("pin_solicitudId", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitudId", Long.parseLong(solicitudId));

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<SolicitudDataBean> list = (List<SolicitudDataBean>) outParameters.get("pocur");

        if(list == null || list.isEmpty()) {
            return null;
        }
        LOG.info("resultado getDataSolicitud = " + list.size());
        return list.get(0);
    }

	@SuppressWarnings("unchecked")
	@Override
	public SolicitudDataBean buscarSolicitudxNroPublicidad(String anho, long numero) throws ExceptionBean {
        LOG.info("Inicio buscarSolicitudxNroPublicidad.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_DATA_SOLICITUD_PUBL)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new SolicitudDataRowMapper()),
                        new SqlParameter("pic_aa_publ", OracleTypes.VARCHAR),
                        new SqlParameter("pin_nu_publ", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pic_aa_publ", anho)
                .addValue("pin_nu_publ", numero);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<SolicitudDataBean> list = (List<SolicitudDataBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado buscarSolicitudxNroPublicidad = " + list.size());
        return list.get(0);		
		
		
	}
	
	
	
    @Override
    public void saveSgmtSolicitud(SgmtSolicitudBean sgmtSolicitudBean) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_SGMT_SOLICITUD)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_estado_inicial", OracleTypes.VARCHAR),
                        new SqlParameter("pin_estado_final", OracleTypes.VARCHAR),
                        new SqlParameter("pin_ts_movimiento", OracleTypes.DATE),
                        new SqlParameter("pin_usr_movimiento", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_rev", OracleTypes.VARCHAR),
                        new SqlParameter("pin_comentario", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_modi", OracleTypes.VARCHAR),
                        new SqlParameter("pin_ts_modi", OracleTypes.DATE),
                        new SqlParameter("pin_solicitante", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", Long.parseLong(sgmtSolicitudBean.getSolicitudId()))
                .addValue("pin_estado_inicial", sgmtSolicitudBean.getEstadoInicial())
                .addValue("pin_estado_final", sgmtSolicitudBean.getEstadoFinal())
                .addValue("pin_ts_movimiento", sgmtSolicitudBean.getTsMovimiento())
                .addValue("pin_usr_movimiento", sgmtSolicitudBean.getUsrMovimiento())
                .addValue("pin_flg_rev", sgmtSolicitudBean.getFlgrev())
                .addValue("pin_comentario", sgmtSolicitudBean.getComentario())
                .addValue("pin_usr_modi", sgmtSolicitudBean.getUsrModi())
                .addValue("pin_ts_modi", sgmtSolicitudBean.getTsModi())
                .addValue("pin_solicitante", sgmtSolicitudBean.getSolicitante());
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public void updateSolicitud(SolicitudBean solicitud) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_UPDATE_ESTADO_SOLICITUD)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_estado", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_liq", OracleTypes.VARCHAR),
                        new SqlParameter("pin_csol_estado", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_obs", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_rec", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_aclar", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_modi", OracleTypes.VARCHAR),
                        new SqlParameter("pin_ts_modi", OracleTypes.DATE));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", Long.parseLong(solicitud.getSolicitudId()))
                .addValue("pin_estado", solicitud.getEstado())
                .addValue("pin_flg_liq", solicitud.getCsolFlgliq())
                .addValue("pin_csol_estado", solicitud.getCsolEstado())
                .addValue("pin_flg_obs", solicitud.getCsolFlgobs())
                .addValue("pin_flg_rec", solicitud.getCsolFlgrec())
                .addValue("pin_flg_aclar", solicitud.getCsolFlgAclar())
                .addValue("pin_usr_modi", solicitud.getUsrModi())
                .addValue("pin_ts_modi", solicitud.getTsModi());
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public void updateSolicitudXCarga(long solicitudId, String estado, String rol) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_UPDATE_SOLICITUD_X_CARGA)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_estado", OracleTypes.VARCHAR),
                        new SqlParameter("pin_rol", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", solicitudId)
                .addValue("pin_estado", estado)
                .addValue("pin_rol", rol);
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public void updateDocEmitidoAclaratoria(long solicitudId, String flgNuevo, String flgAntiguo) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_UPDATE_FLG_DOCU_EMTIDO)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_flg_nuevo", OracleTypes.VARCHAR),
                        new SqlParameter("pin_flg_antiguo", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", solicitudId)
                .addValue("pin_flg_nuevo", flgNuevo)
                .addValue("pin_flg_antiguo", flgAntiguo);
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public void saveExtTbSubsanacion(ExtTbSubsanacionBean extTbSubsanacionBean) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_EXT_TB_SUBSANACION)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_dessub", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_crea", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_sub", OracleTypes.VARCHAR),
                        new SqlParameter("pin_obs_sub", OracleTypes.DATE),
                        new SqlParameter("pin_doc", OracleTypes.BLOB));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", extTbSubsanacionBean.getSolicitudId())
                .addValue("pin_dessub", extTbSubsanacionBean.getDessub())
                .addValue("pin_usr_crea", extTbSubsanacionBean.getUsrcre())
                .addValue("pin_usr_sub", extTbSubsanacionBean.getUsrsub())
                .addValue("pin_obs_sub", extTbSubsanacionBean.getDateSub())
                .addValue("pin_doc", extTbSubsanacionBean.getDoc());
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public void solicitarDesistimiento(long solicitudId, String usr) throws ExceptionBean {
        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_DESISTIR_SOLICITUD)
                .declareParameters(new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_usr_modi", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", solicitudId)
                .addValue("pin_usr_modi", usr);
        simpleJdbcCall.execute(inputParameters);
    }

    @Override
    public List<String> getCodRgst(String anio, long nroPubl) throws ExceptionBean {
        LOG.info("Inicio getCodRgst.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(USER1)
                .withCatalogName(PKG_DETA_ATEN_PVPUB)
                .withProcedureName(PRC_GET_CO_SERV_RGST)
                .declareParameters(new SqlParameter("PIV_AA_PUBL", OracleTypes.CHAR),
                        new SqlParameter("PIV_NU_PUBL", OracleTypes.NUMBER),
                        new SqlOutParameter("POC_CO_SERV_RGST", OracleTypes.VARCHAR),
                        new SqlOutParameter("POC_INDICADOR", OracleTypes.VARCHAR),
                        new SqlOutParameter("POV_C_CODERR", OracleTypes.VARCHAR),
                        new SqlOutParameter("POV_C_MSGERR", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                        .addValue("PIV_AA_PUBL", anio)
                        .addValue("PIV_NU_PUBL", nroPubl);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<String> lista = new ArrayList<>();
        lista.add(0, String.valueOf(outParameters.get("POC_CO_SERV_RGST")) == null ?
                BLANK : String.valueOf(outParameters.get("POC_CO_SERV_RGST")));
        lista.add(1, String.valueOf(outParameters.get("POC_INDICADOR")) == null ?
                BLANK : String.valueOf(outParameters.get("POC_INDICADOR")));
        lista.add(2, String.valueOf(outParameters.get("POV_C_CODERR")) == null ?
                BLANK : String.valueOf(outParameters.get("POV_C_CODERR")));
        lista.add(3, String.valueOf(outParameters.get("POV_C_MSGERR")) == null ?
                BLANK : String.valueOf(outParameters.get("POV_C_MSGERR")));

        LOG.info("resultado getCodRgst = " + lista.size());
        return lista;
    }

    @Override
    public long saveMovimiento(MovimientoBean movimientoBean) throws ExceptionBean {
        LOG.info("Inicio saveMovimiento.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_MOVIMIENTO)
                .declareParameters( new SqlParameter("pin_fec_hor", OracleTypes.DATE),
                        new SqlParameter("pin_tpo_mov", OracleTypes.VARCHAR),
                        new SqlParameter("pin_monto_fin", OracleTypes.NUMBER),
                        new SqlParameter("pin_fg_asig", OracleTypes.VARCHAR),
                        new SqlParameter("pin_linea_prepago_id", OracleTypes.VARCHAR),
                        new SqlOutParameter("pin_movimiento_id", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_fec_hor", movimientoBean.getFecHor())
                .addValue("pin_tpo_mov", movimientoBean.getTpoMov())
                .addValue("pin_monto_fin", movimientoBean.getMontoFin())
                .addValue("pin_fg_asig", movimientoBean.getFgAsig())
                .addValue("pin_linea_prepago_id", movimientoBean.getLineaPrepago());

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        BigDecimal outParameters = (BigDecimal) out.get("pin_movimiento_id");

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado saveMovimiento = " + outParameters.longValue());
        return outParameters.longValue();
    }

    @Override
    public long getPersonaIdByLineaPrepagoId(long lineaPrepagoId) throws ExceptionBean {
        LOG.info("Inicio getPersonaIdByLineaPrepagoId.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withFunctionName(SF_GET_PERSONA_ID)
                .declareParameters( new SqlParameter("pin_linea_prepago_id", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_linea_prepago_id", lineaPrepagoId);

        BigDecimal outParameters = simpleJdbcCall.executeFunction(java.math.BigDecimal.class, inputParameters);

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getPersonaIdByLineaPrepagoId = " + outParameters);
        return outParameters.longValue();
    }

    @Override
    public long saveAbono(AbonoBean abonoBean) throws ExceptionBean {
        LOG.info("Inicio saveAbono.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_ABONO)
                .declareParameters( new SqlParameter("pin_tipo_abono", OracleTypes.VARCHAR),
                        new SqlParameter("pin_tipo_vent", OracleTypes.VARCHAR),
                        new SqlParameter("pin_tpo_pag_vent", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_caja", OracleTypes.VARCHAR),
                        new SqlParameter("pin_tipo_usr", OracleTypes.VARCHAR),
                        new SqlParameter("pin_monto", OracleTypes.NUMBER),
                        new SqlParameter("pin_movimiento_id", OracleTypes.VARCHAR),
                        new SqlParameter("pin_ofic_reg", OracleTypes.VARCHAR),
                        new SqlParameter("pin_reg_pub", OracleTypes.VARCHAR),
                        new SqlParameter("pin_fg_cierre", OracleTypes.VARCHAR),
                        new SqlParameter("pin_persona_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_ts_crea", OracleTypes.DATE),
                        new SqlParameter("pin_ts_modi", OracleTypes.DATE),
                        new SqlParameter("pin_estado", OracleTypes.VARCHAR),
                        new SqlOutParameter("pout_abono_id", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_tipo_abono", abonoBean.getTipoAbono())
                .addValue("pin_tipo_vent", abonoBean.getTipoVent())
                .addValue("pin_tpo_pag_vent", abonoBean.getTpoPagVent())
                .addValue("pin_usr_caja", abonoBean.getUsrCaja())
                .addValue("pin_tipo_usr", abonoBean.getTipoUsr())
                .addValue("pin_monto", abonoBean.getMonto())
                .addValue("pin_movimiento_id", abonoBean.getMovimientoId())
                .addValue("pin_ofic_reg", abonoBean.getOficRegId())
                .addValue("pin_reg_pub", abonoBean.getRegPubId())
                .addValue("pin_fg_cierre", abonoBean.getFgCierre())
                .addValue("pin_persona_id", abonoBean.getPersonaId())
                .addValue("pin_ts_crea", abonoBean.getTsCrea())
                .addValue("pin_ts_modi", abonoBean.getTsModi())
                .addValue("pin_estado", abonoBean.getEstado());

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        BigDecimal outParameters = (BigDecimal) out.get("pout_abono_id");

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado saveAbono = " + outParameters.longValue());
        return outParameters.longValue();
    }

    @Override
    public long saveComprobante(ComprobanteBean comprobanteBean) throws ExceptionBean {
        LOG.info("Inicio saveComprobante.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_COMPROBANTE)
                .declareParameters( new SqlParameter("pin_abono_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_monto", OracleTypes.NUMBER),
                        new SqlParameter("pin_estado", OracleTypes.VARCHAR),
                        new SqlOutParameter("pout_comprobante_id", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_abono_id", comprobanteBean.getAbonoId())
                .addValue("pin_monto", comprobanteBean.getMonto())
                .addValue("pin_estado", comprobanteBean.getEstado());

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        BigDecimal outParameters = (BigDecimal) out.get("pout_comprobante_id");

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado saveComprobante = " + outParameters.longValue());
        return outParameters.longValue();
    }

    @Override
    public long savePagoSolicitud(PagoSolicitudBean pagoSolicitudBean) throws ExceptionBean {
        LOG.info("Inicio savePagoSolicitud.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_PAGO_SOLICITUD)
                .declareParameters( new SqlParameter("pin_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_tpo_pago", OracleTypes.VARCHAR),
                        new SqlParameter("pin_abono_id", OracleTypes.NUMBER),
                        new SqlParameter("pin_monto", OracleTypes.NUMBER),
                        new SqlParameter("pin_ts_crea", OracleTypes.DATE),
                        new SqlParameter("pin_ts_modi", OracleTypes.DATE),
                        new SqlParameter("pin_usr_crea", OracleTypes.VARCHAR),
                        new SqlParameter("pin_usr_modi", OracleTypes.VARCHAR),
                        new SqlParameter("pin_cpasol_flg", OracleTypes.VARCHAR),
                        new SqlOutParameter("pout_pago_solicitud_id", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_solicitud_id", pagoSolicitudBean.getSolicitudId())
                .addValue("pin_tpo_pago", pagoSolicitudBean.getTpoPago())
                .addValue("pin_abono_id", pagoSolicitudBean.getAbonoId())
                .addValue("pin_monto", pagoSolicitudBean.getMonto())
                .addValue("pin_ts_crea", pagoSolicitudBean.getTsCrea())
                .addValue("pin_ts_modi", pagoSolicitudBean.getTsModi())
                .addValue("pin_usr_crea", pagoSolicitudBean.getUsrCrea())
                .addValue("pin_usr_modi", pagoSolicitudBean.getUsrModi())
                .addValue("pin_cpasol_flg", pagoSolicitudBean.getCpasolFlg());

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        BigDecimal outParameters = (BigDecimal) out.get("pout_pago_solicitud_id");

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado savePagoSolicitud = " + outParameters.longValue());
        return outParameters.longValue();
    }

    @Override
    public ReciDetaAtenNacBean getReciDetaAtenNac(String aaPubl, long nuPubl) throws ExceptionBean {
        LOG.info("Inicio getReciDetaAtenNac.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_RECI_DETA_ATEN_NAC)
                .declareParameters(new SqlParameter("pin_aa_publ", OracleTypes.VARCHAR),
                        new SqlParameter("pin_nu_publ", OracleTypes.NUMBER),
                        new SqlOutParameter("pocur", OracleTypes.CURSOR, new ReciDetaAtenNacRowMapper()));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_aa_publ", aaPubl)
                .addValue("pin_nu_publ", nuPubl);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<ReciDetaAtenNacBean> list = (List<ReciDetaAtenNacBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getReciDetaAtenNac = " + list.size());
        return list.get(0);
    }

    @Override
    public long getCodServicioLiquidacion(String coTipoRgst) throws ExceptionBean {
        LOG.info("Inicio getCodServicioLiquidacion.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withFunctionName(SF_GET_COD_SERV_LIQUIDACION)
                .declareParameters( new SqlParameter("pin_cod_tipo_rgst", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_cod_tipo_rgst", coTipoRgst);

        BigDecimal outParameters = simpleJdbcCall.executeFunction(java.math.BigDecimal.class, inputParameters);

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getCodServicioLiquidacion = " + outParameters);
        return outParameters.longValue();
    }

    @Override
    public long saveReciDetaAtenNac(ReciDetaAtenNacBean reciDetaAtenNacBean) throws ExceptionBean {
        LOG.info("Inicio saveReciDetaAtenNac.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_INSERT_RECI_DETA_ATEN_NAC)
                .declareParameters( new SqlParameter("v_aa_reci_tmp", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_zona_regi_dest", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_ofic_regi_dest", OracleTypes.VARCHAR),
                        new SqlParameter("v_mo_tota_serv", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_tipo_serv", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_tipo_rgst", OracleTypes.VARCHAR),
                        new SqlParameter("v_in_resu_asie", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_part_pred", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_asie_sele", OracleTypes.VARCHAR),
                        new SqlParameter("v_ti_docu_iden", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_docu_iden", OracleTypes.VARCHAR),
                        new SqlParameter("v_ape_pate", OracleTypes.VARCHAR),
                        new SqlParameter("v_ape_mate", OracleTypes.VARCHAR),
                        new SqlParameter("v_no_pers", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_libr", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_part", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_fich", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_tomo", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_foli", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_orig_part", OracleTypes.VARCHAR),
                        new SqlParameter("v_im_pagi", OracleTypes.VARCHAR),
                        new SqlParameter("v_no_pagi", OracleTypes.NUMBER),
                        new SqlParameter("v_to_pagi", OracleTypes.NUMBER),
                        new SqlParameter("v_id_part", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_part_rpv", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_plac", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_zona_regi_part", OracleTypes.VARCHAR),
                        new SqlParameter("v_co_ofic_regi_part", OracleTypes.VARCHAR),
                        new SqlParameter("v_in_ambt_naci", OracleTypes.VARCHAR),
                        new SqlParameter("v_ca_copi", OracleTypes.NUMBER),
                        new SqlParameter("v_co_serv_rgst", OracleTypes.NUMBER),
                        new SqlParameter("v_in_estd", OracleTypes.VARCHAR),
                        new SqlParameter("v_ts_crea", OracleTypes.DATE),
                        new SqlParameter("v_solicitud_id", OracleTypes.NUMBER),
                        new SqlParameter("v_im_pagi_clob", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_asie_sele_clob", OracleTypes.VARCHAR),
                        new SqlParameter("v_aa_publ", OracleTypes.VARCHAR),
                        new SqlParameter("v_nu_publ", OracleTypes.NUMBER),
                        new SqlParameter("v_in_tipo_soli", OracleTypes.VARCHAR),
                        new SqlOutParameter("v_reci_deta_aten_nac", OracleTypes.NUMBER)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("v_aa_reci_tmp", reciDetaAtenNacBean.getAaReciTmp())
                .addValue("v_co_zona_regi_dest", reciDetaAtenNacBean.getCoZonaRegiDest())
                .addValue("v_co_ofic_regi_dest", reciDetaAtenNacBean.getCoOficRegiDest())
                .addValue("v_mo_tota_serv", reciDetaAtenNacBean.getSubtotal())
                .addValue("v_co_tipo_serv", reciDetaAtenNacBean.getCoTipoServ())
                .addValue("v_co_tipo_rgst", reciDetaAtenNacBean.getCoTipoRgst())
                .addValue("v_in_resu_asie", reciDetaAtenNacBean.getInResuAsie())
                .addValue("v_nu_part_pred", reciDetaAtenNacBean.getNuPartPred())
                .addValue("v_nu_asie_sele", reciDetaAtenNacBean.getNuAsieSele())
                .addValue("v_ti_docu_iden", reciDetaAtenNacBean.getTiDocuIden())
                .addValue("v_nu_docu_iden", reciDetaAtenNacBean.getNuDocuIden())
                .addValue("v_ape_pate", reciDetaAtenNacBean.getApePate())
                .addValue("v_ape_mate", reciDetaAtenNacBean.getApeMate())
                .addValue("v_no_pers", reciDetaAtenNacBean.getNoPers())
                .addValue("v_co_libr", reciDetaAtenNacBean.getCoLibr())
                .addValue("v_nu_part", reciDetaAtenNacBean.getNuPart())
                .addValue("v_nu_fich", reciDetaAtenNacBean.getNuFich())
                .addValue("v_nu_tomo", reciDetaAtenNacBean.getNuTomo())
                .addValue("v_nu_foli", reciDetaAtenNacBean.getNuFoli())
                .addValue("v_nu_orig_part", reciDetaAtenNacBean.getNuOrigPart())
                .addValue("v_im_pagi", reciDetaAtenNacBean.getImPagi())
                .addValue("v_no_pagi", reciDetaAtenNacBean.getNoPagi())
                .addValue("v_to_pagi", reciDetaAtenNacBean.getToPagi())
                .addValue("v_id_part", reciDetaAtenNacBean.getIdPart())
                .addValue("v_nu_part_rpv", reciDetaAtenNacBean.getNuPartRpv())
                .addValue("v_nu_plac", reciDetaAtenNacBean.getNuPlac())
                .addValue("v_co_zona_regi_part", reciDetaAtenNacBean.getCoZonaRegiPart())
                .addValue("v_co_ofic_regi_part", reciDetaAtenNacBean.getCoOficRegiPart())
                .addValue("v_in_ambt_naci", reciDetaAtenNacBean.getInAmbtNaci())
                .addValue("v_ca_copi", reciDetaAtenNacBean.getCaCopi())
                .addValue("v_co_serv_rgst", reciDetaAtenNacBean.getCoServRgst())
                .addValue("v_in_estd", reciDetaAtenNacBean.getInEstd())
                .addValue("v_ts_crea", reciDetaAtenNacBean.getTsCrea())
                .addValue("v_solicitud_id", reciDetaAtenNacBean.getSolicitudId())
                .addValue("v_im_pagi_clob", reciDetaAtenNacBean.getImPagiClob())
                .addValue("v_nu_asie_sele_clob", reciDetaAtenNacBean.getNuAsieSeleClob())
                .addValue("v_aa_publ", reciDetaAtenNacBean.getAnPubl())
                .addValue("v_nu_publ", reciDetaAtenNacBean.getNuPubl())
                .addValue("v_in_tipo_soli", reciDetaAtenNacBean.getInTipoSoli());

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        BigDecimal outParameters = (BigDecimal) out.get("v_reci_deta_aten_nac");

        if(outParameters.longValue() <= 0) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado saveReciDetaAtenNac = " + outParameters.longValue());
        return outParameters.longValue();
    }

    @Override
    public TransaccionBean getTransactionById(String transId) throws ExceptionBean {
        LOG.info("Inicio getTransactionById.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_SOLICITUD)
                .withProcedureName(SP_GET_TRANSACTION_BY_ID)
                .declareParameters(new SqlParameter("pin_transId", OracleTypes.NVARCHAR),
                        new SqlOutParameter("pocur", OracleTypes.CURSOR, new GetTransactionByIdRowMapper()));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("pin_transId", transId);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<TransaccionBean> list = (List<TransaccionBean>) outParameters.get("pocur");

        if(list.isEmpty()) {
            throw exceptionProperties.getNotFound();
        }
        LOG.info("resultado getTransactionById = " + list.size());
        return list.get(0);
    }

    @Override
    public long savePago(RegularizaPagoBean regularizaPagoBean) throws ExceptionBean {
        LOG.info("Inicio savePago.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_TA_USER_PAYMENT)
                .withProcedureName(INS_TA_USER_PAYMENT)
                .declareParameters( new SqlParameter("in_ID_USER", OracleTypes.NUMBER),
                        new SqlParameter("in_ID_UNICO", OracleTypes.VARCHAR),
                        new SqlParameter("in_PAN", OracleTypes.VARCHAR),
                        new SqlParameter("in_IMP_AUTORIZADO", OracleTypes.NUMBER),
                        new SqlParameter("in_DSC_COD_ACCION", OracleTypes.VARCHAR),
                        new SqlParameter("in_COD_AUTORIZA", OracleTypes.VARCHAR),
                        new SqlParameter("in_CODTIENDA", OracleTypes.VARCHAR),
                        new SqlParameter("in_NUMORDEN", OracleTypes.VARCHAR),
                        new SqlParameter("in_CODACCION", OracleTypes.VARCHAR),
                        new SqlParameter("in_FECHAYHORA_TX", OracleTypes.VARCHAR),
                        new SqlParameter("in_NOM_EMISOR", OracleTypes.VARCHAR),
                        new SqlParameter("in_ORI_TARJETA", OracleTypes.CHAR),
                        new SqlParameter("in_RESPUESTA", OracleTypes.CHAR),
                        new SqlParameter("in_USER_KEY_ID", OracleTypes.VARCHAR),
                        new SqlParameter("in_TRANS_ID", OracleTypes.NUMBER),
                        new SqlParameter("in_ETICKET", OracleTypes.VARCHAR),
                        new SqlParameter("in_CONCEPTO", OracleTypes.VARCHAR),
                        new SqlParameter("in_VISANETRESPONSE", OracleTypes.VARCHAR));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("in_ID_USER", regularizaPagoBean.getVisanetResponse().getIdUser())
                .addValue("in_ID_UNICO", regularizaPagoBean.getVisanetResponse().getIdUnico())
                .addValue("in_PAN", regularizaPagoBean.getVisanetResponse().getPan())
                .addValue("in_IMP_AUTORIZADO", regularizaPagoBean.getCostoTotal().intValue())
                .addValue("in_DSC_COD_ACCION", regularizaPagoBean.getVisanetResponse().getDscCodAccion())
                .addValue("in_COD_AUTORIZA", regularizaPagoBean.getVisanetResponse().getCodAutoriza())
                .addValue("in_CODTIENDA", regularizaPagoBean.getVisanetResponse().getCodtienda())
                .addValue("in_NUMORDEN", regularizaPagoBean.getVisanetResponse().getNumOrden())
                .addValue("in_CODACCION", regularizaPagoBean.getVisanetResponse().getCodAccion())
                .addValue("in_FECHAYHORA_TX", regularizaPagoBean.getVisanetResponse().getFechaYhoraTx())
                .addValue("in_NOM_EMISOR", regularizaPagoBean.getVisanetResponse().getNomEmisor())
                .addValue("in_ORI_TARJETA", regularizaPagoBean.getVisanetResponse().getOriTarjeta())
                .addValue("in_RESPUESTA", regularizaPagoBean.getVisanetResponse().getRespuesta())
                .addValue("in_USER_KEY_ID", regularizaPagoBean.getUsrKeyId())
                .addValue("in_TRANS_ID", regularizaPagoBean.getTransId())
                .addValue("in_ETICKET", regularizaPagoBean.getVisanetResponse().getEticket())
                .addValue("in_CONCEPTO", regularizaPagoBean.getVisanetResponse().getConcepto())
                .addValue("in_VISANETRESPONSE", new Gson().toJson(regularizaPagoBean.getVisanetResponse(),
                        VisanetResponseBean.class));

        simpleJdbcCall.execute(inputParameters);

        LOG.info("resultado savePago.");
        return 1;
    }
    
    
}