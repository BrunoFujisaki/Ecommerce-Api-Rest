package brunofujisaki.ecommerce.domain.produto.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarProdutoDto(
        @NotNull
        Long id,
        String nome,
        String descricao,
        Double preco,
        Integer estoque,
        Boolean adicionar
) {
}
