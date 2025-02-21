package brunofujisaki.ecommerce.domain.produto;

import brunofujisaki.ecommerce.domain.produto.dto.AtualizarProdutoDto;
import brunofujisaki.ecommerce.domain.produto.dto.ProdutoDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private LocalDate dataCadastro;
    private Integer estoque;
    private Boolean ativo;

    public Produto(@Valid ProdutoDto produtoDto) {
        this.nome = produtoDto.nome();
        this.descricao = produtoDto.descricao();
        this.preco = produtoDto.preco();
        this.dataCadastro = LocalDate.now();
        this.estoque = produtoDto.estoque();
        this.ativo = true;
    }

    public void atualizarProduto(AtualizarProdutoDto produtoDto) {
        if (produtoDto.nome() != null)
            this.nome = produtoDto.nome();

        if (produtoDto.descricao() != null)
            this.descricao = produtoDto.descricao();

        if (produtoDto.preco() != null)
            this.preco = produtoDto.preco();

        if (produtoDto.estoque() != null) {
            if (!produtoDto.adicionar() && this.estoque < produtoDto.estoque()) {
                throw new ValidacaoException("Estoque insuficiente");
            }

            this.estoque += produtoDto.adicionar() ? produtoDto.estoque() : -produtoDto.estoque();
        }

    }

    public void atualizarEstoque(Integer quantidade, boolean adicionar) {
        this.estoque += adicionar ? quantidade : -quantidade;
    }

    public void desativar() {
        this.ativo = false;
    }
}
