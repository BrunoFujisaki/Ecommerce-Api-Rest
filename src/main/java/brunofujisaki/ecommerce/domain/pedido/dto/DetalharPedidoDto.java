package brunofujisaki.ecommerce.domain.pedido.dto;

import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;

import java.time.LocalDate;

public record DetalharPedidoDto(
        LocalDate data,
        Long cliente_id,
        Double valor_total,
        StatusPedido status
) {
    public DetalharPedidoDto(Pedido pedido) {
        this(pedido.getData(), pedido.getUsuario().getId(), pedido.getValorTotal(), pedido.getStatus());
    }
}
