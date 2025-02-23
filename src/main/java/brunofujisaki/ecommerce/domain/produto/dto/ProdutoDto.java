package brunofujisaki.ecommerce.domain.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoDto(
      @NotBlank
      String nome,
      @NotBlank
      String descricao,
      @NotNull
      Double preco,
      @NotNull
      Integer estoque
) {
}
