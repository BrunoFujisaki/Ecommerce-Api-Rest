package brunofujisaki.ecommerce.service;

import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;
import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import brunofujisaki.ecommerce.domain.pedido.dto.PedidoDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import brunofujisaki.ecommerce.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public Pedido cadastrarPedido(PedidoDto pedidoDto) {
        var usuario = usuarioRepository.findById(pedidoDto.clienteId()).orElseThrow();

        var pedido = new Pedido(usuario);

        if (pedidoDto.itens().isEmpty()) throw new ValidacaoException("A lista do pedido não pode estar vazia.");

        pedidoDto.itens().forEach(i -> {
            var produto = produtoService.verificarProdutoEAtualizarEstoque(i.produtoId(), i.quantidade(), false);
            pedido.adicionarItem(new ItemPedido(i, produto));
        });

        pedido.calcPrecoTotal(pedidoDto.itens());

        pedidoRepository.save(pedido);

        return pedido;
    }

    public void cancelarPedido(Long id) {
        var pedido = pedidoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Pedido não encontrado."));

        if (pedido.getStatus() != StatusPedido.PENDENTE) throw new ValidacaoException("Só é possível cancelar pedidos pendentes.");

        pedido.getItens().forEach(i -> {
            produtoService.verificarProdutoEAtualizarEstoque(i.getProduto().getId(), i.getQuantidade(), true);
        });
        pedido.atualizarStatus(StatusPedido.CANCELADO);
    }
}
