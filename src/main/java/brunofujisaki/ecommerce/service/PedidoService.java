package brunofujisaki.ecommerce.service;

import brunofujisaki.ecommerce.domain.itempedido.ItemPedido;
import brunofujisaki.ecommerce.domain.pedido.Pedido;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import brunofujisaki.ecommerce.domain.pedido.dto.AtualizarPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.DetalharPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.PedidoDto;
import brunofujisaki.ecommerce.domain.usuario.UserRole;
import brunofujisaki.ecommerce.domain.usuario.Usuario;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class PedidoService implements VerificadorDeAcesso {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoService produtoService;

    public Pedido cadastrarPedido(PedidoDto pedidoDto, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();

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

    public void cancelarPedido(Long id, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        var pedido = pedidoRepository.findById(id).orElseThrow(() -> new ValidacaoException("Pedido não encontrado."));

        boolean usuarioNaoAutorizado =
                !pedidoRepository.pedidoPertenceAoUsuarioLogado(pedido.getId(), usuario.getId())
                        && !usuario.getRole().equals(UserRole.ADMIN);

        if (usuarioNaoAutorizado) throw new ValidacaoException("O pedido não pertence ao usuário logado.");

        if (pedido.getStatus() != StatusPedido.PENDENTE) throw new ValidacaoException("Só é possível cancelar pedidos pendentes.");

        pedido.getItens().forEach(i -> {
            produtoService.verificarProdutoEAtualizarEstoque(i.getProduto().getId(), i.getQuantidade(), true);
        });
        pedido.atualizarStatus(StatusPedido.CANCELADO);
    }

    public Pedido atualizarPedido(@Valid AtualizarPedidoDto atualizarPedidoDto) {
        var pedido = pedidoRepository.findById(atualizarPedidoDto.id()).orElseThrow(() -> new ValidacaoException("Pedido não encontrado."));

        if (pedido.getStatus().equals(StatusPedido.ENTREGUE)) throw new ValidacaoException("Não há como alterar o status de um pedido que ja foi entregue.");

        if (pedido.getStatus().equals(StatusPedido.CANCELADO)) throw new ValidacaoException("Não é possível atualizar um pedido cancelado.");

        if (atualizarPedidoDto.status().equals(StatusPedido.CANCELADO)) {
            pedido.getItens().forEach(item -> {
                item.getProduto().atualizarEstoque(item.getQuantidade(), true);
            });
        };

        pedido.atualizarStatus(atualizarPedidoDto.status());
        return pedido;
    }

    @Override
    public Page<DetalharPedidoDto> verificar(Pageable pageable, Authentication authentication) {
        var usuario = (Usuario) authentication.getPrincipal();
        if (usuario.getRole().equals(UserRole.CLIENTE)) {
            return pedidoRepository.findAllByUsuarioId(usuario.getId(), pageable).map(DetalharPedidoDto::new);
        }
        return pedidoRepository.findAll(pageable).map(DetalharPedidoDto::new);
    }


}
