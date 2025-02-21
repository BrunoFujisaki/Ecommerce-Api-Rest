package brunofujisaki.ecommerce.domain.itempedido.dto;

public record ItemPedidoDto(
        Double precoUnitario,
        Integer quantidade,
        Long produtoId
) {
}
