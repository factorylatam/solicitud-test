package pe.gob.sunarp.app.solicitud.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NiubizData {
    private String terminal;
    private String traceNumber;
    private String eciDescription;
    private String signature;
    private String card;
    private String merchant;
    private String status;
    private String actionDescription;
    private String idUnico;
    private String amount;
    private String authorizationCode;
    private String transactionDate;
    private String actionCode;
    private String eci;
    private String brand;
    private String adquirente;
    private String processCode;
    private String transactionId;
    private String recurrenceStatus;
    private String recurrenceMaxAmount;
}
