package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import pe.gob.sunarp.app.solicitud.bean.ObjetoSolicitudBean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetSolicitudByIdRowMapper implements RowMapper<ObjetoSolicitudBean> {

    @Override
    public ObjetoSolicitudBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ObjetoSolicitudBean.builder()
                .aaPubl(rs.getString("AA_PUBL"))
                .nuPubl(rs.getString("NU_PUBL"))
                .certificadoId(rs.getString("CERTIFICADO_ID"))
                .oficRegId(rs.getString("OFIC_REG_ID"))
                .regPubId(rs.getString("REG_PUB_ID"))
                .ccodDescarga(rs.getString("CCOD_DESCARGA"))
                .cflagCD(rs.getString("CFLAG_CD"))
                .cdeObs(rs.getString("CDE_OBS"))
                .build();
    }
}
