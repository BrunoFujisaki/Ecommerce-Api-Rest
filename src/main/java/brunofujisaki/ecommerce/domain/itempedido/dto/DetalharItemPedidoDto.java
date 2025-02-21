package brunofujisaki.ecommerce.domain.itempedido.dto;

import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;

public record DetalharItemPedidoDto(
        Double precoUnitario,
        Integer quantidade,
        Long produtoId,
        Long pedidoId
) {
    public DetalharItemPedidoDto(ItemPedido itemPedido) {
        this(itemPedido.getPrecoUnitario(), itemPedido.getQuantidade(), itemPedido.getProduto().getId(), itemPedido.getPedido().getId());
    }
}
