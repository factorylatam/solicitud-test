package pe.gob.sunarp.app.solicitud.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties
@Component
@Getter
@Setter
public class ApplicationProperties {

    @Value("#{${certificados.ids.copia.literal}}")
    private List<String> certiCopiaLiteral;

    @Value("#{${certificados.ids.sprn}}")
    private List<String> certiSprn;

    @Value("${reingreso.url}")
    private String reingresoUrl;

    @Value("${descarga.certificado.url}")
    private String descargaCertiUrl;

    @Value("${guardar.caja.url}")
    private String guardarCajaUrl;

    @Value("${linea.prepago}")
    private Long lineaPrepago;

    @Value("${thread.get.transaction}")
    private int threads;

}
