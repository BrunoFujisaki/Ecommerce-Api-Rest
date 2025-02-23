package brunofujisaki.ecommerce.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface VerificadorDeAcesso<T> {
    Page<T> verificar(Pageable pageable, Authentication authentication);
}
