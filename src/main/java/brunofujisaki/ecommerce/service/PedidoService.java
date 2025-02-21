package brunofujisaki.ecommerce.service;

import brunofujisaki.ecommerce.domain.pedido.dto.PedidoDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;
import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import brunofujisaki.ecommerce.repository.ClienteRepository;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public Pedido cadastrarPedido(PedidoDto pedidoDto) {
        var cliente = clienteRepository.findById(pedidoDto.clienteId()).orElseThrow();

        var pedido = new Pedido(cliente);

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
        pedido.getItens().forEach(i -> {
            produtoService.verificarProdutoEAtualizarEstoque(i.getProduto().getId(), i.getQuantidade(), true);
        });
        pedido.atualizarStatus(StatusPedido.CANCELADO);
    }
}
