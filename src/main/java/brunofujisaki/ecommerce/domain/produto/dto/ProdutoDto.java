package brunofujisaki.ecommerce.domain.produto.dto;

public record ProdutoDto(
      String nome,
      String descricao,
      Double preco,
      Integer estoque
) {
}
