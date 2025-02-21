package brunofujisaki.ecommerce.repository;

import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
