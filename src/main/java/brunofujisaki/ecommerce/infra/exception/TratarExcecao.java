package brunofujisaki.ecommerce.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarExcecao {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity erroValidacao(ValidacaoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
