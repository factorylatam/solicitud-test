package pe.gob.sunarp.app.solicitud.repository.impl;

import oracle.jdbc.OracleTypes;

import static pe.gob.sunarp.app.solicitud.util.Constantes.APPSUNARP;
import static pe.gob.sunarp.app.solicitud.util.Constantes.JDBC;
import static pe.gob.sunarp.app.solicitud.util.Constantes.PKG_SOLICITUD;
import static pe.gob.sunarp.app.solicitud.util.Constantes.SP_GET_TRANSACTION;
import static pe.gob.sunarp.app.solicitud.util.Constantes.PKG_TA_USER_TIVE;
import static pe.gob.sunarp.app.solicitud.util.Constantes.SEL_TA_USER_TIVE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

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

import pe.gob.sunarp.app.solicitud.bean.TIVeBean;
import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionBean;
import pe.gob.sunarp.app.solicitud.exception.ExceptionProperties;
import pe.gob.sunarp.app.solicitud.repository.TIVeDao;
import pe.gob.sunarp.app.solicitud.repository.rowmapper.GetTransactionFromUserRowMapper;
import pe.gob.sunarp.app.solicitud.repository.rowmapper.TIVeAppMapper;

@Repository
public class TIVeDaoImpl implements TIVeDao{
    private static final Logger LOG = LoggerFactory.getLogger(TIVeDaoImpl.class);

    @Autowired
    private ApplicationContext context;

	@SuppressWarnings("unchecked")
	@Override
	public List<TIVeBean> getTIVeUserHistory(Long userId)  throws ExceptionBean {
       LOG.info("Inicio getTIVeUserHistory.");

        JdbcTemplate jdbcTemplate = context.getBean(JDBC, JdbcTemplate.class);

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(APPSUNARP)
                .withCatalogName(PKG_TA_USER_TIVE)
                .withProcedureName(SEL_TA_USER_TIVE)
                .declareParameters(new SqlOutParameter("pocur", OracleTypes.CURSOR, new TIVeAppMapper()),
                        new SqlParameter("in_ID_USER", OracleTypes.NUMBER));

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("in_ID_USER", userId);

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        List<TIVeBean> tiveList = (List<TIVeBean>) outParameters.get("pocur");

        LOG.info("resultado getTransactionFromUser = " + tiveList.size());
        return tiveList;

	}
		
    
}
