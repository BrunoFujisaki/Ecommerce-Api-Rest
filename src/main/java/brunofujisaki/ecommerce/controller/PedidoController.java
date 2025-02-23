package brunofujisaki.ecommerce.controller;

import brunofujisaki.ecommerce.domain.pedido.dto.AtualizarPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.DetalharPedidoDto;
import brunofujisaki.ecommerce.domain.pedido.dto.PedidoDto;
import brunofujisaki.ecommerce.domain.usuario.UserRole;
import brunofujisaki.ecommerce.domain.usuario.Usuario;
import brunofujisaki.ecommerce.infra.exception.ValidacaoException;
import brunofujisaki.ecommerce.domain.pedido.StatusPedido;
import brunofujisaki.ecommerce.repository.PedidoRepository;
import brunofujisaki.ecommerce.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pedidos")
@SecurityRequirement(name = "bearer-key")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrarPedido(@RequestBody @Valid PedidoDto pedidoDto, Authentication authentication, UriComponentsBuilder uriBuilder) {
        var pedido = pedidoService.cadastrarPedido(pedidoDto, authentication);
        var uri = uriBuilder.path("pedidos/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(pedidoDto);
    }

    @GetMapping
    public ResponseEntity<Page<DetalharPedidoDto>> listarPedidos(@PageableDefault(size = 10, sort={"id"}) Pageable pageable, Authentication authentication) {
        var page = pedidoService.verificar(pageable, authentication);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizarStatusPedido(@RequestBody @Valid AtualizarPedidoDto atualizarPedidoDto) {
        var pedido = pedidoService.atualizarPedido(atualizarPedidoDto);
        return ResponseEntity.ok(new DetalharPedidoDto(pedido));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity cancelarPedido(@PathVariable Long id, Authentication authentication) {
        pedidoService.cancelarPedido(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{status}")
    public ResponseEntity buscarPedidosPorStatus(@PathVariable StatusPedido status, @PageableDefault(size = 10, sort={"id"}) Pageable pageable) {
        var page = pedidoRepository.encontrarPedidosPeloStatus(status, pageable).map(DetalharPedidoDto::new);
        return ResponseEntity.ok(page);
    }
}
