package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.TransaccionBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetTransactionByIdRowMapper implements RowMapper<TransaccionBean> {
    @Override
    public TransaccionBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        TransaccionBean result = new TransaccionBean();
        result.setTransId(rs.getLong("TRANS_ID"));
        result.setFecHor(rs.getString("FEC_HOR"));
        result.setServicioId(rs.getInt("SERVICIO_ID"));
        result.setCuentaId(rs.getLong("CUENTA_ID"));
        result.setStrBusq(rs.getString("STR_BUSQ"));
        result.setCosto(rs.getBigDecimal("COSTO"));
        result.setBlobJson(rs.getBytes("BLOB_JSON"));
        return result;
    }
}
