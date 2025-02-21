package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.pedido.dto.AtualizarPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.DetalharPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.PedidoDto;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import brunofujisaki.ecommerce.service.PedidoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPedido(@RequestBody @Valid PedidoDto pedidoDto, UriComponentsBuilder uriBuilder) {
        var pedido = pedidoService.cadastrarPedido(pedidoDto);
        var uri = uriBuilder.path("pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidoDto);
    }

    @GetMapping
    public ResponseEntity<Page<DetalharPedidoDto>> listarPedidos(Pageable pageable) {
        var page = pedidoRepository.findAll(pageable).map(DetalharPedidoDto::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarStatusPedido(@RequestBody @Valid AtualizarPedidoDto atualizarPedidoDto) {
        var pedido = pedidoRepository.findById(atualizarPedidoDto.id()).orElseThrow(() -> new ValidacaoException("Pedido não encontrado."));
        pedido.atualizarStatus(atualizarPedidoDto.status());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarPedido(@PathVariable Long id) {
        pedidoService.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{status}")
    public ResponseEntity buscarPedidosPorStatus(@PathVariable StatusPedido status, Pageable pageable) {
        var page = pedidoRepository.encontrarPedidosPeloStatus(status, pageable).map(DetalharPedidoDto::new);
        return ResponseEntity.ok(page);
    }
}
