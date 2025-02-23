package brunofujisaki.ecommerce.domain.pedido.dto;

import brunofujisaki.ecommerce.domain.itempedido.dto.ItemPedidoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoDto(
//        @NotNull
//        Long usuarioId,
        @NotNull @Valid
        List<ItemPedidoDto> itens
) {
}
