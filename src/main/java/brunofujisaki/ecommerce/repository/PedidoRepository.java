package brunofujisaki.ecommerce.repository;

import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("SELECT p FROM Pedido p WHERE p.status = ?1")
    Page<Pedido> encontrarPedidosPeloStatus(StatusPedido status, Pageable pageable);

    @Query("SELECT COUNT(p) > 0 FROM Pedido p JOIN p.itens i WHERE i.produto.id = ?1 AND p.status = 'PENDENTE'")
    boolean existePedidoPendenteComProduto(Long id);



}
