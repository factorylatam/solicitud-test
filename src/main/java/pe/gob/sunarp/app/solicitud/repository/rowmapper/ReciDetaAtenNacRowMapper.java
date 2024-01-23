package pe.gob.sunarp.app.solicitud.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.sunarp.app.solicitud.bean.ReciDetaAtenNacBean;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static pe.gob.sunarp.app.solicitud.util.Constantes.BD_DATE;

public class ReciDetaAtenNacRowMapper implements RowMapper<ReciDetaAtenNacBean> {

    @Override
    public ReciDetaAtenNacBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ReciDetaAtenNacBean.builder()
                .reciDetaAteNacId(rs.getLong("RECI_DETA_ATEN_NAC"))
                .aaReciTmp(rs.getString("AA_RECI_TMP"))
                .coZonaRegiDest(rs.getString("CO_ZONA_REGI_DEST"))
                .coOficRegiDest(rs.getString("CO_OFIC_REGI_DEST"))
                .subtotal(rs.getBigDecimal("MO_TOTA_SERV"))
                .coTipoServ(rs.getString("CO_TIPO_SERV"))
                .coTipoRgst(rs.getString("CO_TIPO_RGST"))
                .inResuAsie(rs.getString("IN_RESU_ASIE"))
                .nuPartPred(rs.getString("NU_PART_PRED"))
                .nuAsieSele(rs.getString("NU_ASIE_SELE"))
                .tiDocuIden(rs.getString("TI_DOCU_IDEN"))
                .nuDocuIden(rs.getString("NU_DOCU_IDEN"))
                .apePate(rs.getString("APE_PATE"))
                .apeMate(rs.getString("APE_MATE"))
                .noPers(rs.getString("NO_PERS"))
                .coLibr(rs.getString("CO_LIBR"))
                .nuPart(rs.getString("NU_PART"))
                .nuFich(rs.getString("NU_FICH"))
                .nuTomo(rs.getString("NU_TOMO"))
                .nuFoli(rs.getString("NU_FOLI"))
                .nuOrigPart(rs.getString("NU_ORIG_PART"))
                .imPagi(rs.getString("IM_PAGI"))
                .noPagi(rs.getBigDecimal("NO_PAGI"))
                .toPagi(rs.getBigDecimal("TO_PAGI"))
                .idPart(rs.getString("ID_PART"))
                .nuPartRpv(rs.getString("NU_PART_RPV"))
                .nuPlac(rs.getString("NU_PLAC"))
                .coZonaRegiPart(rs.getString("CO_ZONA_REGI_PART"))
                .coOficRegiPart(rs.getString("CO_OFIC_REGI_PART"))
                .inAmbtNaci(rs.getString("IN_AMBT_NACI"))
                .caCopi(rs.getBigDecimal("CA_COPI"))
                .coServRgst(rs.getBigDecimal("CO_SERV_RGST"))
                .inEstd(rs.getString("IN_ESTD"))
                .tsCrea(this.convertToLocalDate(rs.getString("TS_CREA")))
                .solicitudId(rs.getLong("SOLICITUD_ID"))
                .imPagiClob(rs.getString("IM_PAGI_CLOB"))
                .nuAsieSeleClob(rs.getString("NU_ASIE_SELE_CLOB"))
                .anPubl(rs.getString("AA_PUBL"))
                .nuPubl(rs.getLong("NU_PUBL"))
                .inTipoSoli(rs.getString("IN_TIPO_SOLI"))
                .build();

    }

    private LocalDateTime convertToLocalDate(String dateToConvert) {
        if(Optional.ofNullable(dateToConvert).isPresent()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BD_DATE);
            return LocalDateTime.parse(dateToConvert, formatter);
        } else {
            return null;
        }
    }
}
