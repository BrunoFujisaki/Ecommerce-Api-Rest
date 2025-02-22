package brunofujisaki.ecommerce.repository;

import brunofujisaki.ecommerce.domain.cliente.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findAllByAtivoTrue(Pageable pageable);

    UserDetails findByEmail(String username);
}
