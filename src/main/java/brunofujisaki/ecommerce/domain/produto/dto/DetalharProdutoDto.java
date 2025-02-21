package brunofujisaki.ecommerce.domain.produto.dto;

import brunofujisaki.ecommerce.domain.produto.Produto;

import java.time.LocalDate;

public record DetalharProdutoDto(
        String nome,
        String descricao,
        Double preco,
        LocalDate dataCadastro,
        Integer estoque,
        Boolean ativo
) {
    public DetalharProdutoDto(Produto produto) {
        this(produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getDataCadastro(), produto.getEstoque(), produto.getAtivo());
    }
}
