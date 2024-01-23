package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTransactionFromUserRowMapper implements RowMapper<TransaccionBean> {

    @Override
    public TransaccionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransaccionBean result = new TransaccionBean();
        result.setTransId(rs.getLong("TRANS_ID"));
        result.setFecHor(rs.getString("FEC_HOR"));
        result.setServicioId(rs.getInt("SERVICIO_ID"));
        result.setCuentaId(rs.getLong("CUENTA_ID"));
        result.setStrBusq(rs.getString("STR_BUSQ"));
        result.setCosto(rs.getBigDecimal("COSTO"));
        result.setSolicitudId(rs.getLong("SOLICITUD_ID"));
        result.setAaPubl(rs.getString("AA_PUBL"));
        result.setNuPubl(rs.getString("NU_PUBL"));
        result.setEstado(rs.getString("MENSAJE_USUARIO"));
        return result;
    }
}
