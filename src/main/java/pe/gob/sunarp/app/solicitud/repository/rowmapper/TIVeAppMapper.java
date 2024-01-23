package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.TIVeBean;

@SuppressWarnings("rawtypes")
public class TIVeAppMapper implements RowMapper{

    @Override
    public TIVeBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TIVeBean.builder()
                .codigoZona(rs.getString("REG_PUB_ID"))
                .codigoOficina(rs.getString("OFIC_REG_ID"))
                .anioTitulo(rs.getString("AA_TITU"))
                .numeroTitulo(rs.getString("NUM_TITU"))
                .numeroPlaca(rs.getString("NUM_PLACA"))
                .codigoVerificacion(rs.getString("CCOD_VERIFICACION"))
                .oficina(rs.getString("OFICINA"))
                .tipo(rs.getString("TIPO"))
                .build();
        
    }
}
