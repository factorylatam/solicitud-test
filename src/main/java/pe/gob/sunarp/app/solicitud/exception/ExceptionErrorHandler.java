package pe.gob.sunarp.app.solicitud.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericException(Exception e){
        log.error("Ocurrió un error: {}" , e.getLocalizedMessage());
        return new ResponseEntity<>(new ExceptionResponse("500","Ocurrio un error generico: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,"AppSunarp-Solicitud"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExceptionBean.class)
    public ResponseEntity<ExceptionResponse> customException(ExceptionBean bean){
        log.error("Ocurrió un error: {}" , bean.getDescription());
        return new ResponseEntity<>(mapException(bean), bean.getCategory());
    }

    private ExceptionResponse mapException(ExceptionBean bean){
        return ExceptionResponse.builder()
                .code(bean.getCode())
                .description(bean.getDescription())
                .category(bean.getCategory())
                .component(bean.getComponent())
                .build();
    }

}
