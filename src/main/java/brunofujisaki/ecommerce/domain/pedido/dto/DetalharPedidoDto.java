package brunofujisaki.ecommerce.domain.pedido.dto;

import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;

import java.time.LocalDate;

public record DetalharPedidoDto(
        Long id,
        LocalDate data,
        Long cliente_id,
        Double valor_total,
        StatusPedido status
) {
    public DetalharPedidoDto(Pedido pedido) {
        this(pedido.getId(), pedido.getData(), pedido.getUsuario().getId(), pedido.getValorTotal(), pedido.getStatus());
    }
}
