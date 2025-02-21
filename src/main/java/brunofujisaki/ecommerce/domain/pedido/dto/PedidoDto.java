package brunofujisaki.ecommerce.domain.pedido.dto;

import brunofujisaki.ecommerce.domain.itempedido.dto.ItemPedidoDto;

import java.util.List;

public record PedidoDto(
        Long clienteId,
        List<ItemPedidoDto> itens
) {
}
