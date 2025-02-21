package brunofujisaki.ecommerce.domain.pedido.dto;

import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record AtualizarPedidoDto(
        @NotNull
        Long id,
        StatusPedido status
) {
}
