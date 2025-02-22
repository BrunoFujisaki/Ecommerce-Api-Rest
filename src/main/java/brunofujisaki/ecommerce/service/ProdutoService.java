package brunofujisaki.ecommerce.service;

import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.produto.Produto;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import brunofujisaki.ecommerce.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    public Produto verificarProdutoEAtualizarEstoque(Long id, Integer quantidade, boolean adicionar) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Produto não encontrado."));

        if (!produto.getAtivo()) throw new ValidacaoException("Produto não disponível.");

        if (!adicionar) verificarEstoque(produto, quantidade);

        produto.atualizarEstoque(quantidade, adicionar);
        return produto;
    }

    public void desativarProduto(Long id) {
        var produto = produtoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Produto não encontrado"));

        if (pedidoRepository.existePedidoPendenteComProduto(produto.getId())) throw new ValidacaoException("Não é possível desativar um produto que possui pedido pendente.");

        produto.desativar();
    }

    private void verificarEstoque(Produto produto, Integer quantidade) {
        if (quantidade > produto.getEstoque() || produto.getEstoque() == 0) {
            throw new ValidacaoException("Produto: " + produto.getNome() + "\nSem estoque suficiente");
        }
    }
}
