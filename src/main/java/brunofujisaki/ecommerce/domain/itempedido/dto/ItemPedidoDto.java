package brunofujisaki.ecommerce.domain.itempedido.dto;

import jakarta.validation.constraints.NotNull;

public record ItemPedidoDto(
        @NotNull
        Double precoUnitario,
        @NotNull
        Integer quantidade,
        @NotNull
        Long produtoId
) {
}
