package brunofujisaki.ecommerce.service;

import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.produto.Produto;
import brunofujisaki.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto verificarProdutoEAtualizarEstoque(Long id, Integer quantidade, boolean adicionar) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Produto não encontrado."));

        if (!adicionar) {
            verificarEstoque(produto, quantidade);
        }

        produto.atualizarEstoque(quantidade, adicionar);
        return produto;
    }

    private void verificarEstoque(Produto produto, Integer quantidade) {
        if (quantidade > produto.getEstoque() || produto.getEstoque() == 0) {
            throw new ValidacaoException("Produto: " + produto.getNome() + "\nSem estoque suficiente");
        }
    }
}
